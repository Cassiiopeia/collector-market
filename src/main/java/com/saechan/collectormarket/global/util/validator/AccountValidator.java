package com.saechan.collectormarket.global.util.validator;

import com.saechan.collectormarket.global.util.constraint.ValidAccount;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountValidator implements ConstraintValidator<ValidAccount, String> {
  // 숫자여야함
  @Override
  public boolean isValid(String accountNumber, ConstraintValidatorContext context) {
    return accountNumber != null && accountNumber.matches("[0-9]+");
  }
}