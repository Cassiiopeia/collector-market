package com.saechan.collectormarket.member.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.saechan.collectormarket.auth.jwt.JwtTokenProvider;
import com.saechan.collectormarket.auth.service.MailService;
import com.saechan.collectormarket.global.excpetion.ErrorCode;
import com.saechan.collectormarket.member.dto.request.MemberEmailUpdateForm;
import com.saechan.collectormarket.member.dto.request.MemberSignInForm;
import com.saechan.collectormarket.member.dto.request.MemberSignUpForm;
import com.saechan.collectormarket.member.dto.request.MemberUpdateForm;
import com.saechan.collectormarket.member.dto.response.MemberDto;
import com.saechan.collectormarket.member.exception.MemberException;
import com.saechan.collectormarket.member.model.entity.Member;
import com.saechan.collectormarket.member.model.repository.MemberRepository;
import com.saechan.collectormarket.member.model.type.UserRole;
import com.saechan.collectormarket.store.model.entity.Store;
import com.saechan.collectormarket.store.service.StoreService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class MemberServiceTest {

  @Mock
  private MemberRepository memberRepository;
  @Mock
  private MailService mailService;
  @Mock
  private StoreService storeService;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private JwtTokenProvider jwtTokenProvider;

  @InjectMocks
  private MemberService memberService;

  private Member validMember;

  private MemberSignUpForm signUpForm;

  private MemberSignInForm signInForm;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    validMember = Member.builder()
        .id(1L)
        .email("chan4760@naver.com")
        .password("encodedPassword")
        .name("suhsaechan")
        .phone("01066667777")
        .point(0.0)
        .role(UserRole.ROLE_USER)
        .activated(true)
        .build();

    signUpForm = MemberSignUpForm.builder()
        .name("suhsaechan")
        .email("chan4760@naver.com")
        .password("chan4760@")
        .phone("01066667777")
        .build();

    signInForm = MemberSignInForm.builder()
        .email("chan4760@naver.com")
        .password("chan4760@")
        .build();

  }


  @Test
  @DisplayName("회원가입 성공")
  void signUp_Success() {
    // given
    when(memberRepository.findByEmail(anyString())).thenReturn(
        Optional.empty());
    when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
    when(memberRepository.save(any(Member.class))).thenReturn(validMember);

    // when
    MemberDto memberDto = memberService.signUp(signUpForm);

    // then
    assertNotNull(memberDto);
    verify(memberRepository, times(1)).save(any(Member.class));
    verify(mailService, times(1)).sendEmailAuth(anyString(), anyString());
    assertEquals("suhsaechan", memberDto.getName());
    assertEquals("chan4760@naver.com", memberDto.getEmail());
    assertEquals("encodedPassword", memberDto.getPassword());
    assertEquals("01066667777", memberDto.getPhone());
    assertEquals(0.0, memberDto.getPoint());
    assertEquals(UserRole.ROLE_USER, memberDto.getRole());
    assertTrue(memberDto.isActivated());
    assertEquals(-1, memberDto.getStoreId());

  }

  @Test
  @DisplayName("회원가입 실패 : 이메일 이미 존재할시")
  void signUp_Fail_EmailAlreadyExists() {
    //given
    when(memberRepository.findByEmail(anyString())).thenReturn(
        Optional.of(validMember));

    //when
    //then
    MemberException memberException = assertThrows(MemberException.class,
        () -> memberService.signUp(signUpForm));
    assertEquals(ErrorCode.ALREADY_EXIST_EMAIL, memberException.getErrorCode());
  }

  @Test
  @DisplayName("로그인 성공")
  void signIn_Success() {
    // given
    when(memberRepository.findByEmail(signInForm.getEmail())).thenReturn(
        Optional.of(validMember));
    when(passwordEncoder.matches(signInForm.getPassword(),
        validMember.getPassword())).thenReturn(true);
    when(jwtTokenProvider.createToken(anyString(), any())).thenReturn(
        "validToken");

    // when
    String token = memberService.signIn(signInForm);

    // then
    verify(memberRepository).findByEmail(signInForm.getEmail());
    verify(passwordEncoder).matches(signInForm.getPassword(),
        validMember.getPassword());
    assertEquals("validToken", token);
  }

  @Test
  @DisplayName("로그인 실패: 회원이 없음")
  void SignIn_Fail_MemberNotFound() {
    //given
    when(memberRepository.findByEmail(anyString())).thenReturn(
        Optional.empty());

    //when
    //then
    MemberException memberException = assertThrows(MemberException.class,
        () -> memberService.signIn(signInForm));
    assertEquals(ErrorCode.MEMBER_NOT_FOUND, memberException.getErrorCode());
  }

  @Test
  @DisplayName("로그인 실패: 비활성화된 회원")
  void SignIn_Fail_MemberNotActivated() {
    //given

    when(memberRepository.findByEmail(anyString())).thenReturn(
        Optional.of(validMember));
    validMember.setActivated(false);

    //when
    //then
    MemberException memberException = assertThrows(MemberException.class,
        () -> memberService.signIn(signInForm));
    assertEquals(ErrorCode.MEMBER_NOT_ACTIVATED,
        memberException.getErrorCode());
  }

  @Test
  @DisplayName("로그인 실패: 비밀번호 불일치")
  void SignIn_Fail_InvalidPassword() {
    //given
    when(memberRepository.findByEmail(anyString())).thenReturn(
        Optional.of(validMember));
    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

    //when
    //then
    MemberException memberException = assertThrows(MemberException.class,
        () -> memberService.signIn(signInForm));
    assertEquals(ErrorCode.INVALID_PASSWORD, memberException.getErrorCode());
  }

  // 이메일 인증 처리 (proceedEmailAuth) 메소드 테스트
  @Test
  @DisplayName("이메일 인증 처리 성공")
  void proceedEmailAuth_Success() {
    // given
    String emailAuthCode = "emailAuthCode";
    validMember.setEmailAuthCode(emailAuthCode);
    validMember.setEmailAuthCreateDt(LocalDateTime.now().minusMinutes(10));

    when(memberRepository.findByEmailAuthCode(emailAuthCode)).thenReturn(
        Optional.of(validMember));
    when(storeService.createStore(validMember)).thenReturn(
        new Store()); // 상점이 없을시 상점 생성

    // when
    memberService.proceedEmailAuth(emailAuthCode);

    // then
    assertTrue(validMember.getActivated());
    assertNull(validMember.getEmailAuthCode());
    assertNull(validMember.getEmailAuthCreateDt());

    assertNotNull(validMember.getStore());

    verify(memberRepository, times(1)).findByEmailAuthCode(emailAuthCode);
    verify(memberRepository, times(1)).save(validMember);
  }

  @Test
  @DisplayName("이메일 인증 처리 실패: 인증 코드 오류")
  void proceedEmailAuth_Fail_NoAuthCode() {
    // given
    String invalidAuthCode = "invalidAuthCode";

    // when
    when(memberRepository.findByEmailAuthCode(invalidAuthCode)).thenReturn(
        Optional.empty());

    // then
    MemberException memberException = assertThrows(MemberException.class,
        () -> memberService.proceedEmailAuth(invalidAuthCode));
    assertEquals(ErrorCode.INVALID_EMAIL_AUTH_CODE,
        memberException.getErrorCode());
  }

  @Test
  @DisplayName("이메일 인증 처리 실패: 인증 시간 초과")
  void proceedEmailAuth_Fail_ExpiredTime() {
    //given
    String emailAuthCode = "emailAuthCode";
    validMember.setEmailAuthCode(emailAuthCode);
    validMember.setEmailAuthCreateDt(LocalDateTime.now().minusMinutes(31));

    when(memberRepository.findByEmailAuthCode(emailAuthCode)).thenReturn(
        Optional.of(validMember));
    when(storeService.createStore(validMember)).thenReturn(
        new Store()); // 상점이 없을시 상점 생성

    //when
    //then
    MemberException memberException = assertThrows(MemberException.class,
        () -> memberService.proceedEmailAuth(emailAuthCode));
    assertEquals(ErrorCode.EXPIRED_EMAIL_AUTH_CODE,
        memberException.getErrorCode());
  }

  @Test
  @DisplayName("이메일 업데이트 성공")
  void updateEmail_Success() {
    // given
    String newEmail = "chan4760@gmail.com";
    MemberEmailUpdateForm emailUpdateForm = MemberEmailUpdateForm.builder()
        .email(newEmail)
        .build();
    when(memberRepository.findByEmail(anyString())).thenReturn(
        Optional.of(validMember));
    when(memberRepository.save(any(Member.class))).thenReturn(validMember);

    // when
    MemberDto result = memberService.updateEmail(validMember.getEmail(),
        emailUpdateForm);

    // then
    assertEquals(newEmail, result.getEmail());
    verify(memberRepository, times(1)).save(validMember);
  }

  @Test
  @DisplayName("회원 정보 업데이트 성공")
  void updateExceptEmail_Success() {
    // given
    MemberUpdateForm updateForm = MemberUpdateForm.builder()
        .name("sammy")
        .password("sammyPassword")
        .phone("01099998888")
        .build();
    when(memberRepository.findByEmail(anyString())).thenReturn(
        Optional.of(validMember));
    when(passwordEncoder.encode(updateForm.getPassword())).thenReturn(
        "encodedSammyPassword");
    when(memberRepository.save(any(Member.class))).thenReturn(validMember);

    // when
    MemberDto result = memberService.updateExceptEmail(validMember.getEmail(),
        updateForm);

    // then
    assertEquals("sammy", result.getName());
    assertEquals("encodedSammyPassword", result.getPassword());
    assertEquals("01099998888", result.getPhone());
    verify(memberRepository, times(1)).save(validMember);
  }


  @Test
  @DisplayName("회원 탈퇴 성공")
  void delete_Success() {
    //given
    when(memberRepository.findByEmail(anyString())).thenReturn(
        Optional.of(validMember));

    //when
    memberService.delete("chan4760@naver.com");

    //then
    verify(memberRepository, times(1)).deleteById(validMember.getId());
  }

  @Test
  @DisplayName("회원 탈퇴 실패: 포인트가 0이 아닐 경우")
  void delete_Fail_NonZeroPoints() {
    // given
    validMember.setPoint(10.0);
    when(memberRepository.findByEmail(anyString())).thenReturn(
        Optional.of(validMember));

    // when
    // then
    MemberException memberException = assertThrows(MemberException.class,
        () -> memberService.delete("chan4760@naver.com"));
    assertEquals(ErrorCode.CANT_DELETE_NONZERO_POINTS_MEMBER,
        memberException.getErrorCode());
  }


}