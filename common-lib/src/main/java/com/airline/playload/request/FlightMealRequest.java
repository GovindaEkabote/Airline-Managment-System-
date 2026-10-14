package com.airline.playload.request;

import jakarta.persistence.ManyToOne;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightMealRequest {

    @NonNull
    private Long flightId;

    @NonNull
    private Long mealId;

    private Boolean available;

    private Double price;

    private Integer displayOrder;
}
