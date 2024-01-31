package com.saechan.collectormarket.global.util.validator;

import com.saechan.collectormarket.global.util.constraint.ValidName;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {

  @Override
  public void initialize(ValidName constraintAnnotation) {
  }

  @Override
  public boolean isValid(String name, ConstraintValidatorContext context) {
    // not null
    // 20자리를 넘으면 안됨
    // 특수문자가 있으면 안됨
    // 숫자가 있으면 안됨
    return name != null && name.length() <= 20 && !name.matches(".*[\\d!@#$%^&*].*");
  }
}
