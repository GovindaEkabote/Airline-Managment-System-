package com.airline.playload.request;

import com.airline.enums.CabinClassType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabinClassRequest {


    @NotBlank
    private String name;

    @NotNull
    private CabinClassType cabinClassType;
    @NotBlank
    private String code;
    private String description;
    @NotNull
    private Long aircraftId;
    private Integer displayOrder;
    private Long seatMapId;
    private Boolean isActive;
    private Boolean isBookable;
    private Integer typicalSeatPitch;
    private Integer typicalSeatWidth;

    private String seatType;


}
