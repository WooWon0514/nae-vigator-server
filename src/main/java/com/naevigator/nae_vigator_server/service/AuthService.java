package com.naevigator.nae_vigator_server.service;

import com.naevigator.nae_vigator_server.domain.User;
import com.naevigator.nae_vigator_server.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(String email, String password, String name) {
        // 여기에 나중에 비밀번호 암호화 로직 등을 추가할 예정입니다.
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password); // 지금은 비밀번호를 그대로 저장합니다.
        newUser.setName(name);
        newUser.setProvider("local"); // 일반 회원가입이므로 "local"로 저장

        // UserRepository를 사용해서 DB에 저장합니다.
        return userRepository.save(newUser);
    }
}