package com.naevigator.nae_vigator_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "document")
@Getter
@Setter
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @Column(nullable = false)
    private String type; // "resume" or "cover_letter"

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private int version;

    @CreationTimestamp
    private LocalDateTime createdAt;
}