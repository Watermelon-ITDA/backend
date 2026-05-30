package com.itda.config;

import com.itda.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompAuthInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                if (jwtUtil.isValid(token)) {
                    String userId = jwtUtil.getUserId(token);
                    accessor.setUser(() -> userId);

                    // 세션 속성에도 저장
                    Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
                    if (sessionAttributes != null) {
                        sessionAttributes.put("userId", userId);
                    }

                    log.info("WebSocket 연결 성공 - userId: {}", userId);
                } else {
                    log.warn("WebSocket 연결 실패 - 유효하지 않은 토큰");
                    throw new IllegalArgumentException("유효하지 않은 토큰");
                }
            }
        }
        return message;
    }
}