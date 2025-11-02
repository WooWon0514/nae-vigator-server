package com.naevigator.nae_vigator_server.repository;

import com.naevigator.nae_vigator_server.domain.AiGenerationLog;
import com.naevigator.nae_vigator_server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AiGenerationLogRepository extends JpaRepository<AiGenerationLog, Long> {
    List<AiGenerationLog> findByUser(User user);

    List<AiGenerationLog> findByUserId(Long userId);
}