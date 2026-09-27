package com.airline.playload.response;

import com.airline.embeddable.Support;
import com.airline.enums.AirlineStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirlineResponse {

    private Long id;

    private String iataCode;

    private String icaoCode;

    private String name;

    private String alias;

    private String logoUrl;

    private String website;

    private AirlineStatus status;

    private String alliance;

    private Long ownerId;

    private UserResponse owner;

    private Long headquartersCityId;

    private CityResponse headquartersCity;

    private Support support;

    private Long updatedById;

    private Instant createdAt;

    private Instant updatedAt;
}