package com.saechan.collectormarket.member.dto.request;

import com.saechan.collectormarket.global.util.constraint.ValidEmail;
import com.saechan.collectormarket.global.util.constraint.ValidPassword;
import com.saechan.collectormarket.global.util.constraint.ValidPhone;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MemberSignUpForm {

  @NotNull
  @Size(max = 20, message = "이름의 길이는 20자 이하여야합니다.")
  private String name;

  @ValidEmail
  private String email;

  @ValidPassword
  private String password;

  @ValidPhone
  private String phone;
}