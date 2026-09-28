package com.airline.service;

import com.airline.enums.FlightStatus;
import com.airline.model.FlightInstance;
import com.airline.playload.request.FlightInstanceRequest;
import com.airline.playload.response.FlightInstanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface FlightInstanceService {

    FlightInstanceResponse createFlightInstance(
            Long airlineId,
            FlightInstanceRequest request
    );

    FlightInstanceResponse getFlightInstanceById( Long id );

    Page<FlightInstanceResponse> getAllFlightInstances(Pageable pageable );

    Page<FlightInstanceResponse> getFlightInstancesByFlightId( Long flightId, Pageable pageable );

    Page<FlightInstanceResponse> getFlightInstancesByScheduleId( Long scheduleId, Pageable pageable );

    FlightInstanceResponse updateFlightInstance( Long id, FlightInstanceRequest request );

    FlightInstanceResponse changeStatus( Long id, FlightStatus status );
    void deleteFlightInstance( Long id );

    Page<FlightInstanceResponse> getByAirlineId(
            Long airlineId,
            Long departureAirportId,
            Long arrivalAirportId,
            Long flightId,
            LocalDate onDate,
            Pageable pageable );


}
