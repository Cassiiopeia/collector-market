package com.saechan.collectormarket.auth.config;

import com.saechan.collectormarket.auth.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtTokenFilter jwtTokenFilter;

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http)
      throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
            // 모두 허용
            .requestMatchers(
                "/docs/**", // swagger
                "/v3/api-docs/**", // swagger
                "/member/signup", // 회원가입
                "/member/email-auth/**", // 이메일 인증
                "/member/signin" // 로그인 경로 허용
            ).permitAll()
            // 고객만 허용
            .requestMatchers(HttpMethod.PUT, "/member/update").hasRole("USER") // 회원 정보 수정
            .requestMatchers(HttpMethod.POST, "/member/changeEmail").hasRole("USER") // 회원 이메일 수정
            .requestMatchers(HttpMethod.GET, "/member/profile").hasRole("USER") // 회원 정보 조회
            .requestMatchers(HttpMethod.DELETE, "/member/delete").hasRole("USER") // 회원 삭제
            .requestMatchers(HttpMethod.PUT, "/store/update").hasRole("USER") // 상점정보 수정
            .requestMatchers(HttpMethod.GET, "/store/profile").hasRole("USER") // 상점정보 조회
            .requestMatchers(HttpMethod.POST, "/point/charge").hasRole("USER") // 포인트 충전
            .requestMatchers(HttpMethod.POST, "/point/withDraw").hasRole("USER") // 포인트 출금(내보내기)
            .requestMatchers(HttpMethod.GET, "/point/history/**").hasRole("USER") // 포인트 거래내역 조회
            .requestMatchers(HttpMethod.POST, "/product/create").hasRole("USER") // 상품 생성
            .requestMatchers(HttpMethod.PUT, "/product/update").hasRole("USER") // 상품 수정(업데이트)
            .requestMatchers(HttpMethod.GET, "/product/view").hasRole("USER") // 상품 수정(업데이트)


            // 관리자만 허용
            .requestMatchers(HttpMethod.PUT, "/admin/*").hasRole("ADMIN")
            // 그 외는 인증 필요
            .anyRequest().authenticated())

        // jwt filter 추가
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}