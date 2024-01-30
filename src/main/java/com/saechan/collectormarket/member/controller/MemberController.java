package com.saechan.collectormarket.member.controller;

import com.saechan.collectormarket.member.dto.request.MemberSignInForm;
import com.saechan.collectormarket.member.dto.request.MemberSignUpForm;
import com.saechan.collectormarket.member.dto.response.MemberDto;
import com.saechan.collectormarket.member.service.MemberService;
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
      @RequestBody MemberSignUpForm form
  ){
    MemberDto memberDto = memberService.signUp(form);
    return ResponseEntity.ok(memberDto);
  }

  @GetMapping("/email-auth")
  public ResponseEntity<Void> emailAuth(
      @RequestParam("code") String emailAuthCode
  ){
    memberService.proceedEmailAuth(emailAuthCode);
    return ResponseEntity.noContent().build();
  }
  @PostMapping("/signin")
  public ResponseEntity<String> signIn(
      @RequestBody MemberSignInForm form
  ){
    String token = memberService.signIn(form);
    log.info("signin token created : {}",token);
    return ResponseEntity.ok(token);
  }


}
