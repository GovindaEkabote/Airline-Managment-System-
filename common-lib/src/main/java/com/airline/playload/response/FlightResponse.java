package com.airline.playload.response;

import com.airline.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightResponse {

    private Long id;
    private String flightNumber;
    private Long airlineId;
    private Long aircraftId;
    private Long departureAirportId;
    private Long arrivalAirportId;
    private Instant scheduledDeparture;
    private Instant scheduledArrival;
    private Instant actualDeparture;
    private Instant actualArrival;
    private FlightStatus status;
    private String departureTerminal;
    private String departureGate;
    private String arrivalTerminal;
    private String arrivalGate;
    private Integer durationMinutes;
    private Integer distanceKm;
    private Integer delayMinutes;
    private String disruptionReason;
    private boolean bookable;
    private Instant createdAt;
    private Instant updatedAt;
}
