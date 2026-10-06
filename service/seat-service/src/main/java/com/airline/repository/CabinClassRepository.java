package com.airline.repository;

import com.airline.model.CabinClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CabinClassRepository extends JpaRepository<CabinClass, Long> {

    // All cabins of an aircraft, in display order (First -> Business -> Economy)
    List<CabinClass> findByAircraftIdOrderByDisplayOrderAsc(Long aircraftId);

    // Only active cabins (used when creating flight instances)
    List<CabinClass> findByAircraftIdAndIsActiveTrueOrderByDisplayOrderAsc(Long aircraftId);


    // Duplicate check before create (matches unique constraint aircraft_id + code)
    boolean existsByAircraftIdAndCode(Long aircraftId, String code);

    // Duplicate check on update (ignore the row being updated)
    boolean existsByAircraftIdAndCodeAndIdNot(Long aircraftId, String code, Long id);
}
