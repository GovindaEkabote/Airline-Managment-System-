package com.airline.playload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
public class CityRequest {

    private String name;
    private String cityCode;
    private String country;
    private String countryCode;
    private String regionCode;
    private String timeZoneId;
    private Boolean active;
}
