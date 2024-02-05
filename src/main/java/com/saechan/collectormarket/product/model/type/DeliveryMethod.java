package com.saechan.collectormarket.product.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryMethod {
  DIRECT("직거래"),
  DELIVERY("배송"),
  BOTH("직거래 or 배송");

  private final String description;
}
