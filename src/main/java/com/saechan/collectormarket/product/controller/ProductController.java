package com.saechan.collectormarket.product.controller;

import com.saechan.collectormarket.product.dto.request.ProductCreateForm;
import com.saechan.collectormarket.product.dto.request.ProductSearchForm;
import com.saechan.collectormarket.product.dto.request.ProductUpdateForm;
import com.saechan.collectormarket.product.dto.response.ProductDto;
import com.saechan.collectormarket.product.dto.response.ProductSearchDto;
import com.saechan.collectormarket.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  private final String DEFAULT_PAGE_NUM = "0";
  private final String DEFAULT_PAGE_SIZE = "10";

  // 상품 생성
  @PostMapping("/create")
  public ResponseEntity<ProductDto> createProduct(
      Authentication authentication,
      @Valid @ModelAttribute ProductCreateForm form
  ) {
    String memberEmail = authentication.getName();
    ProductDto productDto = productService.create(memberEmail, form);
    return ResponseEntity.ok(productDto);
  }

  // 상품 수정
  @PutMapping("/update")
  public ResponseEntity<ProductDto> updateProduct(
      Authentication authentication,
      @Valid @ModelAttribute ProductUpdateForm form,
      @RequestParam long id
  ) {
    String memberEmail = authentication.getName();
    ProductDto productDto = productService.update(memberEmail, form, id);
    return ResponseEntity.ok(productDto);
  }

  // 상품 삭제
  @DeleteMapping("/delete")
  public ResponseEntity<Void> deleteProduct(
      Authentication authentication,
      @RequestParam long id
  ){
    String memberEmail = authentication.getName();
    productService.delete(memberEmail, id);
    return ResponseEntity.noContent().build();
  }

  // 상품 상세정보 보기
  @GetMapping("/view")
  public ResponseEntity<ProductDto> viewProduct(
      Authentication authentication,
      @RequestParam long id
  ){
    ProductDto productDto = productService.view(id);
    return ResponseEntity.ok(productDto);
  }

  // 상품 검색 ( 이름, 가격, 상태, 카테고리, 거래상태 )
  @GetMapping("/search")
  public ResponseEntity<Page<ProductSearchDto>> searchProduct(
      Authentication authentication,
      @Valid @ModelAttribute ProductSearchForm form,
      @RequestParam(defaultValue = DEFAULT_PAGE_NUM) int page,
      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size
  ){
    Page<ProductSearchDto> productSearchDtos = productService.search(form,page,size);
    return ResponseEntity.ok(productSearchDtos);
  }
}