package com.seb028.guenlog.config.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seb028.guenlog.config.auth.JwtTokenizer;
import com.seb028.guenlog.config.auth.utils.CustomAuthorityUtils;
import com.seb028.guenlog.exception.BusinessLogicException;
import com.seb028.guenlog.exception.ExceptionCode;
import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.repository.MemberRepository;
import com.seb028.guenlog.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        var oauth2User = (OAuth2User)authentication.getPrincipal();
        String email = String.valueOf(oauth2User.getAttributes().get("email"));
        List<String> authority = customAuthorityUtils.createRoles(email);
        String nickname = String.valueOf(oauth2User.getAttributes().get("name"));
        String password = "oauth2user!";



//        log.info("loginUser -> {}", oauth2Member);
//        if(!memberRepository.findByEmail(email).isEmpty()) {
//            memberRepository.save(oauth2Member);  //저장을 하면 중복 에러가 발생하고, 저장을 안하면 아래에서 nullpoint에러가 뜨고
//        }
//                saveMember(email, nickname, password);
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        System.out.println("member email : " + findMember.getEmail() + "member Id : " + findMember.getId());
        System.out.println("member initial Login status : " + findMember.getInitialLogin());
        System.out.println("member nickname : " + findMember.getNickname());
        Member oauth2Member = Member.builder()
                .email(email)
                .nickname(nickname)
                .password(getPasswordEncoder().encode(password))
                .initialLogin(findMember.getInitialLogin())
                .build();
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
