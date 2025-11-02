package com.naevigator.nae_vigator_server.service;

import com.naevigator.nae_vigator_server.domain.UserDetail;
import com.naevigator.nae_vigator_server.domain.User;
import com.naevigator.nae_vigator_server.repository.UserDetailRepository;
import com.naevigator.nae_vigator_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor // final 필드를 위한 생성자를 자동으로 만듭니다.
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository; // UserDetail 관리자 추가

    public void updateUserProfile(Long userId, String jobCategory, String jobRole) {

        // 1. users 테이블에서 사용자를 찾습니다.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. id=" + userId));

        // 2. user_details 테이블에서 이 사용자의 상세 정보를 찾거나, 없으면 새로 만듭니다.
        UserDetail userDetail = userDetailRepository.findById(userId)
                .orElse(new UserDetail());

        // 3. UserDetail 객체에 정보를 설정합니다.
        userDetail.setUser(user); // 1:1 관계 설정
        userDetail.setUserId(user.getId()); // ID 설정
        userDetail.setJobCategory(jobCategory);
        userDetail.setJobRole(jobRole);

        // 4. user_details 테이블에 저장합니다.
        userDetailRepository.save(userDetail);
    }
}