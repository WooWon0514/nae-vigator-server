package com.naevigator.nae_vigator_server.repository;

import com.naevigator.nae_vigator_server.domain.Document;
import com.naevigator.nae_vigator_server.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByApplication(Application application);

    List<Document> findByApplicationId(Long applicationId);
}