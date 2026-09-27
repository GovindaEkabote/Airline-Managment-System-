package com.airline.playload.request;

import com.airline.enums.AircraftTypeStatus;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftTypeRequest {

    @Size(max = 100, message = "Manufacturer cannot exceed 100 characters")
    private String manufacturer;

    @Size(max = 100, message = "Model cannot exceed 100 characters")
    private String model;

    @Size(min = 3, max = 4,
            message = "ICAO type code must contain 3 to 4 characters")
    @Pattern(
            regexp = "^[A-Z0-9]{3,4}$",
            message = "ICAO type code must contain only uppercase letters and digits"
    )
    private String icaoTypeCode;

    @Size(max = 10, message = "IATA type code cannot exceed 10 characters")
    private String iataTypeCode;

    @Min(value = 1, message = "Engine count must be at least 1")
    @Max(value = 8, message = "Engine count cannot exceed 8")
    private Integer engineCount;

    @Size(max = 30, message = "Engine type cannot exceed 30 characters")
    private String engineType;

    @Min(value = 1, message = "Passenger capacity must be at least 1")
    @Max(value = 1000, message = "Passenger capacity cannot exceed 1000")
    private Integer passengerCapacity;

    @PositiveOrZero(message = "Cargo capacity cannot be negative")
    private Integer cargoCapacityKg;

    @Positive(message = "Maximum range must be greater than 0")
    private Integer maxRangeKm;

    @Positive(message = "Cruise speed must be greater than 0")
    private Integer cruiseSpeedKmh;

    @Positive(message = "Maximum takeoff weight must be greater than 0")
    private Integer maxTakeoffWeightKg;

    private AircraftTypeStatus status;
}