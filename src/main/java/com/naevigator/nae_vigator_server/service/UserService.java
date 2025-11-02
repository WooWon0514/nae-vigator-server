package com.naevigator.nae_vigator_server.service;

import com.naevigator.nae_vigator_server.domain.User;
import com.naevigator.nae_vigator_server.domain.UserDetail;
import com.naevigator.nae_vigator_server.repository.UserDetailRepository;
import com.naevigator.nae_vigator_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;

    public User getUserProfile(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDetail getUserDetail(Long userId) {
        return userDetailRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User detail not found"));
    }

    public void updateUserProfile(Long userId, String jobCategory, String jobRole) {
    }
}