package com.naevigator.nae_vigator_server.repository;

import com.naevigator.nae_vigator_server.domain.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}