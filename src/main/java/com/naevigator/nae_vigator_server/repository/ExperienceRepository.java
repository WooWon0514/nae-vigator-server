package com.naevigator.nae_vigator_server.repository;

import com.naevigator.nae_vigator_server.domain.Experience;
import com.naevigator.nae_vigator_server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByUser(User user);

    List<Experience> findByUserId(Long userId);
}