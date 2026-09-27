package com.airline.repository;

import com.airline.enums.AircraftTypeStatus;
import com.airline.model.AircraftType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AircraftTypeRepository extends JpaRepository<AircraftType, Long> {

    Optional<AircraftType> findByIcaoTypeCode(String icaoTypeCode);
    Optional<AircraftType> findByIataTypeCode(String iataTypeCode);
    List<AircraftType> findByManufacturerIgnoreCase(String manufacturer);
    List<AircraftType> findByStatus(AircraftTypeStatus status);

    Page<AircraftType> findByStatus(
            AircraftTypeStatus status,
            Pageable pageable
    );

    boolean existsByIcaoTypeCode(String icaoTypeCode);

    boolean existsByIataTypeCode(String iataTypeCode);

}
