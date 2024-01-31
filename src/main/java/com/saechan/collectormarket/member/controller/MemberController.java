package com.saechan.collectormarket.member.controller;

import com.saechan.collectormarket.member.dto.request.MemberEmailUpdateForm;
import com.saechan.collectormarket.member.dto.request.MemberSignInForm;
import com.saechan.collectormarket.member.dto.request.MemberSignUpForm;
import com.saechan.collectormarket.member.dto.request.MemberUpdateForm;
import com.saechan.collectormarket.member.dto.response.MemberDto;
import com.saechan.collectormarket.member.dto.response.MemberProfileDto;
import com.saechan.collectormarket.member.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/signup")
  public ResponseEntity<MemberDto> signUp(
      @Valid @RequestBody MemberSignUpForm form
  ) {
    MemberDto memberDto = memberService.signUp(form);
    return ResponseEntity.ok(memberDto);
  }

  @GetMapping("/email-auth")
  public ResponseEntity<Void> emailAuth(
      @RequestParam("code") String emailAuthCode
  ) {
    memberService.proceedEmailAuth(emailAuthCode);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/signin")
  public ResponseEntity<String> signIn(
      @Valid @RequestBody MemberSignInForm form
  ) {
    String token = memberService.signIn(form);
    log.info("signin token created : {}", token);
    return ResponseEntity.ok(token);
  }

  @PutMapping("/update")
  public ResponseEntity<MemberDto> updateExceptEmail(
      Authentication authentication,
      @Valid @RequestBody MemberUpdateForm form
  ) {
    String memberEmail = authentication.getName();
    MemberDto memberDto = memberService.updateExceptEmail(memberEmail, form);
    return ResponseEntity.ok(memberDto);
  }

  @PostMapping("/changeEmail")
  public ResponseEntity<MemberDto> updateEmail(
      Authentication authentication,
      @Valid @RequestBody MemberEmailUpdateForm form
  ) {
    String memberEmail = authentication.getName();
    MemberDto memberDto = memberService.updateEmail(memberEmail, form);

    return ResponseEntity.ok(memberDto);
  }

  @GetMapping("/profile")
  public ResponseEntity<MemberProfileDto> profile(
      Authentication authentication
  ) {
    String memberEmail = authentication.getName();
    MemberProfileDto memberProfileDto = memberService.getProfile(memberEmail);

    return ResponseEntity.ok(memberProfileDto);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Void> delete(
      Authentication authentication
  ) {
    String memberEmail = authentication.getName();
    memberService.delete(memberEmail);
    return ResponseEntity.noContent().build();
  }

}
