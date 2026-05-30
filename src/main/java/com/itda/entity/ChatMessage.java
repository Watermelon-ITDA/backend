package com.itda.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // 어떤 매칭의 채팅인지
    @Column(nullable = false)
    private String matchId;

    // 보낸 사람 ID
    @Column(nullable = false)
    private String senderId;

    // 원본 메시지
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 번역된 메시지 (다국어 API 결과)
    @Column(columnDefinition = "TEXT")
    private String translatedContent;

    @CreationTimestamp
    private LocalDateTime sentAt;
}
