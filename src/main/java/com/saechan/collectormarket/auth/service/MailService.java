package com.saechan.collectormarket.auth.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

  private final JavaMailSender javaMailSender;


  @Value("${domain.address}")
  String address;

  @Value("${server.port}")
  String port;

  private final String EMAIL_AUTH_ADDRESS = "/member/email-auth";
  public void sendMail(String email, String subject, String text) {
    MimeMessagePreparator msg = new MimeMessagePreparator() {
      @Override
      public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper helper = new MimeMessageHelper(
            mimeMessage, true, "UTF-8"
        );

        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(text, true);
      }
    };
    javaMailSender.send(msg);
  }

  public void sendEmailAuth(String email, String emailAuthCode) {
    MimeMessagePreparator msg = new MimeMessagePreparator() {
      @Override
      public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper helper = new MimeMessageHelper(
            mimeMessage, true, "UTF-8"
        );

        helper.setTo(email);
        helper.setSubject("[콜렉터마켓] 회원가입 이메일인증");
        String link = "http://" + address + ":" + port + EMAIL_AUTH_ADDRESS
            + "?code=" + emailAuthCode;
        log.info("sendEmailAuth link -> {}", link);
        helper.setText("<p>회원가입을 환영합니다.</p>\n"
            + "<p>아래 링크를 클릭하여 이메일을 인증하세요:</p>\n"
            + "<a href=\"" + link + "\">이메일 인증하기</a>", true);
      }
    };
    javaMailSender.send(msg);
  }
}
