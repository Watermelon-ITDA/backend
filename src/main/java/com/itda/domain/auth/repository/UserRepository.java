package com.itda.domain.auth.repository;

import com.itda.domain.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    // 이메일로 유저 조회 (OAuth2 로그인 시 기존 유저 확인용)
    Optional<User> findByEmail(String email);

    // 이메일 존재 여부 확인
    boolean existsByEmail(String email);
}
