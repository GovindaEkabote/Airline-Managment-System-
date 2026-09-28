package com.airline.playload.request;

import com.airline.enums.FlightStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightRequest {

    /**
     * Commercial flight number.
     * Example: AI101, EK501, 6E203
     */
    @NotBlank(message = "Flight number cannot be blank")
    @Size(max = 10, message = "Flight number must be 10 characters or less")
    @Pattern(
            regexp = "^[A-Z]{2}[0-9]{3}$",
            message = "Flight number must be in the format AI101, EK501, 6E203"
    )
    private String flightNumber;
    @NotNull(message = "Airline ID cannot be null")
    @Positive(message = "Airline ID must be positive")
    private Long airlineId;

    @NotNull(message = "Aircraft ID cannot be null")
    @Positive(message = "Aircraft ID must be positive")
    private Long aircraftId;

    @NotNull(message = "Departure airport ID is required")
    @Positive(message = "Departure airport ID must be positive")
    private Long departureAirportId;


    @NotNull(message = "Arrival airport ID is required")
    @Positive(message = "Arrival airport ID must be positive")
    private Long arrivalAirportId;

    @NotNull(message = "Scheduled departure is required")
    private Instant scheduledDeparture;

    @NotNull(message = "Scheduled arrival is required")
    private Instant scheduledArrival;

    @Size(max = 20, message = "Departure terminal must not exceed 20 characters")
    private String departureTerminal;

    @Size(max = 20, message = "Departure gate must not exceed 20 characters")
    private String departureGate;

    @Size(max = 20, message = "Arrival terminal must not exceed 20 characters")
    private String arrivalTerminal;

    @Size(max = 20, message = "Arrival gate must not exceed 20 characters")
    private String arrivalGate;

    @PositiveOrZero(message = "Distance cannot be negative")
    private Integer distanceKm;
}
