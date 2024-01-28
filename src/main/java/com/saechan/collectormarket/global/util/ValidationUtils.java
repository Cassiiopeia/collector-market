package com.saechan.collectormarket.global.util;

import com.saechan.collectormarket.global.util.ValidateUtilsException;
import com.saechan.collectormarket.global.excpetion.ErrorCode;


public class ValidationUtils {

  public static void validateEmail(String email) {
    // Not null
    // @ 기호 존재
    // . 기호 존재
    if (email == null || !email.contains("@") || !email.contains(".")) {
      throw new ValidateUtilsException(ErrorCode.INVALID_EMAIL_FORMAT);
    }
  }

  public static void validatePassword(String password) {
    // Not null
    // 8자리 이상
    // 숫자가 최소 1개이상
    // 특수문자 최소 1개 이상
    if (password == null || password.length() < 8
        || !password.matches(".*[0-9].*") || !password.matches(".*[!@#$%^&*].*")) {
      throw new ValidateUtilsException(ErrorCode.INVALID_PASSWORD_FORMAT);
    }
  }

  public static void validatePhone(String phone) {
    // Not null
    // 숫자와 "+"기호만 존재
    if (phone == null || !phone.matches("[+]?[0-9]+")) {
      throw new ValidateUtilsException(ErrorCode.INVALID_PHONE_FORMAT);
    }
  }

  public static void validateName(String name) {
    // Not null
    // 20자 이하
    if (name == null || name.length() > 20) {
      throw new ValidateUtilsException(ErrorCode.INVALID_NAME_FORMAT);
    }
  }

  public static void validateDescription(String description) {
    // 500자 이하
    if(description.length() > 500){
      throw new ValidateUtilsException(ErrorCode.INVALID_DESCRIPTION_FORMAT);
    }
  }
}
