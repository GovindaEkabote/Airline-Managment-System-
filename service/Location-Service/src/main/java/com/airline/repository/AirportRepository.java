package com.airline.repository;

import com.airline.model.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    boolean existsByIataCodeIgnoreCase(String iataCode);

    boolean existsByIcaoCodeIgnoreCase(String icaoCode);

    boolean existsByIataCodeIgnoreCaseAndIdNot(
            String iataCode,
            Long id
    );

    boolean existsByIcaoCodeIgnoreCaseAndIdNot(
            String icaoCode,
            Long id
    );

    Optional<Airport> findByIataCodeIgnoreCaseAndActiveTrue(
            String iataCode
    );

    Optional<Airport> findByIcaoCodeIgnoreCaseAndActiveTrue(
            String icaoCode
    );

    Page<Airport> findByActiveTrue(Pageable pageable);

    Page<Airport> findByActiveFalse(Pageable pageable);

    Page<Airport> findByCityIdAndActiveTrue(
            Long cityId,
            Pageable pageable
    );

    @Query("""
            SELECT a FROM Airport a
            WHERE a.active = true
            AND (
                LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(a.iataCode) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(a.icaoCode) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(a.city.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            )
            """)
    Page<Airport> searchAirports(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}