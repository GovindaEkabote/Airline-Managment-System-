package com.airline.playload.request;

import com.airline.playload.response.FlightResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightScheduleRequest {

    @NotNull(message = "Flight cannot be null")
    @Positive(message = "Flight ID must be positive")
    private Long flightId;

    @NotNull(message = "Departure time cannot be null")
    private LocalTime departureTime;
    @NotNull(message = "Arrival time cannot be null")
    private LocalTime arrivalTime;
    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;
    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;
    @NotEmpty(message = "Operating days cannot be empty")
    private List<DayOfWeek> operatingDays;
    private Boolean isActive = true;
}
