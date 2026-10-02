package com.airline.service;

import com.airline.playload.request.FareRequest;
import com.airline.playload.response.FareResponse;

import java.util.List;
import java.util.Map;

public interface FareService {

    FareResponse createFare(FareRequest fareRequest);
    FareResponse updateFare(Long id, FareRequest fareRequest);
    FareResponse getFareById(Long id);
    List<FareResponse> getFaresByFlightCabinClassId(
            Long flightId, Long cabinClassId
    );
    void deleteFare(Long id);

    List<FareResponse> getAllFares();

    Map<Long, FareResponse> getLowestFarePerFlight(
            List<Long> flightIds, Long cabinClassId
    );

    Map<Long, FareResponse> getFaresByIds(List<Long> ids);
}
