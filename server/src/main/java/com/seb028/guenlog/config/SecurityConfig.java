package com.seb028.guenlog.config;

import com.seb028.guenlog.config.auth.JwtTokenizer;
import com.seb028.guenlog.config.auth.handler.MemberAuthenticationSuccessHandler;
import com.seb028.guenlog.config.auth.jwt.filter.JwtAuthenticationFilter;
import com.seb028.guenlog.config.auth.jwt.filter.JwtVerificationFilter;
import com.seb028.guenlog.config.auth.utils.CustomAuthorityUtils;
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
//@RequiredArgsConstructor
//@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenizer tokenizer;
    private final CustomAuthorityUtils authorityUtils;

    public SecurityConfig(JwtTokenizer tokenizer, CustomAuthorityUtils authorityUtils) {
        this.tokenizer = tokenizer;
        this.authorityUtils = authorityUtils;
    }

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
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/exercises").authenticated()//--------인증된 사용자만 접근 가능
                        .antMatchers("/exercises/**").authenticated()
                        .antMatchers("/users/info").authenticated()
                        .antMatchers("/users/mypages/**").authenticated()//------
                        .anyRequest().permitAll());//그 외 모든 사용자 접근 가능

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
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(tokenizer, authenticationManager);
            jwtAuthenticationFilter.setFilterProcessesUrl("/users/login");//로그인 url 설정
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(tokenizer, authorityUtils);

            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
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
