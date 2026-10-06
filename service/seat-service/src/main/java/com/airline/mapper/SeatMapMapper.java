package com.airline.mapper;

import com.airline.model.CabinClass;
import com.airline.model.SeatMap;
import com.airline.playload.request.AirlineRequest;
import com.airline.playload.request.CabinClassRequest;
import com.airline.playload.request.SeatMapRequest;
import com.airline.playload.response.AirlineResponse;
import com.airline.playload.response.SeatMapResponse;

import java.util.ArrayList;

public class SeatMapMapper {

    public static SeatMap toEntity(
            SeatMapRequest request,
            CabinClass cabinClass
    ){
        if (request == null){
            return  null;
        }

        return SeatMap.builder()
                .name(request.getName().trim())
                .totalRows(request.getTotalRows())
                .rightSeatsPerRow(request.getRightSeatsPerRow())
                .leftSeatsPerRow(request.getLeftSeatsPerRow())
                .cabinClass(cabinClass)
                .build();
    }

    public static SeatMapResponse toResponse(SeatMap map
                                             /* List<SeatResponse> seats */)
    {
        if (map == null) return null;

        return SeatMapResponse.builder()
                .id(map.getId())
                .name(map.getName())
                .totalRows(map.getTotalRows())
                .leftSeatsPerRow(map.getLeftSeatsPerRow())
                .rightSeatsPerRow(map.getRightSeatsPerRow())
                .airlineId(map.getAirlineId())
                .cabinClass(CabinClassMapper.toResponse(map.getCabinClass()))
//                .seats(seats)
                .createdAt(map.getCreatedAt())
                .updatedAt(map.getUpdatedAt())
                .build();
    }

    public static void updateEntity(
            SeatMapRequest seatMapRequest,
            SeatMap seatMap){

        seatMap.setName(seatMapRequest.getName());
        seatMap.setTotalRows(seatMapRequest.getTotalRows());
        seatMap.setLeftSeatsPerRow(seatMapRequest.getLeftSeatsPerRow());
        seatMap.setRightSeatsPerRow(seatMapRequest.getRightSeatsPerRow());
    }
}
