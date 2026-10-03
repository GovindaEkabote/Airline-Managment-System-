package com.airline.playload.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaggagePolicyResponse {

    private Long id;

    private Long fareId;

    private String name;

    private String description;

    private Double cabinBaggageMaxWeightLimit;

    private Integer cabinBaggagePieces;

    private Double cabinBaggageWeightPerPiece;

    private Double cabinBaggageMaxDimensions;

    private Double checkInBaggageMaxWeightLimit;

    private Integer checkInBaggagePieces;

    private Double checkInBaggageWeightPerPiece;

    private Integer freeCheckBaggageAllowance;

    private Boolean priorityBaggage;

    private Boolean extraBaggageAllowed;

    private Long airlineId;

    private Instant createdAt;

    private Instant updatedAt;
}