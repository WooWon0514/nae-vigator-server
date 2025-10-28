package com.naevigator.nae_vigator_server.controller;

import com.naevigator.nae_vigator_server.service.UserService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // PUT /api/users/me/profile 요청을 처리하는 API
    @PutMapping("/me/profile")
    public String updateUserProfile(@RequestBody Map<String, String> profileMap) {
        Long userId = 1L;

        userService.updateUserProfile(
                userId,
                profileMap.get("jobCategory"),
                profileMap.get("jobRole")
        );
        return "프로필이 성공적으로 업데이트되었습니다.";
    }
}