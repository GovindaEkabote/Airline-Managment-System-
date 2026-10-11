package com.airline.mapper;

import com.airline.model.Seat;
import com.airline.playload.response.SeatResponse;

public class SeatMapper {

    public static SeatResponse toResponse(Seat seat){
        return SeatResponse.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .seatRow(seat.getSeatRow())
                .columnLetter(seat.getColumnLetter())
                .seatType(seat.getSeatType().name())

                .basePrice(seat.getBasePrice())
                .premiumSurcharge(seat.getPremiumSurcharge())
                .totalPrice(seat.getTotalPrice())

                .available(seat.getIsAvailable())
                .blocked(seat.getIsBlocked())
                .active(Boolean.TRUE.equals(seat.getIsActive()))
                .bookable(seat.isBookable())

                .emergencyExit(seat.getIsEmergencyExit())
                .extraLegroom(seat.getIsExtraLegroom())
                .powerOutlet(seat.getIsPowerOutlet())
                .tvScreen(seat.getIsTvScreen())
                .extraWidth(seat.getIsExtraWidth())

                .seatPitch(seat.getSeatPitch())
                .seatWidth(seat.getSeatWidth())

                .seatMapId(seat.getSeatMap().getId())
                .cabinClassId(seat.getCabinClass().getId())
                .cabinClassName(seat.getCabinClass().getCode())
                .createdDate(seat.getCreatedDate())
                .lastModifiedDate(seat.getLastModifiedDate())
                .version(seat.getVersion())

                .build();

    }
}
