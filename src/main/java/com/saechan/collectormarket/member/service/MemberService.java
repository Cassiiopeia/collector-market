package com.saechan.collectormarket.member.service;

import com.saechan.collectormarket.auth.jwt.JwtTokenProvider;
import com.saechan.collectormarket.auth.service.MailService;
import com.saechan.collectormarket.member.dto.request.MemberEmailUpdateForm;
import com.saechan.collectormarket.member.dto.request.MemberSignInForm;
import com.saechan.collectormarket.member.dto.request.MemberSignUpForm;
import com.saechan.collectormarket.member.dto.request.MemberUpdateForm;
import com.saechan.collectormarket.member.dto.response.MemberDto;
import com.saechan.collectormarket.member.dto.response.MemberProfileDto;
import com.saechan.collectormarket.member.exception.MemberException;
import com.saechan.collectormarket.global.excpetion.ErrorCode;
import com.saechan.collectormarket.member.model.entity.Member;
import com.saechan.collectormarket.member.model.repository.MemberRepository;
import com.saechan.collectormarket.member.model.type.UserRole;
import com.saechan.collectormarket.store.service.StoreService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final MailService mailService;
  private final StoreService storeService;

  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  private final static int EMAIL_CERTIFICATION_EXPIRE_MINUTES = 30;

  public MemberDto signUp(MemberSignUpForm form) {

    return memberRepository.findByEmail(form.getEmail()).map(existMember -> {
      if (!existMember.getActivated()) {
        // 비활성화된 회원인 경우 (활성화 이메일 발송)
        String emailAuthCode = UUID.randomUUID().toString().replaceAll("-", "");
        mailService.sendEmailAuth(form.getEmail(), emailAuthCode);

        return MemberDto.from(existMember);
      } else {
        // 이메일이 존재하는경우
        throw new MemberException(ErrorCode.ALREADY_EXIST_EMAIL);
      }
    }).orElseGet(() -> {
      // 신규 회원인 경우

      // 이메일 인증코드 생성 및 메일 전송
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
      return MemberDto.from(memberRepository.save(signUpMember));
    });
  }

  @Transactional
  public void proceedEmailAuth(String emailAuthCode) {
    // 이메일코드로 회원 검색
    Member member = memberRepository.findByEmailAuthCode(emailAuthCode)
        .orElseThrow(
            () -> new MemberException(ErrorCode.INVALID_EMAIL_AUTH_CODE));

    // 인증 만료 시간 (30분) 유효 확인
    if (member.getEmailAuthCreateDt().plusMinutes(EMAIL_CERTIFICATION_EXPIRE_MINUTES)
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

    // 회원 검증
    Member member = verifyMemberFromEmail(form.getEmail());

    // 비밀번호 확인
    if (!passwordEncoder.matches(form.getPassword(), member.getPassword())) {
      throw new MemberException(ErrorCode.INVALID_PASSWORD);
    }

    return jwtTokenProvider.createToken(member.getEmail(), member.getRole());
  }

  @Transactional
  public MemberDto updateEmail(String memberEmail, MemberEmailUpdateForm form) {

    // 회원 검증
    Member member = verifyMemberFromEmail(memberEmail);

    // 이메일 인증코드 생성
    String emailAuthCode = UUID.randomUUID().toString().replaceAll("-", "");
    mailService.sendEmailAuth(form.getEmail(), emailAuthCode);

    // 이메일 인증 변경
    member.setEmail(form.getEmail());
    member.setEmailAuthCode(emailAuthCode);
    member.setEmailAuthCreateDt(LocalDateTime.now());
    member.setActivated(false);

    return MemberDto.from(memberRepository.save(member));
  }

  @Transactional
  public MemberDto updateExceptEmail(String memberEmail, MemberUpdateForm form) {

    // 회원 검증
    Member member = verifyMemberFromEmail(memberEmail);

    // 회원 정보 업데이트
    member.setName(form.getName());
    member.setPassword(passwordEncoder.encode(form.getPassword()));
    member.setPhone(form.getPhone());

    // 회원 저장
    return MemberDto.from(memberRepository.save(member));
  }

  @Transactional
  public void delete(String memberEmail) {
    Member member = verifyMemberFromEmail(memberEmail);

    // 포인트가 0원이어야 탈퇴 가능
    if (member.getPoint() != 0) {
      throw new MemberException(ErrorCode.CANT_DELETE_NONZERO_POINTS_MEMBER);
    }

    memberRepository.deleteById(member.getId());
  }

  @Transactional(readOnly = true)
  public MemberProfileDto getProfile(String memberEmail) {

    // 회원 검증
    return MemberProfileDto.from(verifyMemberFromEmail(memberEmail));
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

