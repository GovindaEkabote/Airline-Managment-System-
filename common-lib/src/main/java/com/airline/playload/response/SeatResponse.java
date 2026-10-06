package com.airline.playload.response;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatResponse {

    private Long id;

    private String seatNumber;

    private int seatRow;

    private Character columnLetter;

    private String seatType;

    private BigDecimal basePrice;

    private BigDecimal premiumSurcharge;

    private BigDecimal totalPrice;

    private boolean available;

    private boolean blocked;

    private boolean active;

    private boolean bookable;

    private boolean emergencyExit;

    private boolean extraLegroom;

    private boolean powerOutlet;

    private boolean tvScreen;

    private boolean extraWidth;

    private Integer seatPitch;

    private Integer seatWidth;

    private Long seatMapId;

    private Long cabinClassId;

    private String cabinClassName;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private Long version;
}
