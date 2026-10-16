package com.airline.playload.request;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightCabinAncillaryRequest {

    @NotNull(message = "Flight ID is required")
    private Long flightId;
    @NotNull(message = "Cabin Class ID is required")
    private Long cabinClassId;
    @NotNull(message = "Ancillary ID is required")
    private Long ancillaryId;
    @NotNull(message = "Availability status is required")
    private Boolean available;

    private Integer maxQuantity;

    private Double price;

    @NotNull(message = "Include in Fare status is requird")
    private Boolean includedInFare;
}
