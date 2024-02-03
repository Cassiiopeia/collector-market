package com.saechan.collectormarket.point.exception;


import com.saechan.collectormarket.global.exception.CustomException;
import com.saechan.collectormarket.global.exception.ErrorCode;

public class PointTransactionException extends CustomException {
  public PointTransactionException(
      ErrorCode errorCode) {
    super(errorCode);
  }
}
