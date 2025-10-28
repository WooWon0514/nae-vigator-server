package com.naevigator.nae_vigator_server.service;

import com.naevigator.nae_vigator_server.domain.Users;
import com.naevigator.nae_vigator_server.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateUserProfile(Long userId, String jobCategory, String jobRole) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. id=" + userId));

        // 사용자의 직군, 직무 정보를 업데이트합니다.
        user.setJobCategory(jobCategory);
        user.setJobRole(jobRole);

    }
}