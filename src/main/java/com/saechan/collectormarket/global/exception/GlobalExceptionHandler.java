package com.saechan.collectormarket.global.exception;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.saechan.collectormarket.global.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  // 커스텀 예외 처리
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
    log.error("{}", e.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getMessage());
    return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);
  }

  // AWS 예외 처리
  @ExceptionHandler({AmazonServiceException.class, AmazonClientException.class})
  public ResponseEntity<String> handleAmazonException(Exception e) {
    log.error("{}", e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e){
    log.error("{}", e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
  }
}

