package com.airline.playload.request;

import com.airline.enums.CoverageType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceCoverageRequest {

    @NotNull(message = "Ancillary ID is required")
    private  Long ancillaryId;

    @NotNull(message = "coverageType is required")
    private CoverageType coverageType;

    @NotNull(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Coverage amount is required")
    @PositiveOrZero(message = "Coverage amount must be positive or zero")
    private Double coverageAmount;

    private Boolean isFlat;

    private String claimCondition;

    private String emergencyContact;

    private Integer displayOrder;

    private Boolean active;
}
