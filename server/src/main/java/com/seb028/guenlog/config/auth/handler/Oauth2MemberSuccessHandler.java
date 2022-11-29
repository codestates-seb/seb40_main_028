package com.seb028.guenlog.config.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seb028.guenlog.config.auth.JwtTokenizer;
import com.seb028.guenlog.config.auth.utils.CustomAuthorityUtils;
import com.seb028.guenlog.exception.BusinessLogicException;
import com.seb028.guenlog.exception.ExceptionCode;
import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
@Slf4j
@Component
public class Oauth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils customAuthorityUtils;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Oauth2MemberSuccessHandler(JwtTokenizer jwtTokenizer, CustomAuthorityUtils customAuthorityUtils, MemberRepository memberRepository) {
        this.jwtTokenizer = jwtTokenizer;
        this.customAuthorityUtils = customAuthorityUtils;
        this.memberRepository = memberRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //OAuth2 로그인 사용자 정보
        var oauth2User = (OAuth2User)authentication.getPrincipal();
        String email = String.valueOf(oauth2User.getAttributes().get("email"));
        List<String> authority = customAuthorityUtils.createRoles(email);
        String nickname = String.valueOf(oauth2User.getAttributes().get("name"));
        String password = "oauth2user!";

        //DB에서 email를 통해 사용자 정보 확인
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        //임시 객체 생성
        Member oauth2Member = Member.builder()
                .email(email)
                .nickname(nickname)
                .initialLogin(findMember.getInitialLogin())
                .build();
        //사용자가 최초 로그인을 시도하면 responseBody에 사용자 임시 정보를 출력
        if(!findMember.getInitialLogin()) {
            String result = objectMapper.writeValueAsString(oauth2Member);
            //프론트에서 initialLogin : false 상태를 확인하는 로직 구현
            response.getWriter().write(result);
        }
        //사용자 생성 정보로 토큰 생성
        String accessToken = delegateAccessToken(findMember);
        String refreshToken = delegateRefreshToken(findMember);

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("RefreshToken", refreshToken);
    }

    public String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", member.getNickname());
        claims.put("roles", member.getRoles());
        claims.put("userId", member.getId());
        //Payload에 username, roles, userId로 구성
        String subject = member.getEmail();
        //토큰 유지 시간으로 accessToken Expiration 설정
        Date expiration = jwtTokenizer.getExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        //secret-key 설정
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        //AccessToken 생성
        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    public String delegateRefreshToken(Member member) {
        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        //refreshToken 생성
        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }

}
