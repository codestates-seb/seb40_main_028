package com.seb028.guenlog.config.auth.jwt.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.seb028.guenlog.config.auth.JwtTokenizer;
import com.seb028.guenlog.member.dto.LoginDto;
import com.seb028.guenlog.member.entity.Member;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtTokenizer tokenizer;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(JwtTokenizer tokenizer, AuthenticationManager authenticationManager) {
        this.tokenizer = tokenizer;
        this.authenticationManager = authenticationManager;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
        //login 유저의 username과 password를 DB와 비교
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authResult) throws IOException, ServletException {
        Member member = (Member)authResult.getPrincipal();
        //로그인에 성공한 경우 해당 객체 정보를 참고해 AccessToken과 RefreshToken 생성
        String accessToken = delegateAccessToken(member);
        String refreshToken = delegateRefreshToken(member);
        //Http Header에 AccessToken, Refresh 토큰 추가
        response.setHeader("Authorization", "Bearer "+ accessToken);
        response.setHeader("Refresh", refreshToken);

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

    public String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", member.getNickname());
        claims.put("roles", member.getRoles());
        claims.put("userId", member.getId());
        //Payload에 username, roles, userId로 구성
        String subject = member.getEmail();
        //토큰 유지 시간으로 accessToken Expiration 설정
        Date expiration = tokenizer.getExpiration(tokenizer.getAccessTokenExpirationMinutes());
        //secret-key 설정
        String base64EncodedSecretKey = tokenizer.encodeBase64SecretKey(tokenizer.getSecretKey());
        //AccessToken 생성
        String accessToken = tokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    public String delegateRefreshToken(Member member) {
        String subject = member.getEmail();
        Date expiration = tokenizer.getExpiration(tokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = tokenizer.encodeBase64SecretKey(tokenizer.getSecretKey());
        //refreshToken 생성
        String refreshToken = tokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }
}
