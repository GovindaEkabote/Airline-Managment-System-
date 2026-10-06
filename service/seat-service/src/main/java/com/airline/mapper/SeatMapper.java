package com.airline.mapper;

import com.airline.enums.SeatType;
import com.airline.model.Seat;
import com.airline.playload.request.SeatRequest;

public class SeatMapper {

    public static Seat toEntity(SeatRequest request) {
        if (request == null) {
            return null;
        }
        return Seat.builder()
                .seatNumber(request.getSeatNumber())
                .seatRow(request.getSeatRow())
                .columnLetter(request.getColumnLetter())
                .seatType(SeatType.valueOf(request.getSeatType()))
                .basePrice(request.getBasePrice())
                .premiumSurcharge(request.getPremiumSurcharge())
                .isAvailable(request.getIsAvailable() != null ? request.getIsAvailable() : true)
                .isBlocked(request.getIsBlocked() != null ? request.getIsBlocked() : false)
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .isEmergencyExit(request.getIsEmergencyExit() != null ? request.getIsEmergencyExit() : false)
                .isExtraLegroom(request.getIsExtraLegroom() != null ? request.getIsExtraLegroom() : false)
                .isPowerOutlet(request.getIsPowerOutlet() != null ? request.getIsPowerOutlet() : false)
                .isTvScreen(request.getIsTvScreen() != null ? request.getIsTvScreen() : false)
                .isExtraWidth(request.getIsExtraWidth() != null ? request.getIsExtraWidth() : false)
                .seatPitch(request.getSeatPitch())
                .seatWidth(request.getSeatWidth())
                .seatMapId(request.getSeatMapId())
                .cabinClassId(request.getCabinClassId())
                .build();
    }
}
