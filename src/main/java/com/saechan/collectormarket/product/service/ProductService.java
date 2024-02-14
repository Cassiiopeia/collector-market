package com.saechan.collectormarket.product.service;


import static com.saechan.collectormarket.transaction.model.type.TransactionStatus.ON_SALE;

import com.saechan.collectormarket.global.exception.ErrorCode;
import com.saechan.collectormarket.global.s3.FileStorageService;
import com.saechan.collectormarket.global.s3.ImageUploadService;
import com.saechan.collectormarket.global.s3.type.ImageProperty;
import com.saechan.collectormarket.member.model.entity.Member;
import com.saechan.collectormarket.member.service.MemberUtils;
import com.saechan.collectormarket.product.dto.request.ProductCreateForm;
import com.saechan.collectormarket.product.dto.request.ProductUpdateForm;
import com.saechan.collectormarket.product.dto.response.ProductDto;
import com.saechan.collectormarket.product.exception.ProductException;
import com.saechan.collectormarket.product.model.entity.Product;
import com.saechan.collectormarket.product.model.repository.ProductImageRepository;
import com.saechan.collectormarket.product.model.repository.ProductRepository;
import com.saechan.collectormarket.store.exception.StoreException;
import com.saechan.collectormarket.store.model.entity.Store;
import com.saechan.collectormarket.store.model.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final StoreRepository storeRepository;

  private final ProductSearchService productSearchService;

  private final FileStorageService fileStorageService;
  private final ImageUploadService imageUploadService;
  private final ProductImageRepository productImageRepository;


  @Transactional
  public ProductDto create(String memberEmail, ProductCreateForm form) {
    // 회원 검증
    Member member = MemberUtils.verifyMemberFromEmail(memberEmail);

    // 상점 검증
    Store store = storeRepository.findById(member.getStore().getId())
        .orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));

    // 상품 생성
    Product newProduct = productRepository.save(
        Product.builder()
            .name(form.getName())
            .description(form.getDescription())
            .price(form.getPrice())
            .productStatus(form.getProductStatus())
            .deliveryMethod(form.getDeliveryMethod())
            .shippingCost(form.getShippingCost())
            .productCategory(form.getProductCategory())
            .transactionStatus(ON_SALE)
            .store(store)
            .build()
    );

    // ProductImage 복수객체 생성 -> Product 에 List<ProductImage> 지정
    newProduct.setProductImages(
        imageUploadService.uploadProductImages( newProduct, form.getImages(),
            ImageProperty.PRODUCT, newProduct.getId())
    );

    // 상품 생성 + ES 인덱스 생성
    productRepository.save(newProduct);
    productSearchService.indexProduct(newProduct);
    return ProductDto.from(newProduct);
  }

  @Transactional
  public ProductDto update(String memberEmail, ProductUpdateForm form, Long id) {
    // 회원 검증
    Member member = MemberUtils.verifyMemberFromEmail(memberEmail);

    // 상점 검증
    Store store = storeRepository.findById(member.getStore().getId())
        .orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));

    // 상품 검증
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));

    // 기존 이미지 존재시 삭제 (S3삭제, ProductImage 삭제)
    if (product.getProductImages() != null && !product.getProductImages().isEmpty()) {
      product.getProductImages().forEach(image -> {
        fileStorageService.deleteFile(image.getImageUrl());
        productImageRepository.delete(image);
      });
      product.setProductImages(null); // 상품 이미지 초기화
    }

    // Form 이미지 파일이 존재시
    if(!form.getImages().isEmpty()){
      // S3 업로드 -> ProductImage 생성 -> Product 에 List<ProductImage> 지정
      product.setProductImages(
          imageUploadService.uploadProductImages( product, form.getImages(),
              ImageProperty.PRODUCT, product.getId())
      );
    }

    // 상품 업데이트
    product.setName(form.getName());
    product.setDescription(form.getDescription());
    product.setPrice(form.getPrice());
    product.setProductStatus(form.getProductStatus());
    product.setDeliveryMethod(form.getDeliveryMethod());
    product.setShippingCost(form.getShippingCost());
    product.setProductCategory(form.getProductCategory());
    product.setTransactionStatus(form.getTransactionStatus());
    product.setStore(store);

    // 업데이트 상품 저장 + ES 인덱스 저장
    productRepository.save(product);
    productSearchService.indexProduct(product);
    return ProductDto.from(product);
  }

  @Transactional
  public void delete(String memberEmail, Long productId){
    // 회원 검증
    Member member = MemberUtils.verifyMemberFromEmail(memberEmail);

    // 상품 검증
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));

    // 소유 상품 검증
    if (!product.getStore().getMember().getEmail().equals(memberEmail)) {
      throw new ProductException(ErrorCode.PRODUCT_ACCESS_DENIED);
    }

    // S3 상품 이미지 삭제
    if (product.getProductImages() != null && !product.getProductImages().isEmpty()) {
      product.getProductImages().forEach(
          image -> fileStorageService.deleteFile(image.getImageUrl()));
    }

    // 상품 삭제
    productRepository.deleteById(productId);
    productSearchService.deleteProductIndex(product);
  }

  @Transactional(readOnly = true)
  public ProductDto view(String memberEmail, long id) {
    // 회원 검증
    Member member = MemberUtils.verifyMemberFromEmail(memberEmail);

    // 상품 검증
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));

    return ProductDto.from(product);
  }
}

