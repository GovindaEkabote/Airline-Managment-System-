package com.airline.playload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightInstanceCabinRequest {

    @NotNull
    private Long flightId;

    @NotNull
    private Long flightInstanceId;
    @NotNull
    private Long cabinClassId;
    private int totalSeats;
    private int bookedSeats;
    @NotNull
    @Positive
    private Double baseFare;

    @NotNull
    @PositiveOrZero
    private Double taxesAndFees;
    @NotNull
    @PositiveOrZero
    private Double airlineFees;
    private Double currentPrice;
    private Boolean isActive;

}
