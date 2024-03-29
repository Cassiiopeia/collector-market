package com.saechan.collectormarket.member.dto.request;


import com.saechan.collectormarket.global.util.constraint.ValidEmail;
import com.saechan.collectormarket.global.util.constraint.ValidPassword;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSignInForm {

  @ValidEmail
  private String email;

  @ValidPassword
  private String password;
}
