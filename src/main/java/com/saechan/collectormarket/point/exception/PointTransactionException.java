package com.saechan.collectormarket.point.exception;


import com.saechan.collectormarket.global.excpetion.CustomException;
import com.saechan.collectormarket.global.excpetion.ErrorCode;

public class PointTransactionException extends CustomException {
  public PointTransactionException(
      ErrorCode errorCode) {
    super(errorCode);
  }
}
