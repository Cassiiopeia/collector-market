package com.saechan.collectormarket.member.dto.request;


import com.saechan.collectormarket.global.util.ValidationUtils;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSignInForm {
  private String email;
  private String password;

  public void validate(){
    ValidationUtils.validateEmail(email);
  }
}
