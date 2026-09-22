package com.airline.playload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityResponse {

    private Long id;
    private String name;
    private String cityCode;
    private String country;
    private String countryCode;
    private String regionCode;
    private String timeZoneId;
    private boolean active;
}
