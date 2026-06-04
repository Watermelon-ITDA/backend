package com.itda.domain.help.controller;

import com.itda.domain.auth.annotation.AuthUser;
import com.itda.domain.auth.entity.User;
import com.itda.domain.help.dto.request.TravlerHelpRequest;
import com.itda.domain.help.service.HelpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/help")
public class HelpController {

    private final HelpService helpService;

    @PostMapping("/regist")
    public ResponseEntity<?> saveTravlerHelpInfo(@AuthUser User user, @RequestBody TravlerHelpRequest req) {

        log.info("userId: {}", user.getId());
        helpService.createHelp(user.getId(), req);
        return ResponseEntity.ok().build();
    }
}
