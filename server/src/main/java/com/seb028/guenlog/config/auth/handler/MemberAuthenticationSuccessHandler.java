package com.seb028.guenlog.config.auth.handler;

import com.seb028.guenlog.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                 Authentication authentication) throws IOException, ServletException {
        Member member = (Member) authentication.getPrincipal();

        response.setHeader("InitialLogin", member.getInitialLogin().toString());
        log.info("InitialLogin -> {}", member.getInitialLogin());
        System.out.println("성공 로직");
    }

}
