package com.saechan.collectormarket.auth.exception;

import com.saechan.collectormarket.global.excpetion.CustomException;
import com.saechan.collectormarket.global.excpetion.ErrorCode;

public class TokenException extends CustomException {
  public TokenException(ErrorCode errorCode) {
    super(errorCode);
  }
}
