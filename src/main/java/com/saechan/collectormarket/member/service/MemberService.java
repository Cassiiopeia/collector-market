package com.saechan.collectormarket.member.service;

import com.saechan.collectormarket.auth.jwt.JwtTokenProvider;
import com.saechan.collectormarket.auth.service.MailService;
import com.saechan.collectormarket.member.dto.request.MemberSignInForm;
import com.saechan.collectormarket.member.dto.request.MemberSignUpForm;
import com.saechan.collectormarket.member.exception.MemberException;
import com.saechan.collectormarket.global.excpetion.ErrorCode;
import com.saechan.collectormarket.member.model.entity.Member;
import com.saechan.collectormarket.member.model.repository.MemberRepository;
import com.saechan.collectormarket.member.model.type.UserRole;
import com.saechan.collectormarket.store.service.StoreService;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  private final MailService mailService;
  private final StoreService storeService;

  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  public Member signUp(MemberSignUpForm form) {

    // 형식 검증
    form.validate();

    // 이메일로 회원 검색
    Optional<Member> existMember = memberRepository.findByEmail(
        form.getEmail());

    // 활성화된 이메일이 존재한 경우
    if (existMember.isPresent() && existMember.get().getActivated()) {
      throw new MemberException(ErrorCode.ALREADY_EXIST_EMAIL);
    }

    // 비활성화된 이메일이 존재한 경우
    if (existMember.isPresent() && !existMember.get().getActivated()) {
      String emailAuthCode = UUID.randomUUID().toString().replaceAll("-", "");
      mailService.sendEmailAuth(form.getEmail(), emailAuthCode);
      return existMember.get();
    }

    // 이메일 인증코드 생성
    String emailAuthCode = UUID.randomUUID().toString().replaceAll("-", "");
    mailService.sendEmailAuth(form.getEmail(), emailAuthCode);

    // 비밀번호 암호화
    String encodedPassword = passwordEncoder.encode(form.getPassword());

    // 회원 생성
    Member signUpMember = Member.builder()
        .email(form.getEmail())
        .password(encodedPassword)
        .name(form.getName())
        .phone(form.getPhone())
        .point((double) 0)
        .role(UserRole.ROLE_USER)
        .emailAuthCode(emailAuthCode)
        .emailAuthCreateDt(LocalDateTime.now())
        .activated(false)
        .store(null)
        .build();

    // 회원 저장
    memberRepository.save(signUpMember);

    return signUpMember;
  }

  public void proceedEmailAuth(String emailAuthCode) {
    // 이메일코드로 회원 검색
    Member member = memberRepository.findByEmailAuthCode(emailAuthCode)
        .orElseThrow(
            () -> new MemberException(ErrorCode.INVALID_EMAIL_AUTH_CODE));

    // 인증 만료 시간 (30분) 유효 확인
    if (member.getEmailAuthCreateDt().plusMinutes(30)
        .isBefore(LocalDateTime.now())) {
      throw new MemberException(ErrorCode.EXPIRED_EMAIL_AUTH_CODE);
    }

    // 신규회원일경우 기본 store 생성
    if (member.getStore() == null) {
      member.setStore(storeService.createStore(member));
    }

    // 회원 활성화, 이메일인증 관련 null 처리
    member.setActivated(true);
    member.setEmailAuthCode(null);
    member.setEmailAuthCreateDt(null);

    memberRepository.save(member);
  }

  public String signIn(MemberSignInForm form) {

    // 형식 검증
    form.validate();

    // 회원 검증
    Member member = verifyMemberFromEmail(form.getEmail());

    // 비밀번호 확인
    if (!passwordEncoder.matches(form.getPassword(), member.getPassword())) {
      throw new MemberException(ErrorCode.INVALID_PASSWORD);
    }

    return jwtTokenProvider.createToken(member.getEmail(), member.getRole());
  }


  // 이메일 정보로 회원 검색 및 유효성 검증
  private Member verifyMemberFromEmail(String email) {

    // 회원 존재 확인
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

    // 비활성화 회원 확인
    if (!member.getActivated()) {
      throw new MemberException(ErrorCode.MEMBER_NOT_ACTIVATED);
    }
    return member;
  }

}

