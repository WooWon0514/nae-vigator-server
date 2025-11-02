package com.naevigator.nae_vigator_server.controller;

import com.naevigator.nae_vigator_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users") // "/api/v1/users"로 시작하는 주소를 담당합니다.
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
}