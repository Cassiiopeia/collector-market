package com.saechan.collectormarket.product.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
  NEW("새상품(미사용)"),
  LIKE_NEW("사용감 없음"),
  MINOR_USAGE("사용감 적음"),
  MAJOR_USAGE("사용감 많음"),
  BROKEN("고장(파손)");

  private final String description;
}

