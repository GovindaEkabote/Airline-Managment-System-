package com.airline.playload.request;

import jakarta.validation.constraints.*;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaggagePolicyRequest {

    @NotNull(message = "Fare ID is required")
    private Long fareId;

    @NotBlank(message = "Baggage policy name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @PositiveOrZero(message = "Cabin baggage maximum weight limit must be zero or positive")
    private Double cabinBaggageMaxWeightLimit;

    @PositiveOrZero(message = "Cabin baggage pieces must be zero or positive")
    private Integer cabinBaggagePieces;

    @PositiveOrZero(message = "Cabin baggage weight per piece must be zero or positive")
    private Double cabinBaggageWeightPerPiece;

    @PositiveOrZero(message = "Cabin baggage maximum dimensions must be zero or positive")
    private Double cabinBaggageMaxDimensions;

    @PositiveOrZero(message = "Check-in baggage maximum weight limit must be zero or positive")
    private Double checkInBaggageMaxWeightLimit;

    @PositiveOrZero(message = "Check-in baggage pieces must be zero or positive")
    private Integer checkInBaggagePieces;

    @PositiveOrZero(message = "Check-in baggage weight per piece must be zero or positive")
    private Double checkInBaggageWeightPerPiece;

    @PositiveOrZero(message = "Free check baggage allowance must be zero or positive")
    private Integer freeCheckBaggageAllowance;

    private Boolean priorityBaggage;

    private Boolean extraBaggageAllowed;

    @NotNull(message = "Airline ID is required")
    private Long airlineId;
}