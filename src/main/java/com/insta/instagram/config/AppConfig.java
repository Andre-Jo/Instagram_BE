package com.insta.instagram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class AppConfig {

    @Bean
    public SecurityFilterChain securityConfigration(HttpSecurity http) throws Exception {

        http
                // CSRF 대책 (현재 CSRF에 대한 대책 비활성화)
                .csrf((csrf) -> csrf.disable())
                // 세션 기반 인증 (현재 Session 기반 인증을 사용하지 않아서 상태 없앰)
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // '/signup' 모듈에 대한 모든 허용 (인증을 하지 않고 사용 가능하도록)
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.POST, "/signup").permitAll()
                //  나머지는 인증 해야 접근 가능
                        .anyRequest().authenticated())
                .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenValidationFilter(), BasicAuthenticationFilter.class)
                // Basic 인증 사용 ( 현재 Bearer Token 인증 방법을 사용하기 때문에 비활성화)
                .httpBasic((httpBasic) -> httpBasic.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
