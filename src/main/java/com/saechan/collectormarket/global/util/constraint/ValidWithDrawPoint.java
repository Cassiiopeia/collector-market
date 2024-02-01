package com.saechan.collectormarket.global.util.constraint;

import com.saechan.collectormarket.global.util.validator.WithDrawPointValidator;
import java.lang.annotation.*;
import javax.validation.*;

@Documented
@Constraint(validatedBy = WithDrawPointValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidWithDrawPoint {
  String message() default "출금 포인트 형식이 올바르지 않습니다.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}