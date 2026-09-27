package com.airline.repository;

import com.airline.enums.AircraftStatus;
import com.airline.model.Aircraft;
import com.airline.model.AircraftType;
import com.airline.playload.response.AircraftResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    Optional<Aircraft> findByRegistrationNumber(String registrationNumber);
    Optional<Aircraft> findBySerialNumber(String serialNumber);
    Optional<Aircraft> findByIcao24(String icao24);
    List<Aircraft> findByAirlineId(Long airlineId);
    Page<Aircraft> findByAirlineId(
            Long airlineId,
            Pageable pageable
    );
    List<Aircraft> findByAircraftTypeId(Long aircraftTypeId);
    Page<Aircraft> findByAircraftTypeId(
            Long aircraftTypeId,
            Pageable pageable
    );

    List<Aircraft> findByStatus(AircraftStatus status);

    Page<Aircraft> findByStatus(
            AircraftStatus status,
            Pageable pageable
    );

    List<Aircraft> findByAirlineIdAndStatus(
            Long airlineId,
            AircraftStatus status
    );

    Page<Aircraft> findByAirlineIdAndStatus(
            Long airlineId,
            AircraftStatus status,
            Pageable pageable
    );

    List<Aircraft> findByAvailableTrue();

    List<Aircraft> findByAirlineIdAndAvailableTrue(
            Long airlineId
    );

    boolean existsByRegistrationNumber(String registrationNumber);

    boolean existsBySerialNumber(String serialNumber);

    boolean existsByIcao24(String icao24);

    boolean existsByRegistrationNumberAndIdNot(
            String registrationNumber,
            Long id
    );

    boolean existsBySerialNumberAndIdNot(
            String serialNumber,
            Long id
    );

    boolean existsByIcao24AndIdNot(
            String icao24,
            Long id
    );
}
