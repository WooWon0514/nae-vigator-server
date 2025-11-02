package com.naevigator.nae_vigator_server.service;

import com.naevigator.nae_vigator_server.domain.Experience;
import com.naevigator.nae_vigator_server.repository.ExperienceApplicationRepository;
import com.naevigator.nae_vigator_server.repository.ExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceApplicationRepository experienceApplicationRepository;

    public Experience createExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    public List<Experience> getUserExperiences(Long userId) {
        return experienceRepository.findByUserId(userId);
    }
}
