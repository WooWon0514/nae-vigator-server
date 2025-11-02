package com.naevigator.nae_vigator_server.service;

import com.naevigator.nae_vigator_server.domain.User;
import com.naevigator.nae_vigator_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void updateUserProfile(Long userId, String jobCategory, String jobRole) {
        // DB에서 userId에 해당하는 사용자를 찾습니다.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. id=" + userId));

        // 사용자의 직군, 직무 정보를 업데이트합니다.
        user.setJobCategory(jobCategory);
        user.setJobRole(jobRole);

        // @Transactional 덕분에 이 메서드가 끝나면 변경된 내용이 자동으로 DB에 저장됩니다.
    }
}