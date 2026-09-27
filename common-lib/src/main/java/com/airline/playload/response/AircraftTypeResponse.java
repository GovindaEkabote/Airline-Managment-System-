package com.airline.playload.response;

import com.airline.enums.AircraftStatus;
import com.airline.enums.AircraftTypeStatus;
import jakarta.persistence.Column;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AircraftTypeResponse {

    private Long id;

    private String manufacturer;

    private String model;

    private String icaoTypeCode;

    private String iataTypeCode;

    private Integer engineCount;

    private String engineType;

    private Integer passengerCapacity;

    private Integer cargoCapacityKg;

    private Integer maxRangeKm;

    private Integer cruiseSpeedKmh;

    private Integer maxTakeoffWeightKg;

    private AircraftTypeStatus status;

    private Instant createdAt;

    private Instant updatedAt;

}
