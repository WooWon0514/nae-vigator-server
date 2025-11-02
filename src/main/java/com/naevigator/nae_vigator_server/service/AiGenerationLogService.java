package com.naevigator.nae_vigator_server.service;

import com.naevigator.nae_vigator_server.domain.AiGenerationLog;
import com.naevigator.nae_vigator_server.repository.AiGenerationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiGenerationLogService {

    private final AiGenerationLogRepository aiGenerationLogRepository;

    public AiGenerationLog saveLog(AiGenerationLog log) {
        return aiGenerationLogRepository.save(log);
    }

    public List<AiGenerationLog> getLogsByUser(Long userId) {
        return aiGenerationLogRepository.findByUserId(userId);
    }
}