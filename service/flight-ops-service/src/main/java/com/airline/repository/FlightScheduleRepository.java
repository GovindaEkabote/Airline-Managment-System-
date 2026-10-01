package com.airline.repository;

import com.airline.model.FlightSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long> {

    Page<FlightSchedule> findByFlightId(
            Long flightId,
            Pageable pageable);

    List<FlightSchedule> findByAirlineId(
            Long airlineId);

    Page<FlightSchedule> findByDepartureAirportId(
            Long departureAirportId,
            Pageable pageable
    );

    Page<FlightSchedule> findByArrivalAirportId(
            Long arrivalAirportId,
            Pageable pageable
    );

    Page<FlightSchedule> findByOperatingDaysContaining(
            DayOfWeek dayOfWeek,
            Pageable pageable
    );


}
