package com.airline.playload.response;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightMealResponse {

    private Long id;

    private Long flightId;

    private MealResponse meal;

    private Boolean available;

    private Double price;

    private Integer displayOrder;
}
