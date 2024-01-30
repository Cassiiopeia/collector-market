package com.saechan.collectormarket.global.util.constraint;

import com.saechan.collectormarket.global.util.validator.PasswordValidator;
import java.lang.annotation.*;
import javax.validation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
  String message() default "비밀번호 형식이 올바르지 않습니다.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
