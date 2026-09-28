package com.airline.service;

import com.airline.enums.FlightStatus;
import com.airline.playload.request.FlightRequest;
import com.airline.playload.response.FlightResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface FlightService {

    FlightResponse createFlight(Long airlineId, FlightRequest flightRequest);
    List<FlightResponse> getAllFlights();
    Page<FlightResponse> getFlightByAirline(Long airlineId,
                                            Long departureAirportId,
                                            Long arrivalAirportId,
                                            Pageable pageable);
    FlightResponse getFlightById(Long id);
    FlightResponse updateFlight(Long id, FlightRequest flightRequest);
    FlightResponse changeStatus(Long id, FlightStatus status);
    void deleteFlight(Long airlineId, Long id);
}
