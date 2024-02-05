package com.saechan.collectormarket.global.util;

import com.saechan.collectormarket.global.exception.CustomException;
import com.saechan.collectormarket.global.exception.ErrorCode;

public class ValidateUtilsException extends CustomException {

  public ValidateUtilsException(
      ErrorCode errorCode) {
    super(errorCode);
  }
}
