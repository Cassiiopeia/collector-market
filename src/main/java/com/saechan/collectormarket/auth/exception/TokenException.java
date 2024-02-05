package com.saechan.collectormarket.auth.exception;

import com.saechan.collectormarket.global.exception.CustomException;
import com.saechan.collectormarket.global.exception.ErrorCode;

public class TokenException extends CustomException {
  public TokenException(ErrorCode errorCode) {
    super(errorCode);
  }
}
