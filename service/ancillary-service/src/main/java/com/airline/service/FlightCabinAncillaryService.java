package com.airline.service;

import com.airline.enums.AncillaryType;
import com.airline.playload.request.FlightCabinAncillaryRequest;
import com.airline.playload.response.FlightCabinAncillaryResponse;

import java.util.List;

public interface FlightCabinAncillaryService {

    FlightCabinAncillaryResponse create(FlightCabinAncillaryRequest request);
    FlightCabinAncillaryResponse getById(Long id);
    List<FlightCabinAncillaryResponse> getByFlightAndCabinClass(Long flightId, Long cabinClassId);
    List<FlightCabinAncillaryResponse> getAllByIds(List<Long> ids);
    FlightCabinAncillaryResponse getByFlightIdAndCabinClassAndtype(
            Long flightId,
            Long cabinClassId,
            AncillaryType ancillaryType
    );
    List<FlightCabinAncillaryResponse> getAllByFlightIdAndCabinClassIdAndType(
            Long flightId,
            Long cabinClassId,
            AncillaryType ancillaryType
    );
    FlightCabinAncillaryResponse update(Long id, FlightCabinAncillaryRequest request);
    void  delete(Long id);
    Double calculateAncillaryPrice(List<Long> ancillaryIds);

}
