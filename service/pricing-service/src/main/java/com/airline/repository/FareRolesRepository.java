package com.airline.repository;

import com.airline.model.FareRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FareRolesRepository extends JpaRepository<FareRoles, Long> {
    FareRoles findByFareId(Long fareId);
    List<FareRoles> findByAirlineId(Long airlineId);
    boolean existsByFareId(Long fareId);


}
