package com.saechan.collectormarket.member.exception;

import com.saechan.collectormarket.global.excpetion.CustomException;
import com.saechan.collectormarket.global.excpetion.ErrorCode;

public class MemberException extends CustomException {

  public MemberException(
      ErrorCode errorCode) {
    super(errorCode);
  }
}
