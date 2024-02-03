package com.saechan.collectormarket.auth.exception;

import com.saechan.collectormarket.global.exception.CustomException;
import com.saechan.collectormarket.global.exception.ErrorCode;

public class MailException extends CustomException {

  public MailException(
      ErrorCode errorCode) {
    super(errorCode);
  }
}
