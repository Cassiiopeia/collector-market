package com.saechan.collectormarket.global.exception.dto;

import com.saechan.collectormarket.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
  private ErrorCode errorCode;
  private String errorMessage;
}

