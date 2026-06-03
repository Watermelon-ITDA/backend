package com.itda.domain.help.service;

import com.itda.domain.help.dto.request.TravlerHelpRequest;
import com.itda.domain.help.entity.TravlerHelp;
import com.itda.domain.help.repository.TravlerHelpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravlerHelpService {

    private final TravlerHelpRepository travlerHelpRepository;

    public void createHelp(String userId, TravlerHelpRequest req) {
        TravlerHelp travlerHelp = new TravlerHelp(
                userId,
                req.getHelpType(),
                req.getAddress(),
                req.getLatitude(),
                req.getLongitude()
        );
        travlerHelpRepository.save(travlerHelp);
    }
}
