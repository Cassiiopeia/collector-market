package com.saechan.collectormarket.member.dto.response;

import com.saechan.collectormarket.member.model.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberProfileDto {

  private String email;

  private String name;

  private String phone;

  private Double point;

  private Long storeId;

  public static MemberProfileDto from(Member member) {
    return MemberProfileDto.builder()
        .email(member.getEmail())
        .name(member.getName())
        .phone(member.getPhone())
        .point(member.getPoint())
        .storeId(Member.getStoreId(member))
        .build();

  }

}
