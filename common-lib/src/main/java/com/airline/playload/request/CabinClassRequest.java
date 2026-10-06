package com.airline.playload.request;

import com.airline.enums.CabinClassType;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabinClassRequest {

    private Long seatMapId;

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
}
