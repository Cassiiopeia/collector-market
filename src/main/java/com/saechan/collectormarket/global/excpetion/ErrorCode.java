package com.saechan.collectormarket.global.excpetion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  // Global

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다."),

  INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

  ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근이 거부되었습니다."),

  // JWT

  EXPIRED_JWT_TOKEN(HttpStatus.FORBIDDEN, "JWT 토큰이 만료되었습니다."),

  UNSUPPORTED_JWT_TOKEN(HttpStatus.FORBIDDEN, "지원하지않는 JWT 토큰입니다."),

  INVALID_JWT_SIGNATURE(HttpStatus.BAD_REQUEST,"JWT 서명이 유효하지 않습니다.");

  private final HttpStatus status;
  private final String message;
}
