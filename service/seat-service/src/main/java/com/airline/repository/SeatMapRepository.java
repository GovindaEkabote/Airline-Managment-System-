package com.airline.repository;

import com.airline.model.SeatMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SeatMapRepository extends JpaRepository<SeatMap, Long> {

    // One seat map per cabin class (unique FK)
    Optional<SeatMap> findByCabinClassId(Long cabinClassId);

    boolean existsByAirlineIdAndCabinClassId(Long airlinId, Long cabinClassId);

    boolean existsByCabinClassId(Long cabinClassId);

    Page<SeatMap> findByAirlineId(Long airlineId, Pageable pageable);

    // Loads map + seats in ONE query (avoids N+1 when you need the full layout)
    @Query("select distinct sm from SeatMap sm left join fetch sm.seats where sm.id = :id")
    Optional<SeatMap> findByIdWithSeats(@Param("id") Long id);
}
