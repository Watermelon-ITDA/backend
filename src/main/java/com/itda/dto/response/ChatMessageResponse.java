package com.itda.dto.response;

import com.itda.entity.ChatMessage;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatMessageResponse(
        String id,
        String matchId,
        String senderId,
        String content,
        String translatedContent,
        LocalDateTime sentAt
) {
    public static ChatMessageResponse from(ChatMessage msg) {
        return ChatMessageResponse.builder()
                .id(msg.getId())
                .matchId(msg.getMatchId())
                .senderId(msg.getSenderId())
                .content(msg.getContent())
                .translatedContent(msg.getTranslatedContent())
                .sentAt(msg.getSentAt())
                .build();
    }
}
