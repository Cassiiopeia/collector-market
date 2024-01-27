package com.saechan.collectormarket.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;

  private final AntPathMatcher antPathMatcher = new AntPathMatcher();

  private static final String BEARER = "Bearer ";

  // Security와 JWT 인증 생략하는 URI
  private static final String[] WHITELIST = {
      "/docs/**", // swagger
      "/v3/api-docs/**" // swagger
  };

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String URI = request.getRequestURI();
    if (Arrays.stream(WHITELIST)
        .anyMatch(whiteListUri -> antPathMatcher.match(whiteListUri, URI))) {
      // Token 검사 생략
      filterChain.doFilter(request, response);
      return;
    }

    String token = resolveToken(request);
    jwtTokenProvider.validateToken(token);
    Authentication authentication = jwtTokenProvider.getAuthentication(token);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    response.setHeader("Authorization", BEARER + token);
    filterChain.doFilter(request, response);
  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith(BEARER)) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
