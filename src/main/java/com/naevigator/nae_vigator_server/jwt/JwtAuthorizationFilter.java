package com.naevigator.nae_vigator_server.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie; // ◀◀◀ Import
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value; // ◀◀◀ Import
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
// import org.springframework.util.StringUtils; // 헤더 방식이 아니므로 필요 없음
import java.io.IOException;
import java.util.Arrays; // ◀◀◀ Import

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    // private static final String AUTH_HEADER = "Authorization"; // 필요 없음
    // private static final String BEARER = "Bearer "; // 필요 없음
    private final TokenProvider tokenProvider;

    // ▼▼▼ application.yml의 쿠키 이름을 가져옴
    @Value("${jwt.cookie-name}")
    private String cookieName;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // --- 1. (가장 중요) 공용 경로는 토큰 검사 없이 즉시 통과 ---
        // (이게 없으면 /oauth2/authorization/naver가 막힙니다)
        String path = request.getServletPath();
        if (
                path.equals("/login") ||
                        path.startsWith("/oauth2/") ||
                        path.startsWith("/api/v1/members/") || // AuthController
                        path.startsWith("/dev/") ||
                        path.startsWith("/h2-console/")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        // --- 2. 쿠키에서 토큰 추출 및 검증 ---
        String token = resolveToken(request); // 1) 토큰 추출 (from Cookie)
        if (token != null && tokenProvider.validateToken(token)) { // 2) 검증
            Authentication auth = tokenProvider.getAuthentication(token); // 3) 인증 객체 생성
            SecurityContextHolder.getContext().setAuthentication(auth);  // 4) 컨텍스트 주입
        }
        filterChain.doFilter(request, response);
    }

    // ▼▼▼ ★★★★★ 헤더 대신 쿠키에서 토큰을 찾는 메소드 ★★★★★ ▼▼▼
    private String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        // 쿠키 배열을 스트림으로 변환하여
        return Arrays.stream(cookies)
                // 쿠키 이름이 yml에 설정된 이름("access_token")과 같은지 확인
                .filter(cookie -> cookie.getName().equals(cookieName))
                // 쿠키의 값을 가져옴
                .map(Cookie::getValue)
                // 첫 번째 값을 찾아서 반환
                .findFirst()
                .orElse(null);
    }
}