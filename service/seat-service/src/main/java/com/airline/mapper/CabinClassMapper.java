package com.airline.mapper;

import com.airline.model.CabinClass;
import com.airline.playload.request.CabinClassRequest;
import com.airline.playload.request.SeatMapRequest;
import com.airline.playload.response.CabinClassResponse;

public class CabinClassMapper {

    public static CabinClass toEntity(
            CabinClassRequest request
//            SeatMapRequest SeatMapRequest
    ){
        if (request == null){
            return null;
        }

        return CabinClass.builder()
                .aircraftId(request.getAircraftId())
                .cabinClassType(request.getCabinClassType())
                .code(request.getCode())
                .displayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0)
                .description(request.getDescription())
                .isActive(request.getIsActive() != null? request.getIsActive(): true)
                .isBookable(request.getIsBookable() != null ? request.getIsBookable() : true)
                .typicalSeatPitch(request.getTypicalSeatPitch())
                .seatType(request.getSeatType())
                .build();
    }

    public static CabinClassResponse toResponse(CabinClass cabinClass){
        if (cabinClass == null){
            return null;
        }
        return CabinClassResponse.builder()
                .id(cabinClass.getId())
//                .seatMapId(SeatMapMapper.toResponse(cabinClass.getSeatMap()))
                .aircraftId(cabinClass.getAircraftId())
                .cabinClassType(cabinClass.getCabinClassType())
                .code(cabinClass.getCode())
                .description(cabinClass.getDescription())
                .displayOrder(cabinClass.getDisplayOrder())
                .isActive(cabinClass.getIsActive())
                .isBookable(cabinClass.getIsBookable())
                .typicalSeatPitch(cabinClass.getTypicalSeatPitch())
                .typicalSeatWidth(cabinClass.getTypicalSeatWidth())
                .seatType(cabinClass.getSeatType())
                .createdAt(cabinClass.getCreatedAt())
                .updatedAt(cabinClass.getUpdatedAt())
                .build();
    }

    public static CabinClass updateEntity(
            CabinClass cabinClass,
            CabinClassRequest request
    ) {
        if (cabinClass == null || request == null) {
            return cabinClass;
        }

        if (request.getAircraftId() != null) {
            cabinClass.setAircraftId(request.getAircraftId());
        }

        if (request.getCabinClassType() != null) {
            cabinClass.setCabinClassType(request.getCabinClassType());
        }

        if (request.getCode() != null) {
            cabinClass.setCode(request.getCode());
        }

        if (request.getDescription() != null) {
            cabinClass.setDescription(request.getDescription());
        }

        if (request.getDisplayOrder() != null) {
            cabinClass.setDisplayOrder(request.getDisplayOrder());
        }

        if (request.getIsActive() != null) {
            cabinClass.setIsActive(request.getIsActive());
        }

        if (request.getIsBookable() != null) {
            cabinClass.setIsBookable(request.getIsBookable());
        }

        if (request.getTypicalSeatPitch() != null) {
            cabinClass.setTypicalSeatPitch(request.getTypicalSeatPitch());
        }

        if (request.getTypicalSeatWidth() != null) {
            cabinClass.setTypicalSeatWidth(request.getTypicalSeatWidth());
        }

        if (request.getSeatType() != null) {
            cabinClass.setSeatType(request.getSeatType());
        }

        return cabinClass;
    }

}
