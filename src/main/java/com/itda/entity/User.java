package com.itda.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    // 'traveler' 또는 'local'
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // 사용 언어 (번역 API 연동용)
    @Column(nullable = false)
    @Builder.Default
    private String language = "ko";

    // 프로필 사진 URL (구글 프로필 사진으로 초기 세팅)
    private String profileImageUrl;

    // 로컬히어로 활동 지역 위도 (동행자만 사용)
    private Double latitude;

    // 로컬히어로 활동 지역 경도 (동행자만 사용)
    private Double longitude;

    // 로컬히어로 레벨 (보상 모델)
    @Column(nullable = false)
    @Builder.Default
    private Integer level = 1;

    // 적립 포인트 (제휴 매장 할인 교환용)
    @Column(nullable = false)
    @Builder.Default
    private Integer points = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // == 비즈니스 메서드 == //

    public void updateRole(Role role) {
        this.role = role;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImage(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void levelUp() {
        this.level += 1;
    }

    public enum Role {
        TRAVELER, LOCAL
    }
}
