package com.airline.service.impl;

import com.airline.config.feignclient.AirlineClient;
import com.airline.config.feignclient.AirportClient;
import com.airline.enums.FlightStatus;
import com.airline.exception.DuplicateResourceException;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.FlightMapper;
import com.airline.model.Flight;
import com.airline.playload.request.FlightRequest;
import com.airline.playload.response.AircraftResponse;
import com.airline.playload.response.AirlineResponse;
import com.airline.playload.response.AirportResponse;
import com.airline.playload.response.FlightResponse;
import com.airline.repository.FlightRepository;
import com.airline.service.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final AirlineClient airlineClient;
    private final AirportClient airportClient;

    @Override
    public FlightResponse createFlight(
            Long userId,
            FlightRequest flightRequest) {

        AirlineResponse airlineResponse =
                airlineClient.getAirlineByOwnerId(userId);

    if (flightRepository.existsByFlightNumber(
            flightRequest.getFlightNumber())){
        throw new DuplicateResourceException("Flight number already exists", "Flight", flightRequest.getFlightNumber());
    }
        Flight flight = FlightMapper.toEntity(flightRequest);
        flight.setAirlineId(airlineResponse.getId());
        if (flight.getScheduledDeparture() == null ||
                flight.getScheduledArrival() == null) {

            throw new IllegalArgumentException(
                    "Scheduled departure and arrival are required"
            );
        }

        if (!flight.getScheduledArrival()
                .isAfter(flight.getScheduledDeparture())) {

            throw new IllegalArgumentException(
                    "Scheduled arrival must be after scheduled departure"
            );
        }
        Flight saved = flightRepository.save(flight);
        return convertIntoFlightResponse(saved);
    }


    @Override
    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(this::convertIntoFlightResponse)
                .toList();
    }

    @Override
    public Page<FlightResponse> getFlightByAirline(Long userId,
                                                   Long departureAirportId,
                                                   Long arrivalAirportId,
                                                   Pageable pageable) {
        AirlineResponse airlineResponse = airlineClient.getAirlineByOwnerId(userId);
        return flightRepository
                .findByAirlineId(airlineResponse.getId(), departureAirportId, arrivalAirportId, pageable)
                .map(this::convertIntoFlightResponse);
    }

    @Override
    public FlightResponse getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
        return convertIntoFlightResponse(flight);
    }

    @Override
    public FlightResponse updateFlight(
            Long id,
            FlightRequest flightRequest) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Flight not found with id: " + id));

        if (flightRequest.getFlightNumber() != null &&
                flightRepository.existsByFlightNumberAndIdNot(
                        flightRequest.getFlightNumber(), id)) {
            throw new DuplicateResourceException(
                    "Flight number already exists",
                    "Flight",
                    flightRequest.getFlightNumber());
        }

        FlightMapper.updateEntity(flight, flightRequest);
        Flight updated = flightRepository.save(flight);
        return convertIntoFlightResponse(updated);
    }

    @Override
    public FlightResponse changeStatus(Long id, FlightStatus status) {
        Flight existing = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
        existing.setStatus(status);
        Flight updated = flightRepository.save(existing);
        return convertIntoFlightResponse(updated);
    }

    @Override
    public void deleteFlight(Long userId,Long id) {
        AirlineResponse airlineResponse = airlineClient.getAirlineByOwnerId(userId);
        Flight flight = flightRepository.findByAirlineIdAndId(airlineResponse.getId(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
        flightRepository.delete(flight);
    }

    private FlightResponse convertIntoFlightResponse(Flight flight){

        AircraftResponse aircraftId =airlineClient.getAircraftById(flight.getAircraftId());
        AirlineResponse airlineId =airlineClient.getAirlineById(flight.getAirlineId());
        AirportResponse departureAirportId =airportClient.getAirport(flight.getDepartureAirportId());
        AirportResponse arrivalAirportId =airportClient.getAirport(flight.getArrivalAirportId());
        return FlightMapper.toResponse(
                flight,
                aircraftId,
                airlineId,
                departureAirportId,
                arrivalAirportId
        );
    }
}
