package com.saechan.collectormarket.global.exception;

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


  // Validation

  INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "이메일 형식이 올바르지 않습니다."),

  INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "비밀번호 형식이 올바르지 않습니다."),

  INVALID_PHONE_FORMAT(HttpStatus.BAD_REQUEST, "전화번호 형식이 올바르지 않습니다."),

  INVALID_NAME_FORMAT(HttpStatus.BAD_REQUEST, "이름 형식이 올바르지 않습니다."),

  INVALID_DESCRIPTION_FORMAT(HttpStatus.BAD_REQUEST, "내용 형식이 올바르지 않습니다."),

  // Member

  ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),

  INVALID_EMAIL_AUTH_CODE(HttpStatus.BAD_REQUEST, "이메일 인증코드가 잘못되었습니다."),

  EXPIRED_EMAIL_AUTH_CODE(HttpStatus.BAD_REQUEST, "이메일 인증시간이 만료되었습니다."),

  MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "회원이 존재하지 않습니다."),

  MEMBER_NOT_ACTIVATED(HttpStatus.BAD_REQUEST, "비활성화된 회원입니다."),

  INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

  CANT_DELETE_NONZERO_POINTS_MEMBER(HttpStatus.BAD_REQUEST, "포인트가 0이 아니면 탈퇴할 수 없습니다."),

  // PointTransaction

  NOT_ENOUGH_POINT(HttpStatus.BAD_REQUEST, "포인트가 부족합니다."),

  // Store

  STORE_NOT_FOUND(HttpStatus.BAD_REQUEST, "상점이 존재하지 않습니다."),


  // S3 file Storage

  FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "파일 크기가 초과되었습니다."),

  INVALID_FILE_FORMAT(HttpStatus.BAD_REQUEST, "잘못된 파일 형식입니다."),

  FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 중 오류가 발생했습니다."),

  FILE_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 삭제 중 오류가 발생했습니다."),

  // JWT

  EXPIRED_JWT_TOKEN(HttpStatus.FORBIDDEN, "JWT 토큰이 만료되었습니다."),

  UNSUPPORTED_JWT_TOKEN(HttpStatus.FORBIDDEN, "지원하지않는 JWT 토큰입니다."),

  INVALID_JWT_SIGNATURE(HttpStatus.BAD_REQUEST,"JWT 서명이 유효하지 않습니다.");

  private final HttpStatus status;
  private final String message;
}
