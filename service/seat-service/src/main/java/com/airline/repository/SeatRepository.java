package com.airline.repository;

import com.airline.model.Seat;
import com.airline.model.SeatMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    boolean existsBySeatMapId( Long id);
    List<Seat> findBySeatMapId(Long id);
}
