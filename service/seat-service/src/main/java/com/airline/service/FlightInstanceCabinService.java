package com.airline.service;

import com.airline.model.FlightInstanceCabin;
import com.airline.playload.request.FlightInstanceCabinRequest;
import com.airline.playload.response.FlightInstanceCabinResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightInstanceCabinService {

    FlightInstanceCabinResponse createFlightInstanceCabin(FlightInstanceCabinRequest request);

    FlightInstanceCabinResponse getFlightInstanceCabinById(Long id);

    Page<FlightInstanceCabinResponse> getFlightInstance(Long id, Pageable pageable);

    FlightInstanceCabinResponse getByFlightInstanceIdAndCabinClassId(
            Long flightInstanceId, Long cabinClassId);

    FlightInstanceCabinResponse updateFlightInstanceCabin(
            Long id, FlightInstanceCabinRequest request);

    void deleteFlightInstanceCabin(Long id);

}
