package com.saechan.collectormarket.product.exception;

import com.saechan.collectormarket.global.exception.CustomException;
import com.saechan.collectormarket.global.exception.ErrorCode;

public class ProductException extends CustomException {
  public ProductException(ErrorCode errorCode) {
    super(errorCode);
  }
}


