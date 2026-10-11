package com.airline.mapper;

import com.airline.enums.SeatAvailabilityStatus;
import com.airline.model.SeatInstance;
import com.airline.playload.response.SeatInstanceResponse;
import com.airline.playload.response.SeatResponse;
import com.airline.service.impl.SeatServiceImpl;

public class SeatInstanceMapper {


    public static SeatInstanceResponse toResponse(
            SeatInstance seatInstance
    ){

        return SeatInstanceResponse.builder()
                .id(seatInstance.getId())
                .flightId(seatInstance.getFlightId())
                .seatId(seatInstance.getSeat() != null ? seatInstance.getId() : null)
                .seatNumber(seatInstance.getSeat() != null ? seatInstance.getSeat().getSeatNumber() : null)
                .seatType(seatInstance.getSeat() != null ? seatInstance.getSeat().getSeatType().name() : null)
                .seatPosition(seatInstance.getSeat() != null ? seatInstance.getSeat().getFullPosition() : null)
                .seat(SeatMapper.toResponse(seatInstance.getSeat()))
                .price(seatInstance.getPremiumSurcharge().doubleValue())
                .status(seatInstance.getStatus())
                .flightInstanceId(seatInstance.getFlightInstanceId())
                .isBooked(seatInstance.isBooked())
                .flightCabinId(seatInstance.getFlightInstanceCabin() != null ? seatInstance.getFlightInstanceCabin().getId() : null)
//                .flightCabinClassType(seatInstance.getF)
                .fare(seatInstance.getFare())
                .version(seatInstance.getVersion())
                .createdAt(seatInstance.getCreatedAt())
                .updatedAt(seatInstance.getUpdatedAt())
                .isAvailable(seatInstance.isAvailable())
                .isOccupied(seatInstance.getStatus() == SeatAvailabilityStatus.OCCUPIED)
//                .seatCharacteristics(
//                        String.valueOf(seatInstance.getSeat() != null
//                                ? seatInstance.getSeat().getColumnLetter()
//                                : null)
//                )
                .build();

    }
}
