package com.naevigator.nae_vigator_server.service;

import com.naevigator.nae_vigator_server.domain.User;
import com.naevigator.nae_vigator_server.domain.UserDetail;
import com.naevigator.nae_vigator_server.repository.UserDetailRepository;
import com.naevigator.nae_vigator_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;

    public void updateUserProfile(Long userId, String jobCategory, String jobRole) {

        // 1️⃣ users 테이블에서 사용자 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. id=" + userId));

        // 2️⃣ user_details 테이블에서 해당 사용자 정보 찾기 (없으면 새로 생성)
        UserDetail userDetail = userDetailRepository.findByUser(user)
                .orElseGet(() -> {
                    UserDetail newDetail = new UserDetail();
                    newDetail.setUser(user);
                    newDetail.setCreatedAt(LocalDateTime.now());
                    return newDetail;
                });

        // 3️⃣ 값 설정
        userDetail.setJobCategory(jobCategory);
        userDetail.setJobRole(jobRole);
        userDetail.setUpdatedAt(LocalDateTime.now());

        // 4️⃣ DB 저장
        userDetailRepository.save(userDetail);
    }
}