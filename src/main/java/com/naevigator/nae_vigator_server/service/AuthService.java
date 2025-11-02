package com.naevigator.nae_vigator_server.service;

import com.naevigator.nae_vigator_server.domain.User;
import com.naevigator.nae_vigator_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signUp(String email, String password, String name) {
        // 이미 가입된 이메일인지 확인하는 로직 (나중에 추가)

        User newUser = new User();
        newUser.setEmail(email);
        // 비밀번호를 암호화해서 저장합니다.
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setName(name);
        newUser.setProvider("local"); // 일반 회원가입

        // UserRepository를 사용해서 DB에 저장합니다.
        return userRepository.save(newUser);
    }
}