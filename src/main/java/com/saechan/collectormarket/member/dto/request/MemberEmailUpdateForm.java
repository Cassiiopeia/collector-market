package com.saechan.collectormarket.member.dto.request;

import com.saechan.collectormarket.global.util.constraint.ValidEmail;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class MemberEmailUpdateForm {

  @ValidEmail
  private String email;

}
