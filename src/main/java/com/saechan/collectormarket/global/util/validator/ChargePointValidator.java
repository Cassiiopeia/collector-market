package com.saechan.collectormarket.global.util.validator;

import com.saechan.collectormarket.global.util.constraint.ValidChargePoint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChargePointValidator implements ConstraintValidator<ValidChargePoint, Double> {
  // NOT NULL
  // 충전 최소 금액 1000원
  // 회당 최대 충전 금액한도 500만원
  // 충전 금액은 양수
  @Override
  public boolean isValid(Double chargePoint, ConstraintValidatorContext context) {
    return chargePoint != null && chargePoint >= 1000 && chargePoint <= 5000000;
  }
}