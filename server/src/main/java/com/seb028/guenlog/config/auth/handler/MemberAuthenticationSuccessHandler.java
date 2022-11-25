package com.seb028.guenlog.config.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seb028.guenlog.exception.BusinessLogicException;
import com.seb028.guenlog.exception.ExceptionCode;
import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MemberRepository memberRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                 Authentication authentication) throws IOException, ServletException {
        //로그인 시도한 이메일로 DB에 저장된 객체를 검색
        Optional<Member> optionalMember = memberRepository.findByEmail(authentication.getName());
        //Member 객체로 불러옴
        Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        //헤더에 사용자 최초 로그인 상태 전송
        response.setHeader("InitialLogin", findMember.getInitialLogin().toString());
        response.addHeader("InitialLogin", findMember.getInitialLogin().toString());
        //ResponseBody를 Json 형식으로 보여주도록 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //새로운 멤버 객체에 로그인 시도하는 사용자의 이메일과 최초 로그인 상태를 저장
        Member helloMember = new Member();
        helloMember.setInitialLogin(findMember.getInitialLogin());
        helloMember.setEmail(findMember.getEmail());
        //사용자가 최초 로그인이라면 사용자 객체 정보를 보여줌
        if(!findMember.getInitialLogin()) {
            String result = objectMapper.writeValueAsString(helloMember);
            //프론트에서 initialLogin : false 상태를 확인하는 로직 구현
            response.getWriter().write(result);
        }

        System.out.println("성공 로직");
    }

}
