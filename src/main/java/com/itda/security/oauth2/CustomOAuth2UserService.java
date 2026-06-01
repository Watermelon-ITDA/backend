package com.itda.security.oauth2;

import com.itda.domain.auth.entity.User;
import com.itda.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 구글에서 받아오는 정보
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String picture = (String) attributes.get("picture");

        log.info("구글 로그인 시도 - email: {}", email);

        // DB에 유저가 있으면 프로필 업데이트, 없으면 신규 가입
        boolean[] isNewUser = {false};

        User user = userRepository.findByEmail(email)
                .map(existingUser -> {
                    existingUser.updateProfileImage(picture);
                    log.info("기존 유저 로그인 - email: {}", email);
                    return existingUser;
                })
                .orElseGet(() -> {
                    isNewUser[0] = true;
                    User newUser = User.builder()
                            .email(email)
                            .nickname(name)
                            .role(User.Role.TRAVELER) // 기본값 여행자, 이후 역할 선택에서 변경
                            .profileImageUrl(picture)
                            .language("ko")
                            .build();
                    log.info("신규 유저 가입 - email: {}", email);
                    return userRepository.save(newUser);
                });

        // userId, isNew를 attributes에 추가해서 OAuth2SuccessHandler에서 사용
        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                Map.of(
                        "id", user.getId(),
                        "email", email,
                        "name", name,
                        "picture", picture != null ? picture : "",
                        "isNew", isNewUser[0]
                ),
                "email"
        );
    }
}
