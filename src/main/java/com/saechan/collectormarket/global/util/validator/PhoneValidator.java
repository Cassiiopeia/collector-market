package com.saechan.collectormarket.global.util.validator;

import com.saechan.collectormarket.global.util.constraint.ValidPhone;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

  @Override
  public void initialize(ValidPhone constraintAnnotation) {
  }

  @Override
  public boolean isValid(String phone, ConstraintValidatorContext context) {
    // not null
    // 처음에 + 기호가 있어도 되고 없어도됨
    // -는 허용하지않음.
    // 숫자만 허용
    return phone != null && phone.matches("^\\+?[0-9]+$");

  }
}