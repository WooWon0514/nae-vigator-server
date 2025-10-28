package com.naevigator.nae_vigator_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Alt + Enter로 import
import org.springframework.security.crypto.password.PasswordEncoder; // Alt + Enter로 import
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // ▼▼▼ 서버 구동을 위한 핵심 부품: PasswordEncoder 정의 ▼▼▼
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호를 안전하게 암호화하는 BCrypt 알고리즘을 사용하도록 정의합니다.
        return new BCryptPasswordEncoder();
    }
    // ▲▲▲ 이 코드가 추가되어야 오류가 사라집니다. ▲▲▲

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 기능 끄기
                .csrf((csrf) -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // "/api/auth/signup" 주소는 누구나 접근할 수 있도록 허용
                        .requestMatchers("/api/auth/signup").permitAll()
                        // 그 외의 모든 요청은 인증된 사용자만 접근 가능
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}