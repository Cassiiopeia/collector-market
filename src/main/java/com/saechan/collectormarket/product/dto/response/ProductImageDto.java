package com.saechan.collectormarket.product.dto.response;

import com.saechan.collectormarket.product.model.entity.ProductImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductImageDto {
  private Long id;
  private String imageUrl;
  private Long productId;

  public static ProductImageDto from(ProductImage productImage){
    return ProductImageDto.builder()
        .id(productImage.getId())
        .imageUrl(productImage.getImageUrl())
        .productId(productImage.getProduct().getId())
        .build();
  }
}

