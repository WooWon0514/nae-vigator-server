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
@Table(name = "user_details")
public class UserDetail {

    @Id
    @Column(name = "user_id")
    private Long id;   // PK=FK

    @OneToOne
    @MapsId        // users.id 를 PK로 같이 씀
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 50)
    private String jobCategory;

    @Column(length = 50)
    private String jobRole;

    @Column(length = 255)
    private String profileImageUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public void setUserId(Long id) {
    }
}