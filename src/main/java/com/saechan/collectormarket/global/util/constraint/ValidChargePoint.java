package com.saechan.collectormarket.global.util.constraint;

import com.saechan.collectormarket.global.util.validator.ChargePointValidator;
import java.lang.annotation.*;
import javax.validation.*;

@Documented
@Constraint(validatedBy = ChargePointValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidChargePoint {
  String message() default "충전 포인트 형식이 올바르지 않습니다.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
