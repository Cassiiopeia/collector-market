package com.saechan.collectormarket.member.dto.request;

import com.saechan.collectormarket.global.util.ValidationUtils;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberSignUpForm {

  private String name;

  private String email;

  private String password;

  private String phone;

  public void validate() {
    ValidationUtils.validateEmail(email);
    ValidationUtils.validatePassword(password);
    ValidationUtils.validateName(name);
    ValidationUtils.validatePhone(phone);
  }
}