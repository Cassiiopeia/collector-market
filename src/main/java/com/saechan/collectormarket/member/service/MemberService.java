package com.saechan.collectormarket.member.service;

import com.saechan.collectormarket.member.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;

}
