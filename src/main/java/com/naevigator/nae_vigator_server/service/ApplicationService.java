package com.naevigator.nae_vigator_server.service;

import com.naevigator.nae_vigator_server.domain.Application;
import com.naevigator.nae_vigator_server.domain.ApplicationQuestion;
import com.naevigator.nae_vigator_server.domain.Document;
import com.naevigator.nae_vigator_server.repository.ApplicationQuestionRepository;
import com.naevigator.nae_vigator_server.repository.ApplicationRepository;
import com.naevigator.nae_vigator_server.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationQuestionRepository applicationQuestionRepository;
    private final DocumentRepository documentRepository;

    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    public List<Application> getUserApplications(Long userId) {
        return applicationRepository.findByUserId(userId);
    }

    public List<ApplicationQuestion> getQuestions(Long applicationId) {
        return applicationQuestionRepository.findByApplicationId(applicationId);
    }

    public List<Document> getDocuments(Long applicationId) {
        return documentRepository.findByApplicationId(applicationId);
    }
}