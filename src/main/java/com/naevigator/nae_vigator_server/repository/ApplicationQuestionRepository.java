package com.naevigator.nae_vigator_server.repository;

import com.naevigator.nae_vigator_server.domain.ApplicationQuestion;
import com.naevigator.nae_vigator_server.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationQuestionRepository extends JpaRepository<ApplicationQuestion, Long> {
    List<ApplicationQuestion> findByApplicationOrderByOrderNoAsc(Application application);
}