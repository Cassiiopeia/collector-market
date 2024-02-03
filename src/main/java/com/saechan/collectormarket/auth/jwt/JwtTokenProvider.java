package com.saechan.collectormarket.auth.jwt;

import com.saechan.collectormarket.auth.exception.TokenException;
import com.saechan.collectormarket.global.exception.ErrorCode;
import com.saechan.collectormarket.member.model.type.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtTokenProvider {
  private final String secretKey;
  private final long expireTime;

  public JwtTokenProvider(
      @Value("${spring.security.jwt.secret}")
      String secretKey,

      @Value("${spring.security.jwt.expire}")
      long expireTime
  ){
    this.secretKey = secretKey;
    this.expireTime = expireTime;
  }

  public String createToken(String username, UserRole role){
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("role", role.name());
    Date now = new Date();
    Date validTime = new Date(now.getTime() + expireTime);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validTime)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  public boolean validateToken(String token){
    try {
      Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
      log.info("Token is valid");
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | io.jsonwebtoken.MalformedJwtException e) {
      throw new TokenException(ErrorCode.INVALID_JWT_SIGNATURE);
    } catch (io.jsonwebtoken.ExpiredJwtException e) {
      throw new TokenException(ErrorCode.EXPIRED_JWT_TOKEN);
    } catch (io.jsonwebtoken.UnsupportedJwtException e) {
      throw new TokenException(ErrorCode.UNSUPPORTED_JWT_TOKEN);
    }
  }

  public Authentication getAuthentication(String token){
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
    String roleString = claims.get("role",String.class);
    List<SimpleGrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority(roleString));

    User principal = new User(claims.getSubject(), "",authorities);
    return new UsernamePasswordAuthenticationToken(principal,"",authorities);
  }
}
