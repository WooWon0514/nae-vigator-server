package com.naevigator.nae_vigator_server.controller;

import com.naevigator.nae_vigator_server.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth") // /api/auth 로 시작하는 모든 요청은 이 Controller가 담당합니다.
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // POST /api/auth/signup 요청을 처리하는 메소드입니다.
    @PostMapping("/signup")
    public String signUp(@RequestBody Map<String, String> userMap) {
        // AuthService에게 회원가입 처리를 시킵니다.
        authService.signUp(
                userMap.get("email"),
                userMap.get("password"),
                userMap.get("name")
        );
        // 프론트엔드에게 성공 메시지를 보냅니다.
        return "회원가입이 완료되었습니다.";
    }
}