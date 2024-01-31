package com.saechan.collectormarket.global.util.validator;

import com.saechan.collectormarket.global.util.constraint.ValidPassword;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

  @Override
  public void initialize(ValidPassword constraintAnnotation) {
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    // not null
    // 최소 하나의 숫자 포함
    // 최소 하나의 소문자 알파벳 포함
    // 최소 하나의 특수문자 !@#$%^&* 포함
    // 최소 8자리 ~ 최대 20자리
    return password != null && password.matches("((?=.*\\d)(?=.*[a-z])(?=.*[!@#$%^&*]).{8,20})");
  }
}
