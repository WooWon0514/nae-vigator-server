package com.naevigator.nae_vigator_server.repository;

import com.naevigator.nae_vigator_server.domain.User;
import com.naevigator.nae_vigator_server.domain.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

    // ✅ 특정 User 객체로 user_details 조회
    Optional<UserDetail> findByUser(User user);
}