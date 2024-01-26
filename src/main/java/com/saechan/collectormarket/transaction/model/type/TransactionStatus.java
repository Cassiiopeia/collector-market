package com.saechan.collectormarket.transaction.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionStatus {
  ON_SALE("판매중"),
  RESERVED("예약됨"),
  SOLD_OUT("판매됨");

  private final String description;
}