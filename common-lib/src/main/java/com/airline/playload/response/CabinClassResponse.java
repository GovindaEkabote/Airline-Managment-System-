package com.airline.playload.response;

import com.airline.enums.CabinClassType;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabinClassResponse {

    private Long id;
    private SeatMapResponse seatMapId;

    private Long aircraftId;

    private CabinClassType cabinClassType;

    private String code;

    private String description;

    private Integer displayOrder;

    private Boolean isActive;

    private Boolean isBookable;

    private Integer typicalSeatPitch;

    private Integer typicalSeatWidth;

    private String seatType;

    private Instant createdAt;

    private Instant updatedAt;

}
