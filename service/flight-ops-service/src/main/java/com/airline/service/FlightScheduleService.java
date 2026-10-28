package com.airline.service;

import com.airline.playload.request.FlightScheduleRequest;
import com.airline.playload.response.FlightScheduleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.util.List;

public interface FlightScheduleService {
    FlightScheduleResponse createFlightSchedule(
            Long userId,
            FlightScheduleRequest flightSchedule);

    FlightScheduleResponse getFlightScheduleById(Long id);

        List<FlightScheduleResponse> getFlightSchedulesByAirline(Long userId);

    FlightScheduleResponse updateFlightSchedule(
            Long scheduleId,
            FlightScheduleRequest flightSchedule);

    void deleteFlightSchedule(Long id);

    Page<FlightScheduleResponse> getAllFlightSchedules(
            Pageable pageable
    );

    Page<FlightScheduleResponse> getSchedulesByFlight(
            Long flightId,
            Pageable pageable
    );

    Page<FlightScheduleResponse> getSchedulesByDepartureAirport(
            Long airportId,
            Pageable pageable
    );

    Page<FlightScheduleResponse> getSchedulesByArrivalAirport(
            Long airportId,
            Pageable pageable
    );

    Page<FlightScheduleResponse> getSchedulesByDay(
            DayOfWeek dayOfWeek,
            Pageable pageable
    );
}
