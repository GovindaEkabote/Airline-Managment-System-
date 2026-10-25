package com.airline.service.impl;

import com.airline.config.feignclient.AirlineClient;
import com.airline.config.feignclient.AirportClient;
import com.airline.enums.FlightStatus;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.FlightInstanceMapper;
import com.airline.mapper.FlightMapper;
import com.airline.model.Flight;
import com.airline.model.FlightInstance;
import com.airline.playload.request.FlightInstanceRequest;
import com.airline.playload.response.*;
import com.airline.repository.FlightInstanceRepository;
import com.airline.repository.FlightRepository;
import com.airline.service.FlightInstanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlightInstanceServiceImpl implements FlightInstanceService {

    private final FlightInstanceRepository flightInstanceRepository;
    private final FlightRepository flightRepository;
    private final AirlineClient airlineClient;
    private final AirportClient airportClient;

    @Override
    public FlightInstanceResponse createFlightInstance(
            Long airlineId,
            FlightInstanceRequest request) {


        Flight flight = flightRepository
                .findById(request.getFlightId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Flight not found",
                                "id",
                                request.getFlightId()
                        ));

        if (!flight.getAirlineId().equals(airlineId)){
            throw new IllegalArgumentException(
                    "Flight does not belong to the specified airline"
            );
        }

        AircraftResponse aircraftResponse =airlineClient.getAircraftById(flight.getAircraftId());

        FlightInstance flightInstance =
                FlightInstanceMapper.toEntity(
                        request,
                        flight
                );

        flightInstance.setTotalSeats(
                aircraftResponse.getTotalSeats()
        );

        flightInstance.setAvailableSeats(
                aircraftResponse.getTotalSeats()
        );

        FlightInstance savedFlightInstance =
                flightInstanceRepository.save(flightInstance);

        // publish kafka event,

        return convertIntoFlightInstanceResponse(
                savedFlightInstance
        );
    }

    @Override
    public FlightInstanceResponse getFlightInstanceById(Long id) {
        FlightInstance flightInstance =
                flightInstanceRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Flight instance not found",
                                "id",
                                id
                        ));
        return convertIntoFlightInstanceResponse(
                flightInstance
        );
    }

    @Override
    public Page<FlightInstanceResponse> getAllFlightInstances(Pageable pageable) {
        return flightInstanceRepository.findAll(pageable)
                .map(this::convertIntoFlightInstanceResponse);
    }

    @Override
    public Page<FlightInstanceResponse> getFlightInstancesByFlightId(
            Long flightId,
            Pageable pageable) {
        return flightInstanceRepository.findByFlightId(flightId, pageable)
                .map(this::convertIntoFlightInstanceResponse);
    }

    @Override
    public Page<FlightInstanceResponse> getFlightInstancesByScheduleId(
            Long scheduleId,
            Pageable pageable) {
        return flightInstanceRepository
                .findByScheduleId(scheduleId, pageable)
                .map(this::convertIntoFlightInstanceResponse);
    }

    @Override
    @Transactional
    public FlightInstanceResponse updateFlightInstance(
            Long id,
            FlightInstanceRequest request) {
        FlightInstance flightInstance =
                flightInstanceRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Flight instance not found",
                                        "id",
                                        id
                                ));

        FlightInstanceMapper.updateEntity(flightInstance, request);
        return convertIntoFlightInstanceResponse(flightInstanceRepository.save(flightInstance));
    }

    @Override
    public FlightInstanceResponse changeStatus(Long id, FlightStatus status) {
        FlightInstance flightInstance =
                flightInstanceRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Flight instance not found",
                                        "id",
                                        id
                                ));
        flightInstance.setStatus(status);
        FlightInstance savedFlightInstance =
                flightInstanceRepository.save(flightInstance);
        return convertIntoFlightInstanceResponse(savedFlightInstance);
    }

    @Override
    public void deleteFlightInstance(Long id) {
        FlightInstance flightInstance =
                flightInstanceRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Flight instance not found",
                                        "id",
                                        id
                                ));

        flightInstanceRepository.delete(flightInstance);
    }

    @Override
    public Page<FlightInstanceResponse> getByAirlineId(
            Long airlineId,
            Long departureAirportId,
            Long arrivalAirportId,
            Long flightId,
            LocalDate onDate,
            Pageable pageable) {
        LocalDateTime start = onDate != null ? onDate.atStartOfDay() : null;
        LocalDateTime end = onDate != null ? onDate.plusDays(1).atStartOfDay() : null;
        return flightInstanceRepository.findByAirlineId(
                airlineId,
                departureAirportId,
                arrivalAirportId,
                flightId,
                start,
                end,
                pageable
        ).map(this::convertIntoFlightInstanceResponse);
    }

    private FlightInstanceResponse convertIntoFlightInstanceResponse(
            FlightInstance flightInstance) {

        AirlineResponse airlineResponse =
                airlineClient.getAirlineById(flightInstance.getAirlineId());

        AirportResponse departureAirportResponse = airportClient.getAirport(flightInstance.getDepartureAirportId());

        AirportResponse arrivalAirportResponse =
                airportClient.getAirport(flightInstance.getArrivalAirportId());

        AircraftResponse aircraftResponse =
                airlineClient.getAircraftById(flightInstance.getFlight().getAircraftId());

        FlightResponse flightResponse =
                FlightMapper.toResponse(
                        flightInstance.getFlight(),
                        aircraftResponse,
                        airlineResponse,
                        departureAirportResponse,
                        arrivalAirportResponse
                );

        return FlightInstanceMapper.toResponse(
                flightInstance,
                flightResponse,
                aircraftResponse,
                airlineResponse,
                departureAirportResponse,
                arrivalAirportResponse
        );
    }
}
