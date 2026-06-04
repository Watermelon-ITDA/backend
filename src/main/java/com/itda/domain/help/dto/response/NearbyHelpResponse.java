package com.itda.domain.help.dto.response;

import com.itda.domain.help.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NearbyHelpResponse {
    private String userId;
    private String type;
    private String address;
    private Double latitude;
    private Double longitude;
    private Double distance;
    private Role role;
    private String content;
}
