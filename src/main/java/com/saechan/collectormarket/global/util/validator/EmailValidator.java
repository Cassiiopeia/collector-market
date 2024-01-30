package com.saechan.collectormarket.global.util.validator;

import com.saechan.collectormarket.global.util.constraint.ValidEmail;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidator;

public class EmailValidator implements ConstraintValidator<ValidEmail,String>{

  @Override
  public void initialize(ValidEmail email){
  }
  @Override
  public boolean isValid(String emailField, ConstraintValidatorContext cxt) {
    return emailField != null && emailField.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
  }
}
