package com.airline.repository;

import com.airline.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findByAirlineId(Long airlineId);

    List<Meal> findByAncillary_Id(Long ancillaryId);

    List<Meal> findByAirlineIdAndAvailableTrueAndActiveTrue(
            Long airlineId
    );

}
