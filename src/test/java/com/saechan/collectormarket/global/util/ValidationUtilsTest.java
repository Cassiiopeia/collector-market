package com.saechan.collectormarket.global.util;


import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValidationUtilsTest {

  @Test
  void validateEmail() {
  }

  @Test
  void validatePassword() {
  }

  @Test
  void validatePhone() {
  }

  @Test
  @DisplayName("Name: 성공 :올바론 이름")
  void validateName_Success() {
    // 정상적인 이름에 대한 테스트
    ValidationUtils.validateName("올바른이름");
  }

  @Test
  @DisplayName("Name: 실패 :null")
  void validateName_Fail_Null() {
    assertThrows(ValidateUtilsException.class, () -> ValidationUtils.validateName(null));
  }

  @Test
  @DisplayName("Name: 실패 :길이가 20자를 초과")
  void validateName_Fail_TooLong() {
    assertThrows(ValidateUtilsException.class, () -> ValidationUtils.validateName("이름이너무길면예외가발생합니다이름이너무길면예외가발생합니다"));
  }

  @Test
  void validateDescription() {
  }
}