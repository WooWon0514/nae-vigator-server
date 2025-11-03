package com.naevigator.nae_vigator_server.oauth2.handler;

// ... (기존 import들)
import com.naevigator.nae_vigator_server.jwt.TokenProvider;
import com.naevigator.nae_vigator_server.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.naevigator.nae_vigator_server.oauth2.service.OAuth2UserPrincipal;
import com.naevigator.nae_vigator_server.oauth2.util.CookieUtils;
import jakarta.servlet.http.Cookie; // ◀◀◀ CookieUtils 대신 직접 import
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

import static com.naevigator.nae_vigator_server.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository.MODE_PARAM_COOKIE_NAME;
import static com.naevigator.nae_vigator_server.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

// ... (static import들)

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Value("${jwt.cookie-name}")
    private String accessTokenCookieName;

    @Value("${jwt.access-exp-minutes}")
    private long accessTokenExpiryMinutes;

    // ▼▼▼ yml에서 cookie-domain 값을 가져오도록 @Value 추가 ▼▼▼
    @Value("${jwt.cookie-domain}")
    private String cookieDomain;
    // ▲▲▲ --- ▲▲▲

    {
        setDefaultTargetUrl("/api/v1/home");
    }

    // onAuthenticationSuccess 메소드는 이전과 동일합니다 (수정 필요 없음)
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // ... (이 메소드는 수정 안 하셔도 됩니다)
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    // determineTargetUrl 메소드 내부를 수정합니다.
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {

        // ... (메소드 상단부는 동일합니다)
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);

        if (principal == null) {
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("error", "Login failed")
                    .build().toUriString();
        }

        String mode = CookieUtils.getCookie(request, MODE_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse("login");

        if ("login".equalsIgnoreCase(mode)) {
            // ... (log.info 부분은 그대로)
            log.info("email={}, name={}, nickname={}, accessToken={}", principal.getUserInfo().getEmail(),
                    principal.getUserInfo().getName(),
                    principal.getUserInfo().getNickname(),
                    principal.getUserInfo().getAccessToken()
            );

            // 3) JWT 생성
            String accessToken = tokenProvider.createToken(authentication);
            // String refreshToken = "test_refresh_token"; // TODO: 리프레시 토큰도 발급

            // ▼▼▼ ★★★★★ 여기가 핵심 수정 사항입니다 ★★★★★ ▼▼▼

            // 4) CookieUtils 대신, Cookie 객체를 직접 생성합니다.
            int cookieMaxAgeSeconds = (int) (accessTokenExpiryMinutes * 60);

            Cookie accessTokenCookie = new Cookie(accessTokenCookieName, accessToken);

            // 5) 쿠키 속성을 명시적으로 설정합니다.
            accessTokenCookie.setPath("/"); // ◀◀◀ 가장 중요! 사이트 전역으로 설정
            accessTokenCookie.setDomain(cookieDomain); // ◀◀◀ yml의 "localhost" 설정
            accessTokenCookie.setMaxAge(cookieMaxAgeSeconds);
            accessTokenCookie.setHttpOnly(true); // JavaScript에서 접근 불가
            // accessTokenCookie.setSecure(true); // ◀ HTTPS 환경에서만 true로 설정 (현재 localhost는 false가 맞습니다)

            // 6) 응답(response)에 쿠키를 추가합니다.
            response.addCookie(accessTokenCookie);

            // TODO: 리프레시 토큰도 동일하게 쿠키로 구워야 합니다.

            // 7) URL 파라미터 없이 깔끔한 targetUrl을 반환합니다.
            return targetUrl;
            // ▲▲▲ ★★★★★ --- ▲▲▲ ★★★★★
        }

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", "Login failed")
                .build().toUriString();
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        // ... (이 메소드는 수정 안 하셔도 됩니다)
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2UserPrincipal) {
            return (OAuth2UserPrincipal) principal;
        }
        return null;
    }
}