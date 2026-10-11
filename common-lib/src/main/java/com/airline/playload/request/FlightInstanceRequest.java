package com.airline.playload.request;

import com.airline.enums.FlightStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightInstanceRequest {

    @NotNull(message = "Flight id is required")
    private Long flightId;



    private Long departureAirportId;

    private Long arrivalAirportId;

    private Long scheduleId;

    @NotNull(message = "Departure date and time is required")
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival date and time is required")
    private LocalDateTime arrivalDateTime;

    @NotNull(message = "Total seats are required")
    @Min(value = 1, message = "Total seats must be at least 1")
    private Integer totalSeats;

    private Integer availableSeats;

    private FlightStatus status;

    private Integer minAdvanceBookingDays;

    private Integer maxAdvanceBookingDays;

    private Boolean isActive;
}
