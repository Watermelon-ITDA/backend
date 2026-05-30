package com.itda.service;

import com.itda.dto.request.ChatMessageRequest;
import com.itda.dto.response.ChatMessageResponse;
import com.itda.entity.ChatMessage;
import com.itda.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    // 메시지 저장 + 브로드캐스트용 반환
    @Transactional
    public ChatMessageResponse saveMessage(String senderId, ChatMessageRequest request) {
        ChatMessage message = ChatMessage.builder()
                .matchId(request.getMatchId())
                .senderId(senderId)
                .content(request.getContent())
                // TODO: 한국관광공사 다국어 API 연동 시 번역 결과 저장
                .translatedContent(null)
                .build();

        ChatMessage saved = chatMessageRepository.save(message);
        log.info("채팅 메시지 저장 - matchId: {}, senderId: {}", request.getMatchId(), senderId);
        return ChatMessageResponse.from(saved);
    }

    // 채팅 내역 조회
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getMessages(String matchId) {
        return chatMessageRepository.findByMatchIdOrderBySentAtAsc(matchId)
                .stream()
                .map(ChatMessageResponse::from)
                .toList();
    }
}
