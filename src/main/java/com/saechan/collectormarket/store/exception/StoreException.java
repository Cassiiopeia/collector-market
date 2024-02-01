package com.saechan.collectormarket.store.exception;

import com.saechan.collectormarket.global.excpetion.CustomException;
import com.saechan.collectormarket.global.excpetion.ErrorCode;

public class StoreException extends CustomException {

  public StoreException(
      ErrorCode errorCode) {
    super(errorCode);
  }
}
