package com.saechan.collectormarket.global.util;

// ValidationTest.java

import static org.junit.jupiter.api.Assertions.*;

import com.saechan.collectormarket.member.dto.request.MemberSignUpForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import javax.validation.ConstraintViolation;

class ValidationTest {

  private Validator validator;
  private MemberSignUpForm form;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();

    form = MemberSignUpForm.builder()
        .name("suhsaechan")
        .email("chan46760@naver.com")
        .password("chan4760@")
        .phone("01066667777")
        .build();
  }

  @Test
  @DisplayName("이메일 유효성 검증")
  void emailValidation() {

    Set<ConstraintViolation<MemberSignUpForm>> violations = validator.validate(form);
    System.out.println(violations.toString());

    // 정상 이메일
    assertTrue(violations.isEmpty());

    // @가 없음
    form.setEmail("chan4760");
    violations = validator.validate(form);
    assertFalse(violations.isEmpty());
  }

  @Test
  @DisplayName("비밀번호 유효성 검증")
  void passwordValidation() {
    Set<ConstraintViolation<MemberSignUpForm>> violations = validator.validate(form);

    // 정상 비밀번호
    assertEquals(true, violations.isEmpty());

    // 비정상 비밀번호: 특수문자 없음
    form.setPassword("chan4760");
    violations = validator.validate(form);
    assertEquals(false, violations.isEmpty());

    // 비정상 비밀번호: 숫자 없음
    form.setPassword("chanchan@");
    violations = validator.validate(form);
    assertEquals(false, violations.isEmpty());

    // 비정상 비밀번호: 8자 이하
    form.setPassword("c4760@");
    violations = validator.validate(form);
    assertEquals(false, violations.isEmpty());

    // 비정상 비밀번호: 20자 이상
    form.setPassword("chanchanchanchanchanchanchanchan178@");
    violations = validator.validate(form);
    assertEquals(false, violations.isEmpty());
  }

  @Test
  @DisplayName("전화번호 유효성 검증")
  void phoneValidation() {
    // 정상 전화번호
    Set<ConstraintViolation<MemberSignUpForm>> violations = validator.validate(form);
    assertEquals(true, violations.isEmpty());

    // 비정상 전화번호: 영어
    form.setPhone("a01066667777");
    violations = validator.validate(form);
    assertEquals(false, violations.isEmpty());

    // 비정상 전화번호: +이외 특수문자
    form.setPhone("-01066667777");
    violations = validator.validate(form);
    assertEquals(false, violations.isEmpty());
  }

}
