package com.saechan.collectormarket.store.exception;

import com.saechan.collectormarket.global.exception.CustomException;
import com.saechan.collectormarket.global.exception.ErrorCode;

public class StoreException extends CustomException {

  public StoreException(
      ErrorCode errorCode) {
    super(errorCode);
  }
}
