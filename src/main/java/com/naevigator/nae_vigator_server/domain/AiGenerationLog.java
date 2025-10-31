package com.naevigator.nae_vigator_server.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ai_generation_log")
public class AiGenerationLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // documents / applications / experiences ... 이런 걸로 보임
    @Column(name = "target_type", length = 20, nullable = false)
    private String targetType;

    @Column(name = "target_id")
    private Long targetId;

    @Column(length = 20)
    private String action;

    @Column(length = 50)
    private String provider;

    @Column(length = 100)
    private String model;

    @Lob
    private String inputs;

    @Lob
    private String outputSummary;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
