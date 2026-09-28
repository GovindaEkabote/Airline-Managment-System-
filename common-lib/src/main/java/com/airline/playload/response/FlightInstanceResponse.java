package com.airline.playload.response;

import com.airline.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightInstanceResponse {

    private Long id;

    private AirlineResponse airline;

    private FlightResponse flight;

    private Long departureAirportId;

    private Long arrivalAirportId;

    private Long scheduleId;

    private LocalDateTime departureDateTime;

    private LocalDateTime arrivalDateTime;

    private String formattedDuration;

    private Integer totalSeats;

    private Integer availableSeats;

    private FlightStatus status;

    private Integer minAdvanceBookingDays;

    private Integer maxAdvanceBookingDays;

    private Boolean isActive;
}
