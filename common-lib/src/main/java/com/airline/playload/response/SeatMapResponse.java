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

    // ✅ Flat reference to the parent — no recursion
    private Long cabinClassId;
    private String cabinClassName;
    private String cabinClassCode;

    private Integer totalSeats;
    private Integer availableSeats;
    private Integer occupiedSeats;

    private Integer windowsSeats;
    private Integer aisleSeats;
    private Integer middleSeats;
    private Integer premiumSeats;
    private Integer emergencyExitSeats;

    // optional, if you have a code like ECONOMY/BUSINESS

    private Instant createdAt;
    private Instant updatedAt;
}
