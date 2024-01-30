package com.saechan.collectormarket.member.dto.request;

import com.saechan.collectormarket.global.util.constraint.ValidPassword;
import com.saechan.collectormarket.global.util.constraint.ValidPhone;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberUpdateForm {

  @NotNull
  @Size(max = 20, message = "이름의 길이는 20자 이하여야합니다.")
  private String name;

  @ValidPassword
  private String password;

  @ValidPhone
  private String phone;
}