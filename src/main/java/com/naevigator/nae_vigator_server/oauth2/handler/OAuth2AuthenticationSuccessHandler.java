package com.naevigator.nae_vigator_server.oauth2.handler;

import com.naevigator.nae_vigator_server.jwt.TokenProvider;
import com.naevigator.nae_vigator_server.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.naevigator.nae_vigator_server.oauth2.service.OAuth2UserPrincipal;
import com.naevigator.nae_vigator_server.oauth2.util.CookieUtils;
import jakarta.servlet.http.Cookie;
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

    @Value("${jwt.cookie-domain}")
    private String cookieDomain;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            log.debug("Response already committed. Unable to redirect to {}", targetUrl);
            return;
        }

        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {

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
            log.info("✅ OAuth2 로그인 성공: email={}, name={}",
                    principal.getUserInfo().getEmail(),
                    principal.getUserInfo().getName());

            String accessToken = tokenProvider.createToken(authentication);
            int cookieMaxAgeSeconds = (int) (accessTokenExpiryMinutes * 60);

            Cookie accessTokenCookie = new Cookie(accessTokenCookieName, accessToken);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(cookieMaxAgeSeconds);
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setSecure(false);

            // 🚀 localhost에서는 Domain을 설정하지 않아야 쿠키가 정상 동작함
            if (cookieDomain != null && !cookieDomain.equals("localhost") && !cookieDomain.isEmpty()) {
                accessTokenCookie.setDomain(cookieDomain);
            }


            response.addCookie(accessTokenCookie);
            return targetUrl;
        }

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", "Login failed")
                .build().toUriString();
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof OAuth2UserPrincipal p) {
            return p;
        }
        return null;
    }
}
