package com.itda.controller;

import com.itda.dto.request.ChatMessageRequest;
import com.itda.dto.response.ChatMessageResponse;
import com.itda.entity.User;
import com.itda.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * WebSocket 메시지 수신 및 브로드캐스트
     */
    @MessageMapping("/chat.send")
    public void sendMessage(
            @Payload ChatMessageRequest request,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Principal 대신 headerAccessor에서 userId 추출
        String senderId = (String) headerAccessor.getSessionAttributes().get("userId");

        if (senderId == null && headerAccessor.getUser() != null) {
            senderId = headerAccessor.getUser().getName();
        }

        if (senderId == null) {
            senderId = "unknown";
        }

        // DB 저장
        ChatMessageResponse saved = chatService.saveMessage(senderId, request);

        // 채팅방 구독자들에게 브로드캐스트
        messagingTemplate.convertAndSend(
                "/topic/chat/" + request.getMatchId(),
                saved
        );
    }

    /**
     * 채팅 내역 조회
     * GET /api/chat/{matchId}/messages
     */
    @GetMapping("/api/chat/{matchId}/messages")
    public ResponseEntity<List<ChatMessageResponse>> getMessages(
            @PathVariable String matchId,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(chatService.getMessages(matchId));
    }
}