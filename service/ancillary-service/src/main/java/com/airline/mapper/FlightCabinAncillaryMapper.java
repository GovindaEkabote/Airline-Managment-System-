package com.airline.mapper;

import com.airline.model.Ancillary;
import com.airline.model.FlightCabinAncillary;
import com.airline.playload.request.FlightCabinAncillaryRequest;
import com.airline.playload.response.FlightCabinAncillaryResponse;
import com.airline.playload.response.InsuranceConverageResponse;

import java.util.List;

public class FlightCabinAncillaryMapper {

    private void FlightCabinAncillaryMapper(){

    }

    public static FlightCabinAncillary toEntity(
            FlightCabinAncillaryRequest request,
            Ancillary ancillary
    ){
        if (request == null){
            return null;
        }

        return FlightCabinAncillary.builder()
                .flightId(request.getFlightId())
                .cabinClassId(request.getCabinClassId())
                .ancillary(ancillary)
                .available(request.getAvailable())
                .maxQuantity(request.getMaxQuantity())
                .price(request.getPrice())
                .includedInFare(request.getIncludedInFare())
                .build();
    }

    public static FlightCabinAncillaryResponse toResponse(
            FlightCabinAncillary request,
            List<InsuranceConverageResponse> converageResponses
    ){
        if (request == null){
            return  null;
        }

        return FlightCabinAncillaryResponse.builder()
                .id(request.getId())
                .flightId(request.getFlightId())
                .cabinClassId(request.getCabinClassId())
                .ancillaryResponse(
                        AncillaryMapper.toResponse(request.getAncillary(),converageResponses))
                .available(request.getAvailable())
                .price(request.getPrice())
                .includedInFare(request.getIncludedInFare())
                .maxQuantity(request.getMaxQuantity())
                .build();
    }


}
