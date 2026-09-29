package com.airline.repository;

import com.airline.model.FlightInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FlightInstanceRepository extends JpaRepository<FlightInstance, Long> {

    @Query("""
            SELECT fi FROM FlightInstance fi
            WHERE fi.airlineId = :airlineId
                AND (:departureAirportId IS NULL 
                   OR fi.departureAirportId = :departureAirportId)
                AND (:arrivalAirportId IS NULL 
                   OR fi.arrivalAirportId = :arrivalAirportId)
                AND (:flight IS NULL 
                   OR fi.flight = :flight)
                AND (:onDate IS NULL 
                   OR (fi.departureDateTime >= :onDate 
                   AND fi.departureDateTime < :nextDate
                ))  
            """)
    Page<FlightInstance> findByAirlineId(
            @Param("airlineId") Long airlineId,
            @Param("departureAirportId") Long departureAirportId,
            @Param("arrivalAirportId") Long arrivalAirportId,
            @Param("flightId") Long flightId,
            @Param("onDate") LocalDateTime onDate,
            @Param("nextDate") LocalDateTime nextDate,
            Pageable pageable);

    Optional<FlightInstance> findByAirlineIdAndId(
            Long airlineId,
            Long id
    );

    Page<FlightInstance> findByScheduleId(
            Long scheduleId,
            Pageable pageable
    );

    Page<FlightInstance>  findByFlightId(
            Long flightId,
            Pageable pageable
    );


    Page<FlightInstance> findByFlight_Id(
            Long flightId,
            Pageable pageable
    );

    boolean existsByFlight_IdAndDepartureDateTime(
            Long flightId,
            LocalDateTime departureDateTime
    );

    boolean existsByFlight_IdAndDepartureDateTimeAndIdNot(
            Long flightId,
            LocalDateTime departureDateTime,
            Long id
    );

    boolean existsByFlight_Id(Long flightId);

    Page<FlightInstance> findByStatus(
            com.airline.enums.FlightStatus status,
            Pageable pageable
    );

}
