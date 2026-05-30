package com.itda.security.oauth2;

import com.itda.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Value("${app.oauth2.redirect-uri}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String userId = (String) oAuth2User.getAttribute("id");
        String email = (String) oAuth2User.getAttribute("email");

        // JWT 발급
        String token = jwtUtil.generateToken(userId, email);

        log.info("OAuth2 로그인 성공 - userId: {}, token 발급 완료", userId);

        // 프론트엔드로 리다이렉트 (토큰을 쿼리 파라미터로 전달)
        // 프론트에서 /oauth2/callback?token=xxx 로 받아서 localStorage에 저장
        response.sendRedirect(redirectUri + "?token=" + token);
    }
}
