package com.airline.playload.request;

import com.airline.enums.CabinClassType;
import com.airline.playload.response.AirportResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSearchRequest {

    private Long departureAirportId;

    private Long arrivalAirportId;

    @NotNull(message = "Departure date is required")
    private LocalDate departureAirportTime;

    @Min(value = 1, message = "At least one passenger is required")
    private Integer passengers;

    @NotNull(message = "Cabin class is required")
    private CabinClassType cabinClassType;

    // Filter parameters
    private List<Long> airlines;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String departureTimeRange;
    private String arrivalTimeRange;
    private Integer maxDuration;
    private String sortBy;
    private String sortOrder;
}
