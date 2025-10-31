package com.naevigator.nae_vigator_server.repository;

import com.naevigator.nae_vigator_server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}