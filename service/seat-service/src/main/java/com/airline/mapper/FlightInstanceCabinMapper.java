package com.airline.mapper;

import com.airline.enums.CabinClassType;
import com.airline.model.FlightInstanceCabin;
import com.airline.playload.response.FlightInstanceCabinResponse;

import java.util.stream.Collectors;

public class FlightInstanceCabinMapper {

    public static FlightInstanceCabinResponse  toResponse(FlightInstanceCabin response){

        if (response == null){
            return null;
        }

        return FlightInstanceCabinResponse.builder()
                .id(response.getId())
                .flightInstanceId(response.getFlightInstanceId())
                .cabinClassType(CabinClassType.valueOf(response.getCabinClass().getCode()))
                .cabinClass(CabinClassMapper.toResponse(
                        response.getCabinClass(), response.getCabinClass().getSeatMap()
                ))
                .seats(response.getSeatInstances() != null ?
                       response.getSeatInstances().stream().map(
                               SeatInstanceMapper::toResponse
                        ).collect(Collectors.toList()) : null)
                .seatMap(response.getCabinClass() != null && response.getCabinClass().getSeatMap() != null
                ? SeatMapMapper.toSimpleResponse(response.getCabinClass().getSeatMap()) : null)
                .totalSeats(response.getTotalSeats())
                .bookedSeats(response.getBookedSeats())
                .availableSeats(response.getAvailableSeats())
                .build();
    }
}
