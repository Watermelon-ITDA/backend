package com.itda.domain.help.service;

import com.itda.domain.help.dto.request.TravlerHelpRequest;
import com.itda.domain.help.dto.response.NearbyHelpResponse;
import com.itda.domain.help.entity.Help;
import com.itda.domain.help.entity.Role;
import com.itda.domain.help.repository.HelpRepository;
import com.itda.domain.help.util.DistanceCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HelpService {

    private final HelpRepository helpRepository;
    private final DistanceCalculator distanceCalculator;

    public void createHelp(String userId, TravlerHelpRequest req) {
        Help help = new Help(
                userId,
                req.getHelpType(),
                req.getAddress(),
                req.getLatitude(),
                req.getLongitude(),
                req.getRole(),
                req.getContent(),
                req.getActive()
        );
        helpRepository.save(help);
    }

    public List<NearbyHelpResponse> findNearby(Role role, double lat, double lng) {
        return helpRepository.findByRole(role)
                .stream()
                .map(help -> {

                    double distance =
                            distanceCalculator.calculate(
                                    lat,
                                    lng,
                                    help.getLatitude(),
                                    help.getLongitude()
                            );

                    return new NearbyHelpResponse(
                            help.getUserId(),
                            help.getType(),
                            help.getAddress(),
                            help.getLatitude(),
                            help.getLongitude(),
                            distance,
                            help.getRole(),
                            help.getContent()
                    );
                })
                .filter(item -> item.getDistance() <= 3) // 3km 이내
                .sorted(
                        Comparator.comparing(
                                NearbyHelpResponse::getDistance
                        )
                )
                .limit(20)
                .toList();

    }
}
