package com.naevigator.nae_vigator_server.repository;

import com.naevigator.nae_vigator_server.domain.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
}