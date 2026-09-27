package com.airline.playload.request;

import com.airline.enums.AirlineStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirlineRequest {

    @NotBlank(message = "IATA code is required")
    @Size(min = 2, max = 2, message = "IATA code must contain exactly 2 characters")
    @Pattern(
            regexp = "^[A-Z]{2}$",
            message = "IATA code must contain exactly 2 uppercase letters"
    )
    private String iataCode;

    @NotBlank(message = "ICAO code is required")
    @Size(min = 3, max = 3, message = "ICAO code must contain exactly 3 characters")
    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "ICAO code must contain exactly 3 uppercase letters"
    )
    private String icaoCode;

    @NotBlank(message = "Airline name is required")
    @Size(max = 100, message = "Airline name cannot exceed 100 characters")
    private String name;

    @Size(max = 100, message = "Alias cannot exceed 100 characters")
    private String alias;

    @Size(max = 500, message = "Logo URL cannot exceed 500 characters")
    private String logoUrl;

    @Size(max = 500, message = "Website cannot exceed 500 characters")
    private String website;
    private AirlineStatus status;

    @Size(max = 50, message = "Alliance cannot exceed 50 characters")
    private String alliance;

    private Long headquartersCityId;

    private String supportEmail;
    private String supportPhone;
    private String supportHours;
}