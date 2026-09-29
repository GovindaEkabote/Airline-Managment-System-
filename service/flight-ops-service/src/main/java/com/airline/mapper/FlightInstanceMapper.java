package com.airline.mapper;

import com.airline.model.Flight;
import com.airline.model.FlightInstance;
import com.airline.playload.request.FlightInstanceRequest;
import com.airline.playload.response.*;

public class FlightInstanceMapper {

    public static FlightInstance toEntity(FlightInstanceRequest request, Flight flight) {

        if (request == null){
            return null;
        }

        return FlightInstance.builder()
                .flight(flight)
                .airlineId(flight.getAirlineId())
                .departureAirportId(
                        request.getDepartureAirportId() != null
                                ? request.getDepartureAirportId() : null
                )
                .arrivalAirportId(
                        request.getArrivalAirportId() != null
                                ? request.getArrivalAirportId() : null
                )
                .scheduleId(request.getScheduleId())
                .departureDateTime(request.getDepartureDateTime())
                .arrivalDateTime(request.getArrivalDateTime())
                .minAdvanceBookingDays(request.getMinAdvanceBookingDays())
                .maxAdvanceBookingDays(request.getMaxAdvanceBookingDays())
                .isActive(request.getIsActive() != null
                        ? request.getIsActive() : true)
                .status(request.getStatus())

                .build();
    }

    public static FlightInstanceResponse toResponse(
            FlightInstance flightInstance,
            FlightResponse flightResponse,
            AircraftResponse aircraftResponse,
            AirlineResponse airlineResponse,
            AirportResponse departureAirportResponse,
            AirportResponse arrivalAirportResponse) {

        if (flightInstance == null) {
            return null;
        }


        return FlightInstanceResponse.builder()
                .id(flightInstance.getId())

                .airline(airlineResponse)
                .flight(flightResponse)
                .departureAirport(
                        departureAirportResponse
                )
                .aircraft(aircraftResponse)
                .arrivalAirport(
                        arrivalAirportResponse
                )
                .scheduleId(
                        flightInstance.getScheduleId()
                )
                .departureDateTime(
                        flightInstance.getDepartureDateTime()
                )
                .arrivalDateTime(
                        flightInstance.getArrivalDateTime()
                )
                .formattedDuration(
                        flightInstance.getFormatedDuration()
                )

                .totalSeats(
                        flightInstance.getTotalSeats()
                )

                .availableSeats(
                        flightInstance.getAvailableSeats()
                )

                .status(
                        flightInstance.getStatus()
                )

                .minAdvanceBookingDays(
                        flightInstance.getMinAdvanceBookingDays()
                )

                .maxAdvanceBookingDays(
                        flightInstance.getMaxAdvanceBookingDays()
                )

                .isActive(
                        flightInstance.getIsActive()
                )

                .build();

    }

    public static FlightInstance updateEntity(
            FlightInstance flightInstance,
            FlightInstanceRequest request) {

        if (flightInstance == null || request == null) {
            return flightInstance;
        }

        if (request.getDepartureAirportId() != null) {
            flightInstance.setDepartureAirportId(
                    request.getDepartureAirportId()
            );
        }

        if (request.getArrivalAirportId() != null) {
            flightInstance.setArrivalAirportId(
                    request.getArrivalAirportId()
            );
        }

        if (request.getScheduleId() != null) {
            flightInstance.setScheduleId(
                    request.getScheduleId()
            );
        }

        if (request.getDepartureDateTime() != null) {
            flightInstance.setDepartureDateTime(
                    request.getDepartureDateTime()
            );
        }

        if (request.getArrivalDateTime() != null) {
            flightInstance.setArrivalDateTime(
                    request.getArrivalDateTime()
            );
        }

        if (request.getTotalSeats() != null) {
            flightInstance.setTotalSeats(
                    request.getTotalSeats()
            );
        }

        if (request.getStatus() != null) {
            flightInstance.setStatus(
                    request.getStatus()
            );
        }
        if (request.getAvailableSeats() != null){
            flightInstance.setAvailableSeats(
                    request.getAvailableSeats()
            );
        }

        if(request.getStatus() != null){
            flightInstance.setStatus(
                    request.getStatus()
            );
        }

        if (request.getMinAdvanceBookingDays() != null) {
            flightInstance.setMinAdvanceBookingDays(
                    request.getMinAdvanceBookingDays()
            );
        }

        if (request.getMaxAdvanceBookingDays() != null) {
            flightInstance.setMaxAdvanceBookingDays(
                    request.getMaxAdvanceBookingDays()
            );
        }

        if (request.getIsActive() != null) {
            flightInstance.setIsActive(
                    request.getIsActive()
            );
        }

        return flightInstance;
    }
}
