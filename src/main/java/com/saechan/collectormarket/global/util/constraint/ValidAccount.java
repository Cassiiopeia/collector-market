package com.saechan.collectormarket.global.util.constraint;

import com.saechan.collectormarket.global.util.validator.AccountValidator;
import java.lang.annotation.*;
import javax.validation.*;

@Documented
@Constraint(validatedBy = AccountValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAccount{
  String message() default "계좌번호 형식이 올바르지 않습니다.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}