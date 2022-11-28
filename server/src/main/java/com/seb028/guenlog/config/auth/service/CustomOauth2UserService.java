package com.seb028.guenlog.config.auth.service;

import com.seb028.guenlog.config.auth.dto.OauthAttributes;
import com.seb028.guenlog.config.auth.dto.SessionMember;
import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(request);

        String registrationId = request.getClientRegistration().getRegistrationId();
        String usernameAttributeName = request.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OauthAttributes attributes = OauthAttributes.of(registrationId, usernameAttributeName, oAuth2User.getAttributes());
        Optional<Member> repository = memberRepository.findByEmail(attributes.getEmail());
        Member member;
        if(repository.isEmpty()){
            member = saveMember(attributes);
        }
        else {
            member = saveOrUpdate(attributes);
        }
        System.out.println(member.getInitialLogin());
//        Member member = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionMember(member));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes.getAttributes(), attributes.getNameAttributeKey());
    }
    private Member saveOrUpdate(OauthAttributes attributes){
        Member member = memberRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getEmail(), attributes.getName()))
                .orElse(attributes.toEntity());
        return memberRepository.save(member);
    }

    private Member saveMember(OauthAttributes attributes){
        Member member = Member.builder()
                .email(attributes.getEmail())
                .nickname(attributes.getName())
                .password("oauth2member!!")
                .build();
        return memberRepository.save(member);
    }
}
