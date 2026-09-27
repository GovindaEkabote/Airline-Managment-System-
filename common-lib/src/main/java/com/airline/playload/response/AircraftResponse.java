package com.airline.playload.response;

import com.airline.enums.AircraftStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftResponse {

    private Long id;

    private String registrationNumber;

    private String serialNumber;

    private String icao24;

    private Long aircraftTypeId;

    private AircraftTypeResponse aircraftType;

    private Long airlineId;

    private AirlineResponse airline;

    private Integer manufactureYear;

    private LocalDate deliveryDate;

    private AircraftStatus status;

    private Boolean available;

    private Long updatedById;

    private Instant createdAt;

    private Instant updatedAt;

}
