package com.naevigator.nae_vigator_server.repository;

import com.naevigator.nae_vigator_server.domain.Users;
import com.naevigator.nae_vigator_server.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// User를 모두 Users로 변경해주세요.
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}