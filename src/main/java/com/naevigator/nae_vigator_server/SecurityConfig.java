// package 주소는 팀원분의 파일 경로에 맞춰주세요.
package com.naevigator.nae_vigator_server;

// --- 필요한 import 목록 ---
import com.naevigator.nae_vigator_server.jwt.JwtAuthorizationFilter;
import com.naevigator.nae_vigator_server.oauth2.handler.OAuth2AuthenticationFailureHandler;
import com.naevigator.nae_vigator_server.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import com.naevigator.nae_vigator_server.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.naevigator.nae_vigator_server.oauth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // import 추가
import org.springframework.security.crypto.password.PasswordEncoder; // import 추가
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    // ▼▼▼ 사용자님의 코드에서 가져온 필수 부품! ▼▼▼
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // ▲▲▲ --- ▲▲▲

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // ▼▼▼ 두 사람의 규칙을 합친 최종 허용 목록! ▼▼▼
                        .requestMatchers("/", "/login", "/api/auth/**", "/oauth2/**", "/dev/**", "/h2-console/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth -> oauth
                        .authorizationEndpoint(ep ->
                                ep.authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository))
                        .userInfoEndpoint(ui -> ui.userService(customOAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler)
                );

        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}