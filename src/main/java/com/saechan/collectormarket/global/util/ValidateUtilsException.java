package com.saechan.collectormarket.global.util;

import com.saechan.collectormarket.global.excpetion.CustomException;
import com.saechan.collectormarket.global.excpetion.ErrorCode;

public class ValidateUtilsException extends CustomException {

  public ValidateUtilsException(
      ErrorCode errorCode) {
    super(errorCode);
  }
}
