package com.airline.mapper;

import com.airline.enums.FlightStatus;
import com.airline.model.Flight;
import com.airline.playload.request.FlightRequest;
import com.airline.playload.response.AircraftResponse;
import com.airline.playload.response.AirlineResponse;
import com.airline.playload.response.AirportResponse;
import com.airline.playload.response.FlightResponse;

import java.time.Duration;
import java.time.Instant;

public class FlightMapper {

    public static Flight toEntity(FlightRequest request){
        if (request == null){
            return null;
        }

        return Flight.builder()
                .flightNumber(request.getFlightNumber())
                .airlineId(request.getAirlineId())
                .aircraftId(request.getAircraftId())
                .departureAirportId(request.getDepartureAirportId())
                .arrivalAirportId(request.getArrivalAirportId())
                .scheduledDeparture(request.getScheduledDeparture())
                .scheduledArrival(request.getScheduledArrival())
                .departureTerminal(request.getDepartureTerminal())
                .departureGate(request.getDepartureGate())
                .arrivalTerminal(request.getArrivalTerminal())
                .arrivalGate(request.getArrivalGate())
                .distanceKm(request.getDistanceKm())
                .durationMinutes(calculateDuration(
                        request.getScheduledDeparture(),
                        request.getScheduledArrival()
                ))
                .status(FlightStatus.SCHEDULED)
                .delayMinutes(0)
                .bookable(true)
                .build();
    }

    public static FlightResponse toResponse(Flight flight,
                                            AircraftResponse  aircraftResponse,
                                            AirlineResponse airlineResponse,
                                            AirportResponse departureAirportResponse,
                                            AirportResponse arrivalAirportResponse){
        if (flight == null){
            return null;
        }

        return FlightResponse.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .airline(airlineResponse)
                .aircraft(aircraftResponse)
                .departureAirport(departureAirportResponse)
                .arrivalAirport(arrivalAirportResponse)
                .scheduledDeparture(flight.getScheduledDeparture())
                .scheduledArrival(flight.getScheduledArrival())
                .actualDeparture(flight.getActualDeparture())
                .actualArrival(flight.getActualArrival())
                .status(flight.getStatus())
                .departureTerminal(flight.getDepartureTerminal())
                .departureGate(flight.getDepartureGate())
                .arrivalTerminal(flight.getArrivalTerminal())
                .arrivalGate(flight.getArrivalGate())
                .durationMinutes(flight.getDurationMinutes())
                .distanceKm(flight.getDistanceKm())
                .delayMinutes(flight.getDelayMinutes())
                .disruptionReason(flight.getDisruptionReason())
                .bookable(flight.isBookable())
                .createdAt(flight.getCreatedAt())
                .updatedAt(flight.getUpdatedAt())
                .build();
    }

    public static Flight updateEntity(
            Flight flight,
            FlightRequest request
    ) {

        if (flight == null || request == null) {
            return flight;
        }

        if (request.getFlightNumber() != null) {
            flight.setFlightNumber(request.getFlightNumber());
        }

        if (request.getAirlineId() != null) {
            flight.setAirlineId(request.getAirlineId());
        }

        if (request.getAircraftId() != null) {
            flight.setAircraftId(request.getAircraftId());
        }

        if (request.getDepartureAirportId() != null) {
            flight.setDepartureAirportId(
                    request.getDepartureAirportId()
            );
        }

        if (request.getArrivalAirportId() != null) {
            flight.setArrivalAirportId(
                    request.getArrivalAirportId()
            );
        }

        if (request.getScheduledDeparture() != null) {
            flight.setScheduledDeparture(
                    request.getScheduledDeparture()
            );
        }

        if (request.getScheduledArrival() != null) {
            flight.setScheduledArrival(
                    request.getScheduledArrival()
            );
        }

        if (request.getDepartureTerminal() != null) {
            flight.setDepartureTerminal(
                    request.getDepartureTerminal()
            );
        }

        if (request.getDepartureGate() != null) {
            flight.setDepartureGate(
                    request.getDepartureGate()
            );
        }

        if (request.getArrivalTerminal() != null) {
            flight.setArrivalTerminal(
                    request.getArrivalTerminal()
            );
        }

        if (request.getArrivalGate() != null) {
            flight.setArrivalGate(
                    request.getArrivalGate()
            );
        }

        if (request.getDistanceKm() != null) {
            flight.setDistanceKm(
                    request.getDistanceKm()
            );
        }
        /*
         * Recalculate duration whenever scheduled
         * departure or arrival changes.
         */
        if (flight.getScheduledDeparture() != null
                && flight.getScheduledArrival() != null) {

            flight.setDurationMinutes(
                    calculateDuration(
                            flight.getScheduledDeparture(),
                            flight.getScheduledArrival()
                    )
            );
        }

        return flight;
    }

    private static Integer calculateDuration(
            java.time.Instant departure,
            java.time.Instant arrival
    ){
        if (departure == null || arrival == null) {
            return null;
        }

        return (int) Duration.between(
                departure,
                arrival
        ).toMinutes();
    }

}
