package com.naevigator.nae_vigator_server.controller;

import com.naevigator.nae_vigator_server.oauth2.service.OAuth2UserPrincipal;
import com.naevigator.nae_vigator_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/me/profile")
    public ResponseEntity<String> updateUserProfile(
            @RequestBody Map<String, String> profileMap,
            Authentication authentication) {

        // ✅ 현재 로그인한 사용자 정보 꺼내기
        OAuth2UserPrincipal principal = (OAuth2UserPrincipal) authentication.getPrincipal();
        Long userId = Long.valueOf(principal.getUserInfo().getId());

        // ✅ 프로필 업데이트
        userService.updateUserProfile(
                userId,
                profileMap.get("jobCategory"),
                profileMap.get("jobRole")
        );
        return null;
    }
}