package com.itda.domain.chat.repository;

import com.itda.domain.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {

    // 매칭 ID로 채팅 내역 조회 (시간순)
    List<ChatMessage> findByMatchIdOrderBySentAtAsc(String matchId);
}
