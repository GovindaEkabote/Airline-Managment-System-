package com.airline.playload.response;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatMapResponse {

    private Long id;
    private String name;

    private int totalRows;

    private int rightSeatsPerRow;

    private int leftSeatsPerRow;

    private Long airlineId;
    private String iataCode;
    private String airlineName;

    private List<SeatResponse> seats;

    private CabinClassResponse cabinClass;

    private Instant createdAt;

    private Instant updatedAt;
}
