package com.airline.repository;

import com.airline.enums.AncillaryType;
import com.airline.model.FlightCabinAncillary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightCabinAncillaryRepository extends JpaRepository<FlightCabinAncillary, Long> {

    List<FlightCabinAncillary> findByFlightIdAndCabinClassId(
            Long flightId,
            Long cabinClassId
            );

    List<FlightCabinAncillary> findByFlightIdAndCabinClassIdAndAncillary_AncillaryType(
            Long flightId,
            Long cabinClassId,
            AncillaryType ancillaryType
    );

}
