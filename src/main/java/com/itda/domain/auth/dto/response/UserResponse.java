package com.itda.domain.auth.dto.response;

import com.itda.domain.auth.entity.User;
import lombok.Builder;

@Builder
public record UserResponse(
        String id,
        String nickname,
        String email,
        String role,
        String language,
        String profileImageUrl,
        Double latitude,
        Double longitude,
        Integer level,
        Integer points
) {
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .role(user.getRole().name())
                .language(user.getLanguage())
                .profileImageUrl(user.getProfileImageUrl())
                .latitude(user.getLatitude())
                .longitude(user.getLongitude())
                .level(user.getLevel())
                .points(user.getPoints())
                .build();
    }
}
