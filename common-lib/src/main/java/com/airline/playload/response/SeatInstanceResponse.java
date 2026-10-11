package com.airline.playload.response;

import com.airline.enums.CabinClassType;
import com.airline.enums.SeatAvailabilityStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatInstanceResponse {

    private Long id;
    private Long flightId;
    private Long seatId;
    private String seatNumber;
    private String seatType;
    private String seatPosition;
    private SeatResponse seat;
    private Double price;
    private SeatAvailabilityStatus status;
    private Long flightInstanceId;
    private Boolean isBooked;
    private Long flightCabinId;
    private CabinClassType flightCabinClassType;
    private BigDecimal fare;
    private Long version;

    private Instant createdAt;
    private Instant updatedAt;

    private Boolean isAvailable;
    private Boolean isOccupied;
    private String seatCharacteristics;


}
