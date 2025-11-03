package com.naevigator.nae_vigator_server.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List; // ◀◀◀ List Import 추가

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Value("${jwt.cookie-name}")
    private String cookieName;

    // ▼▼▼ ★★★★★ 검사를 "무시"할 경로 목록을 만듭니다 ★★★★★ ▼▼▼
    private final List<String> permitAllPaths = List.of(
            "/login",
            "/oauth2/",
            "/api/v1/members/",
            "/dev/",
            "/h2-console/"
    );

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // ▼▼▼ ★★★★★ getServletPath() 대신 getRequestURI() 사용 ★★★★★ ▼▼▼
        String path = request.getRequestURI(); // ◀◀◀ getServletPath() 대신 사용

        // 1. "무시"할 경로인지 확인
        boolean isPermitted = permitAllPaths.stream()
                .anyMatch(permitPath -> path.startsWith(permitPath));

        if (isPermitted) {
            filterChain.doFilter(request, response);
            return;
        }
        // ▲▲▲ --- ▲▲▲ --- ▲▲▲

        // --- 2. (보호된 경로) 쿠키에서 토큰 추출 및 검증 ---
        String token = resolveToken(request);
        if (token != null && tokenProvider.validateToken(token)) {
            Authentication auth = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    // 쿠키에서 토큰을 찾는 resolveToken 메소드 (이전과 동일)
    private String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}