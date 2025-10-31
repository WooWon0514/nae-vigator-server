package com.naevigator.nae_vigator_server.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "experience_application")
public class ExperienceApplication {

    @EmbeddedId
    private ExperienceApplicationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("experienceId")
    @JoinColumn(name = "experience_id")
    private Experience experience;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("applicationId")
    @JoinColumn(name = "application_id")
    private Application application;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class ExperienceApplicationId implements Serializable {
        @Column(name = "experience_id")
        private Long experienceId;

        @Column(name = "application_id")
        private Long applicationId;
    }
}