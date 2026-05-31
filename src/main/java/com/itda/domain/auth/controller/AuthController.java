package com.itda.domain.auth.controller;

import com.itda.domain.auth.dto.response.UserResponse;
import com.itda.domain.auth.entity.User;
import com.itda.domain.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * 내 정보 조회
     * GET /api/auth/me
     * Header: Authorization: Bearer {token}
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(UserResponse.from(user));
    }

    /**
     * 역할 변경 (여행자 ↔ 동행자)
     * PATCH /api/auth/role
     * Body: { "role": "LOCAL" }
     */
    @PatchMapping("/role")
    public ResponseEntity<UserResponse> updateRole(
            @AuthenticationPrincipal User user,
            @RequestParam String role
    ) {
        User updated = userService.updateRole(user.getId(), role);
        return ResponseEntity.ok(UserResponse.from(updated));
    }
}
