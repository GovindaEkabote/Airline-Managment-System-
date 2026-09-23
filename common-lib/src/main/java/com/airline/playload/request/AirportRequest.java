package com.airline.playload.request;

import com.airline.embeddable.Address;
import com.airline.embeddable.GeoCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirportRequest {

    @Pattern(regexp = "^[A-Z]{3}$", message = "IATA code must be 3 uppercase letters")
    @Size(min = 3, max = 3, message = "IATA code must be 3 uppercase letters")
    @Column(name = "iata_code", unique = true, nullable = false, length = 3)
    @NotNull(message = "IATA code must not be null")
    private String iataCode;
    @NotBlank(message = "ICAO code must not be blank")
    @Size(min = 4, max = 4, message = "ICAO code must be exactly 4 characters")
    @Pattern(
            regexp = "^[A-Z]{4}$",
            message = "ICAO code must contain exactly 4 uppercase letters"
    )
    private String icaoCode;
    @NotBlank(message = "Airport name must not be blank")
    private String name;
    @Valid
    private Address address;
    @Valid
    private GeoCode geoCode;
    private ZoneId timeZone;
    private Boolean active;

    @Valid
    private Long cityId;


}
