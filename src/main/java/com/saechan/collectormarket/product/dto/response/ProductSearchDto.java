package com.saechan.collectormarket.product.dto.response;

import com.saechan.collectormarket.product.model.entity.Product;
import com.saechan.collectormarket.transaction.model.type.TransactionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductSearchDto {
  private Long id;

  private String name;

  private Double price;

  // 썸네일 이미지 주소
  private String imageUrl;

  private TransactionStatus transactionStatus;

  public static ProductSearchDto from(Product product){
    return ProductSearchDto.builder()
        .id(product.getId())
        .name(product.getName())
        .price(product.getPrice())
        .imageUrl(product.getProductImages().getFirst().getImageUrl())
        .transactionStatus(product.getTransactionStatus())
        .build();
  }
}
