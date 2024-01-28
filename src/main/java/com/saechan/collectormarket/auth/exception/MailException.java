package com.saechan.collectormarket.auth.exception;

import com.saechan.collectormarket.global.excpetion.CustomException;
import com.saechan.collectormarket.global.excpetion.ErrorCode;

public class MailException extends CustomException {

  public MailException(
      ErrorCode errorCode) {
    super(errorCode);
  }
}
