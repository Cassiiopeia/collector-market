package com.saechan.collectormarket.global.excpetion;

import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException{
  private final ErrorCode errorCode;

  public CustomException(ErrorCode errorCode){
    super (errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
