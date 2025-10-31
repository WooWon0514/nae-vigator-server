package com.naevigator.nae_vigator_server.repository;

import com.naevigator.nae_vigator_server.domain.ExperienceApplication;
import com.naevigator.nae_vigator_server.domain.ExperienceApplication.ExperienceApplicationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceApplicationRepository extends JpaRepository<ExperienceApplication, ExperienceApplicationId> {
}