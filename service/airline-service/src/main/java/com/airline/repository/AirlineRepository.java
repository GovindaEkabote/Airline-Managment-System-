package com.airline.repository;

import com.airline.enums.AirlineStatus;
import com.airline.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

    Optional<Airline> findByOwnerId(Long userId);
    Optional<Airline> findByIataCode(String iataCode);

    Optional<Airline> findByIcaoCode(String icaoCode);

    boolean existsByIataCode(String iataCode);

    boolean existsByIcaoCode(String icaoCode);

    List<Airline> findByStatus(AirlineStatus status);

}
