package com.airline.repository;

import com.airline.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightNumber(String flightNumber);

    boolean existsByFlightNumber(String flightNumber);
    boolean existsByFlightNumberAndIdNot(String flightNumber, Long id);

    @Query("""
            SELECT f FROM Flight f 
            WHERE f.airlineId = :airlineId 
            AND( :departureAirportId is null or f.departureAirportId = :departureAirportId )
            AND( :arrivalAirportId is null or f.arrivalAirportId = :arrivalAirportId)
            """)
    Page<Flight> findByAirlineId(@Param("airlineId") Long airlineId,
                                 @Param("departureAirportId") Long departureAirportId,
                                 @Param("arrivalAirportId") Long arrivalAirportId,
                                 Pageable pageable);

    Optional<Flight> findByAirlineIdAndId(Long airlineId, Long id);
}
