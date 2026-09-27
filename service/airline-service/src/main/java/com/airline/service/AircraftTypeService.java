package com.airline.service;

import com.airline.enums.AircraftTypeStatus;
import com.airline.playload.request.AircraftTypeRequest;
import com.airline.playload.response.AircraftTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AircraftTypeService {

    // Create
    AircraftTypeResponse createAircraftType(
            AircraftTypeRequest request
    );

    // Get by ID
    AircraftTypeResponse getAircraftTypeById(
            Long id
    );

    // Get all with pagination
    Page<AircraftTypeResponse> getAllAircraftTypes(
            Pageable pageable
    );

    // Update
    AircraftTypeResponse updateAircraftType(
            AircraftTypeRequest request,
            Long id
    );

    // Delete
    void deleteAircraftType(
            Long id
    );

    // Find by ICAO aircraft type code
    AircraftTypeResponse getAircraftTypeByIcaoCode(
            String icaoTypeCode
    );

    // Find by IATA aircraft type code
    AircraftTypeResponse getAircraftTypeByIataCode(
            String iataTypeCode
    );

    // Find by manufacturer
    List<AircraftTypeResponse> getAircraftTypesByManufacturer(
            String manufacturer
    );

    // Get by status
    List<AircraftTypeResponse> getAircraftTypesByStatus(
            AircraftTypeStatus status
    );

    // Change status
    AircraftTypeResponse changeStatus(
            Long id,
            AircraftTypeStatus status
    );

    // Dropdown
    List<AircraftTypeResponse> getAircraftTypeDropdown();
}