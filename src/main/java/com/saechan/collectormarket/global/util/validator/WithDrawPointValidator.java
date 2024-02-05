package com.saechan.collectormarket.global.util.validator;

import com.saechan.collectormarket.global.util.constraint.ValidWithDrawPoint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WithDrawPointValidator implements ConstraintValidator<ValidWithDrawPoint, Double> {
  // NOT NULL
  // 출금 금액은 0원 보다 커야함
  // 출금 금액은 양수
  @Override
  public boolean isValid(Double withDrawPoint, ConstraintValidatorContext context) {
    return withDrawPoint != null && withDrawPoint > 0;
  }
}