package com.saechan.collectormarket.product.service;


import static com.saechan.collectormarket.transaction.model.type.TransactionStatus.ON_SALE;

import com.saechan.collectormarket.global.exception.ErrorCode;
import com.saechan.collectormarket.global.s3.FileStorageService;
import com.saechan.collectormarket.member.model.entity.Member;
import com.saechan.collectormarket.member.service.MemberUtils;
import com.saechan.collectormarket.product.dto.request.ProductCreateForm;
import com.saechan.collectormarket.product.dto.request.ProductUpdateForm;
import com.saechan.collectormarket.product.dto.response.ProductDto;
import com.saechan.collectormarket.product.exception.ProductException;
import com.saechan.collectormarket.product.model.entity.Product;
import com.saechan.collectormarket.product.model.entity.ProductImage;
import com.saechan.collectormarket.product.model.repository.ProductImageRepository;
import com.saechan.collectormarket.product.model.repository.ProductRepository;
import com.saechan.collectormarket.store.exception.StoreException;
import com.saechan.collectormarket.store.model.entity.Store;
import com.saechan.collectormarket.store.model.repository.StoreRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final FileStorageService fileStorageService;
  private final StoreRepository storeRepository;
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

    // 복수 이미지 파일처리
    List<String> imageUrls = fileStorageService.uploadFiles(
        form.getImages(), "product", newProduct.getId());

    List<ProductImage> productImages = new ArrayList<>();
    imageUrls.forEach(imageUrl -> {
      ProductImage productImage = ProductImage.builder()
          .imageUrl(imageUrl)
          .product(newProduct)
          .build();
      productImages.add(productImage);
    });
    newProduct.setImages(productImages);

    // 상품 생성
    productRepository.save(newProduct);
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

    // 기존 이미지 존재시 삭제
    if (!product.getImages().isEmpty()) {
      product.getImages().forEach(image -> {
        fileStorageService.deleteFile(image.getImageUrl());
        productImageRepository.delete(image);
      });
      product.getImages().clear();
    }

    // 이미지 초기화 후 복수 이미지 파일처리
    List<String> imageUrls = fileStorageService.uploadFiles(
        form.getImages(), "product", product.getId());
    List<ProductImage> productImages = new ArrayList<>();
    imageUrls.forEach(imageUrl -> {
      ProductImage productImage = ProductImage.builder()
          .imageUrl(imageUrl)
          .product(product)
          .build();
      productImages.add(productImage);
    });
    product.setImages(productImages);

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

    // 업데이트 상품 저장
    productRepository.save(product);
    return ProductDto.from(product);
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

