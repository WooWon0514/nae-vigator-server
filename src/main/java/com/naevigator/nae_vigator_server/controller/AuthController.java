package com.naevigator.nae_vigator_server.controller;

import com.naevigator.nae_vigator_server.service.AuthService;
import com.naevigator.nae_vigator_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    // 5. 기존 회원가입 API (POST /api/v1/members/signup)
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody Map<String, String> userMap) {
        authService.signUp(
                userMap.get("email"),
                userMap.get("password"),
                userMap.get("name")
        );
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PutMapping("/me/profile")
    public ResponseEntity<String> updateUserProfile(@RequestBody Map<String, String> profileMap) {
        Long userId = 1L;

        userService.updateUserProfile(
                userId,
                profileMap.get("jobCategory"),
                profileMap.get("jobRole")
        );
        return ResponseEntity.ok("프로필이 성공적으로 업데이트되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> userMap) {
        // String token = authService.login(userMap.get("email"), userMap.get("password"));
        // return ResponseEntity.ok(token);

        return ResponseEntity.ok("로그인 성공 (JWT 토큰 발급 예정)");
    }
}