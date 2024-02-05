package com.saechan.collectormarket.member.exception;

import com.saechan.collectormarket.global.exception.CustomException;
import com.saechan.collectormarket.global.exception.ErrorCode;

public class MemberException extends CustomException {

  public MemberException(
      ErrorCode errorCode) {
    super(errorCode);
  }
}
