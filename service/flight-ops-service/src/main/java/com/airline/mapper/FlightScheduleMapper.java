package com.airline.mapper;

import com.airline.model.Flight;
import com.airline.model.FlightSchedule;
import com.airline.playload.request.FlightScheduleRequest;
import com.airline.playload.response.AirportResponse;
import com.airline.playload.response.FlightResponse;
import com.airline.playload.response.FlightScheduleResponse;

public class FlightScheduleMapper {

    public static FlightSchedule toEntity(FlightScheduleRequest request,
                                          Flight flight){
        if (request == null || flight == null ){
            return null;
        }

        return FlightSchedule.builder()
                .flight(flight)
                .airlineId(flight.getAirlineId())
                .departureAirportId(flight.getDepartureAirportId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .operatingDays(request.getOperatingDays())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();
    }

    public static FlightScheduleResponse toResponse(
            FlightSchedule schedule,
            AirportResponse departureAirport,
            AirportResponse arrivalAirport

    ){
        if (schedule == null || departureAirport == null || arrivalAirport == null){
            return null;
        }

        return FlightScheduleResponse.builder()
                .id(schedule.getId())
                .flightId(schedule.getFlight() != null ? schedule.getFlight().getId() : null)
                .flightNumber(schedule.getFlight() != null ? schedule.getFlight().getFlightNumber() : null)
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureTime(schedule.getDepartureTime())
                .arrivalTime(schedule.getArrivalTime())
                .startDate(schedule.getStartDate())
                .endDate(schedule.getEndDate())
                .operatingDays(schedule.getOperatingDays())
                .isActive(schedule.getIsActive())
                .build();
    }

    public static FlightSchedule updateEntity(
            FlightSchedule schedule,
            FlightScheduleRequest request
    ){
        if (schedule == null || request == null){
            return null;
        }

        if (schedule.getDepartureTime() != null){
            schedule.setDepartureTime(request.getDepartureTime());
        }

        if (schedule.getArrivalTime() != null){
            schedule.setArrivalTime(request.getArrivalTime());
        }

        if (schedule.getStartDate() != null){
            schedule.setStartDate(request.getStartDate());
        }

        if (schedule.getEndDate() != null){
            schedule.setEndDate(request.getEndDate());
        }

        if (schedule.getOperatingDays() != null){
            schedule.setOperatingDays(request.getOperatingDays());
        }

        if (schedule.getIsActive() != null){
            schedule.setIsActive(request.getIsActive());
        }
        return schedule;
    }


}
