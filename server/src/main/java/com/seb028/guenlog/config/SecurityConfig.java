package com.seb028.guenlog.config;

import com.seb028.guenlog.config.auth.JwtTokenizer;
import com.seb028.guenlog.config.auth.handler.*;
import com.seb028.guenlog.config.auth.jwt.filter.JwtAuthenticationFilter;
import com.seb028.guenlog.config.auth.jwt.filter.JwtVerificationFilter;
import com.seb028.guenlog.config.auth.service.CustomOauth2UserService;
import com.seb028.guenlog.config.auth.utils.CustomAuthorityUtils;
import com.seb028.guenlog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberRepository memberRepository;
    private final CustomOauth2UserService customOAuth2UserService;
    private final Oauth2MemberSuccessHandler oauth2MemberSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션 생성하지 않음
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/exercises").authenticated()//--------인증된 사용자만 접근 가능
                        .antMatchers("/exercises/**").authenticated()
                        .antMatchers("/users/info").authenticated()
                        .antMatchers("/users/mypages/**").authenticated()
                        .antMatchers("/mappage").authenticated()//------
                        .anyRequest().permitAll())//그 외 모든 사용자 접근 가능
                .oauth2Login()//OAuth2 로그인 시작
                .userInfoEndpoint()//로그인 성공시 사용자 정보를 가져옴
                .userService(customOAuth2UserService); //로그인 성공 후 oauth2userservice 호출
        http
                .oauth2Login()
                .successHandler(oauth2MemberSuccessHandler);//oauth2 인증 성공 후처리 handler 호출
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class); //authentication 객체를 얻음
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenizer, authenticationManager);
            jwtAuthenticationFilter.setFilterProcessesUrl("/users/login");//로그인 url 설정
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler(memberRepository));//인증 성공시 로직
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());//인증 실패시 에러 출력

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class)
//                    .addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class)
            ;
        }
    }

    //cors 설정
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*"); //모든 origin에 대해 접근 가능
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "OPTIONS", "DELETE")); //해당 메서드에 대해 사용 가능
        configuration.setAllowedHeaders(Arrays.asList("*"));//전체 해더 접근 가능
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization"); //Authorization 헤더 접근 가능
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
