package com.saechan.collectormarket.product.controller;

import com.saechan.collectormarket.product.dto.request.ProductCreateForm;
import com.saechan.collectormarket.product.dto.request.ProductUpdateForm;
import com.saechan.collectormarket.product.dto.response.ProductDto;
import com.saechan.collectormarket.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

  @PostMapping("/create")
  public ResponseEntity<ProductDto> createProduct(
      Authentication authentication,
      @Valid @ModelAttribute ProductCreateForm form
  ) {
    String memberEmail = authentication.getName();
    ProductDto productDto = productService.create(memberEmail, form);
    return ResponseEntity.ok(productDto);
  }

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

  @DeleteMapping("/delete")
  public ResponseEntity<Void> deleteProduct(
      Authentication authentication,
      @RequestParam long id
  ){
    String memberEmail = authentication.getName();
    productService.delete(memberEmail, id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/view")
  public ResponseEntity<ProductDto> viewProduct(
      Authentication authentication,
      @RequestParam long id
  ){
    String memberEmail = authentication.getName();
    ProductDto productDto = productService.view(memberEmail, id);
    return ResponseEntity.ok(productDto);
  }
}