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
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //?????? ???????????? ??????
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
                        .antMatchers("/exercises").authenticated()//--------????????? ???????????? ?????? ??????
                        .antMatchers("/exercises/**").authenticated()
                        .antMatchers("/users/info").authenticated()
                        .antMatchers("/users/mypages/**").authenticated()
                        .antMatchers("/mappage").authenticated()//------
                        .anyRequest().permitAll())//??? ??? ?????? ????????? ?????? ??????
                .oauth2Login()//OAuth2 ????????? ??????
                .userInfoEndpoint()//????????? ????????? ????????? ????????? ?????????
                .userService(customOAuth2UserService); //????????? ?????? ??? oauth2userservice ??????
        http
                .oauth2Login()
                .successHandler(oauth2MemberSuccessHandler);//oauth2 ?????? ?????? ????????? handler ??????
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class); //authentication ????????? ??????
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenizer, authenticationManager);
            jwtAuthenticationFilter.setFilterProcessesUrl("/users/login");//????????? url ??????
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler(memberRepository));//?????? ????????? ??????
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());//?????? ????????? ?????? ??????

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class)
//                    .addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class)
            ;
        }
    }

    //cors ??????
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("https://realguenlog.vercel.app/"); //?????? origin??? ?????? ?????? ??????
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "OPTIONS", "DELETE")); //?????? ???????????? ?????? ?????? ??????
        configuration.setAllowedHeaders(Arrays.asList("*"));//?????? ?????? ?????? ??????
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization"); //Authorization ?????? ?????? ??????
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
