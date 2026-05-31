package com.itda.domain.chat.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageRequest {
    private String matchId;    // 어떤 매칭방인지
    private String content;    // 메시지 내용
}
