package com.saechan.collectormarket.global.lock;

import com.saechan.collectormarket.global.exception.CustomException;
import com.saechan.collectormarket.global.exception.ErrorCode;

public class LockException extends CustomException {

  public LockException(
      ErrorCode errorCode) {
    super(errorCode);
  }
}
