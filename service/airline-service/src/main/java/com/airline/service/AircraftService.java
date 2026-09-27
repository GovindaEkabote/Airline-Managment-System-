package com.airline.service;

import com.airline.enums.AircraftStatus;
import com.airline.playload.request.AircraftRequest;
import com.airline.playload.response.AircraftResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AircraftService {

    public AircraftResponse saveAircraft(AircraftRequest aircraft);
    AircraftResponse getAircraft(Long id);
    Page<AircraftResponse> getAllAircrafts(
            Pageable pageable
    );
    AircraftResponse updateAircraft(AircraftRequest request, Long id);
    void deleteAircraft(
            Long id
    );

    // Find by registration / tail number
    AircraftResponse getAircraftByRegistrationNumber(
            String registrationNumber
    );

    // Find by manufacturer serial number
    AircraftResponse getAircraftBySerialNumber(
            String serialNumber
    );

    // Find by ICAO24
    AircraftResponse getAircraftByIcao24(
            String icao24
    );

    // Get aircraft belonging to an airline
    Page<AircraftResponse> getAircraftsByAirlineId(
            Long airlineId,
            Pageable pageable
    );

    // Get aircraft of a particular aircraft type
    Page<AircraftResponse> getAircraftsByAircraftTypeId(
            Long aircraftTypeId,
            Pageable pageable
    );

    // Get aircraft by status
    Page<AircraftResponse> getAircraftsByStatus(
            AircraftStatus status,
            Pageable pageable
    );

    // Get aircraft by airline and status
    List<AircraftResponse> getAircraftsByAirlineIdAndStatus(
            Long airlineId,
            AircraftStatus status
    );

    // Get currently available aircraft
    List<AircraftResponse> getAvailableAircrafts();

    // Get available aircraft of an airline
    List<AircraftResponse> getAvailableAircraftsByAirlineId(
            Long airlineId
    );

    // Change aircraft status
    AircraftResponse changeStatus(
            Long id,
            AircraftStatus status
    );

    // Change availability
    AircraftResponse changeAvailability(
            Long id,
            Boolean available
    );

    // Dropdown
    List<AircraftResponse> getAircraftDropdown();
}
