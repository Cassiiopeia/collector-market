package com.saechan.collectormarket.global.util.constraint;

import com.saechan.collectormarket.global.util.validator.PhoneValidator;
import java.lang.annotation.*;
import javax.validation.*;

@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhone {
  String message() default "전화번호 형식이 올바르지 않습니다.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}