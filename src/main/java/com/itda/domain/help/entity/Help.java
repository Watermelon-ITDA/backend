package com.itda.domain.help.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "help")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Help {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column
    private String type;

    @Column
    private String address;

    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String content;

    private Boolean active;

    public Help(String userId,
                String type,
                String address,
                Double latitude,
                Double longitude,
                Role role,
                String content,
                Boolean active
    ) {
        this.userId = userId;
        this.type = type;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.role = role;
        this.content = content;
        this.active = active;
    }
}

