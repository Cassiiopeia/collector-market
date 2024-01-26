package com.saechan.collectormarket.global.excpetion.dto;

import com.saechan.collectormarket.global.excpetion.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
  private ErrorCode errorCode;
  private String errorMessage;
}

