package com.saechan.collectormarket.member.service;

import com.saechan.collectormarket.global.exception.ErrorCode;
import com.saechan.collectormarket.member.exception.MemberException;
import com.saechan.collectormarket.member.model.entity.Member;
import com.saechan.collectormarket.member.model.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberUtils {
  private static MemberRepository memberRepository;

  @Autowired
  public MemberUtils(MemberRepository repo) {
    memberRepository = repo;
  }

  // 회원 유효성 검증
  public static Member verifyMemberFromEmail(String email) {
    // 회원 존재 확인
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

    // 비활성화 회원 확인
    if (!member.getActivated()) {
      throw new MemberException(ErrorCode.MEMBER_NOT_ACTIVATED);
    }
    return member;
  }

  // 회원 유효성 검증 : Pessimistic Lock
  public static Member verifyMemberFromEmailWithLock(String email){
    // 회원 존재 확인
    Member member = memberRepository.findByEmailWithLock(email)
        .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

    // 비활성화 회원 확인
    if (!member.getActivated()) {
      throw new MemberException(ErrorCode.MEMBER_NOT_ACTIVATED);
    }
    return member;
  }
}
