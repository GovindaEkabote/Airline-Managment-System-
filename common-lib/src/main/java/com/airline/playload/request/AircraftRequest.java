package com.airline.playload.request;

import com.airline.enums.AircraftStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftRequest {

    @Size(max = 10, message = "Registration number must be at most 10 characters long")
    private String registrationNumber;

    @Size(max = 50, message = "Serial number cannot exceed 50 characters")
    private String serialNumber;

    @Size(max = 6, message = "ICAO24 must contain at most 6 characters")
    @Pattern(
            regexp = "^[0-9A-Fa-f]{6}$",
            message = "ICAO24 must contain exactly 6 hexadecimal characters"
    )
    private String icao24;

    private Long aircraftTypeId;

    private Long airlineId;

    private AircraftStatus status;

    @Min(value = 1900, message = "Manufacture year is invalid")
    @Max(value = 2100, message = "Manufacture year is invalid")
    private Integer manufactureYear;

    private java.time.LocalDate deliveryDate;

    private Boolean available;

    private Integer totalSeats;

    private Integer economySeats;

    private Integer premiumEconomySeats;

    private Integer businessSeats;

    private Integer firstClassSeats;

    private Integer cruisingSpeedKmh;

    private Integer rangeKm;

    private Integer maxAltitudeFt;

    private LocalDate nextMaintenanceDate;

    private Long currentAirportId;
}
