package com.airline.repository;

import com.airline.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Flight findByFlightNumber(String flightNumber);

    Page<Flight> findByAirlineId(Pageable pageable, Long airlineId);

    List<Flight> findByDepartureAirportId(Long departureAirportId);

    List<Flight> findByArrivalAirportId(Long arrivalAirportId);
}
