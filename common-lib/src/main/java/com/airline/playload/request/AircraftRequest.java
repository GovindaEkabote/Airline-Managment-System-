package com.airline.playload.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftRequest {

    @NotBlank(message = "Registration number is required")
    @Size(max = 10, message = "Registration number must be at most 10 characters long")
    private String registrationNumber;

    @NotBlank(message = "Serial number is required")
    @Size(max = 50, message = "Serial number cannot exceed 50 characters")
    private String serialNumber;

    @Size(max = 6, message = "ICAO24 must contain at most 6 characters")
    @Pattern(
            regexp = "^[0-9A-Fa-f]{6}$",
            message = "ICAO24 must contain exactly 6 hexadecimal characters"
    )
    private String icao24;

    @NotNull(message = "Aircraft type ID is required")
    private Long aircraftTypeId;

    @NotNull(message = "Airline ID is required")
    private Long airlineId;

    @Min(value = 1900, message = "Manufacture year is invalid")
    @Max(value = 2100, message = "Manufacture year is invalid")
    private Integer manufactureYear;

    private java.time.LocalDate deliveryDate;

    private Boolean available;
}
