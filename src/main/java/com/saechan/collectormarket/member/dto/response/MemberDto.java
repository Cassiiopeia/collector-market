package com.saechan.collectormarket.member.dto.response;

import com.saechan.collectormarket.member.model.entity.Member;
import com.saechan.collectormarket.member.model.type.UserRole;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MemberDto {

  private String email;

  private String password;

  private String name;

  private String phone;

  private Double point;

  private UserRole role;

  private String emailAuthCode;

  private LocalDateTime emailAuthCreateDt;

  private boolean activated;

  private Long storeId;

  public static MemberDto from(Member member) {
    return MemberDto.builder()
        .email(member.getEmail())
        .password(member.getPassword())
        .name(member.getName())
        .phone(member.getPhone())
        .point(member.getPoint())
        .role(member.getRole())
        .emailAuthCode(member.getEmailAuthCode())
        .emailAuthCreateDt(member.getEmailAuthCreateDt())
        .activated(member.getActivated())
        .storeId(member.getStore() == null ? -1 : member.getStore().getId())
        .build();
  }

}

