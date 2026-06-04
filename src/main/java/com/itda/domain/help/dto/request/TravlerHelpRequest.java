package com.itda.domain.help.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TravlerHelpRequest {
    private String helpType;
    private String address;
    private Double latitude;
    private Double longitude;
    private String role;
    private String content;
    private Boolean active;
}
