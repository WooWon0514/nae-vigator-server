package com.naevigator.nae_vigator_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 사용자 고유 번호

    @Column(nullable = false, unique = true)
    private String email; // 이메일 (로그인 ID)

    @Column
    private String password; // 비밀번호 (암호화 저장)

    @Column(nullable = false)
    private String name; // 사용자 이름

    @Column(nullable = false)
    private String provider; // 가입 경로 (예: "local", "naver", "kakao")

    @Column
    private String providerId; // 소셜 로그인 고유 ID

    @Column
    private String jobCategory; // 직군 (NULL 허용)

    @Column
    private String jobRole; // 상세 직무 (NULL 허용)

    @CreationTimestamp
    private LocalDateTime createdAt; // 생성 일시

    @UpdateTimestamp
    private LocalDateTime updatedAt; // 수정 일시
}