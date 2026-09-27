package com.airline.service.impl;

import com.airline.enums.AircraftTypeStatus;
import com.airline.enums.AirlineStatus;
import com.airline.exception.DuplicateResourceException;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.AircraftMapper;
import com.airline.model.Aircraft;
import com.airline.playload.request.AircraftRequest;
import com.airline.playload.response.AircraftResponse;
import com.airline.playload.response.AircraftTypeResponse;
import com.airline.playload.response.AirlineResponse;
import com.airline.enums.AircraftStatus;
import com.airline.repository.AircraftRepository;
import com.airline.service.AircraftService;
import com.airline.service.AircraftTypeService;
import com.airline.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AircraftTypeService aircraftTypeService;
    private final AirlineService airlineService;

    // =========================================================
    // CREATE
    // =========================================================

    @Override
    @Transactional
    public AircraftResponse saveAircraft(AircraftRequest request) {

        validateUniqueFields(request);

        // Validate aircraft type
        AircraftTypeResponse aircraftType =
                aircraftTypeService.getAircraftTypeById(
                        request.getAircraftTypeId()
                );

        if (aircraftType.getStatus() != AircraftTypeStatus.ACTIVE) {
            throw new IllegalStateException(
                    "Aircraft type is not active: "
                            + request.getAircraftTypeId()
            );
        }

        // Validate airline
        AirlineResponse airline =
                airlineService.getAirlineById(
                        request.getAirlineId()
                );

        if (airline.getStatus() != AirlineStatus.ACTIVE) {
            throw new IllegalStateException(
                    "Airline is not active: "
                            + request.getAirlineId()
            );
        }

        Aircraft aircraft = AircraftMapper.toEntity(request);

        if (aircraft.getStatus() == null) {
            aircraft.setStatus(AircraftStatus.ACTIVE);
        }

        if (aircraft.getAvailable() == null) {
            aircraft.setAvailable(true);
        }

        if (aircraft.getOperational() == null) {
            aircraft.setOperational(true);
        }

        Aircraft savedAircraft =
                aircraftRepository.save(aircraft);

        return toEnrichedResponse(
                savedAircraft,
                aircraftType,
                airline
        );
    }

    // =========================================================
    // GET BY ID
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public AircraftResponse getAircraft(Long id) {

        Aircraft aircraft = findAircraftById(id);

        AircraftTypeResponse aircraftType =
                aircraftTypeService.getAircraftTypeById(
                        aircraft.getAircraftTypeId()
                );

        AirlineResponse airline =
                airlineService.getAirlineById(
                        aircraft.getAirlineId()
                );

        return toEnrichedResponse(
                aircraft,
                aircraftType,
                airline
        );
    }

    // =========================================================
    // GET ALL
    // =========================================================

    @Override
    public Page<AircraftResponse> getAllAircrafts(Pageable pageable) {

        return aircraftRepository.findAll(pageable)
                .map(AircraftMapper::toResponse);
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @Override
    @Transactional
    public AircraftResponse updateAircraft(
            AircraftRequest request,
            Long id) {

        // 1. Find existing aircraft
        Aircraft aircraft = findAircraftById(id);

        // 2. Check duplicate unique fields
        validateUniqueFieldsForUpdate(request, id);

        // 3. Validate aircraft type
        AircraftTypeResponse aircraftType =
                aircraftTypeService.getAircraftTypeById(
                        request.getAircraftTypeId()
                );

        // 4. Validate airline
        AirlineResponse airline =
                airlineService.getAirlineById(
                        request.getAirlineId()
                );

        // 5. Validate seat configuration
        validateSeatConfiguration(request);

        // 6. Validate maintenance date
        validateMaintenanceDate(request);

        // 7. Update entity
        AircraftMapper.updateEntity(
                aircraft,
                request
        );

        // 8. Save
        Aircraft updatedAircraft =
                aircraftRepository.save(aircraft);

        // 9. Return enriched response
        return toEnrichedResponse(
                updatedAircraft,
                aircraftType,
                airline
        );
    }

    // =========================================================
    // DELETE
    // =========================================================

    @Override
    @Transactional
    public void deleteAircraft(Long id) {

        Aircraft aircraft = findAircraftById(id);

        aircraftRepository.delete(aircraft);
    }

    // =========================================================
    // FIND BY REGISTRATION NUMBER
    // =========================================================

    @Override
    public AircraftResponse getAircraftByRegistrationNumber(
            String registrationNumber) {

        Aircraft aircraft = aircraftRepository
                .findByRegistrationNumber(registrationNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "registrationNumber",
                                "registrationNumber",
                                registrationNumber
                        )
                );

        return toEnrichedResponse(aircraft);
    }

    // =========================================================
    // FIND BY SERIAL NUMBER
    // =========================================================

    @Override
    public AircraftResponse getAircraftBySerialNumber(
            String serialNumber) {

        Aircraft aircraft = aircraftRepository
                .findBySerialNumber(serialNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "serialNumber",
                                "serialNumber",
                                serialNumber
                        )
                );

        return toEnrichedResponse(aircraft);
    }

    // =========================================================
    // FIND BY ICAO24
    // =========================================================

    @Override
    public AircraftResponse getAircraftByIcao24(String icao24) {

        Aircraft aircraft = aircraftRepository
                .findByIcao24(icao24)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "icao24",
                                "icao24",
                                icao24
                        )
                );

        return toEnrichedResponse(aircraft);
    }

    // =========================================================
    // GET BY AIRLINE
    // =========================================================

    @Override
    public Page<AircraftResponse> getAircraftsByAirlineId(
            Long airlineId,
            Pageable pageable) {

        // Validate airline exists
        airlineService.getAirlineById(airlineId);

        return aircraftRepository
                .findByAirlineId(airlineId, pageable)
                .map(AircraftMapper::toResponse);
    }

    // =========================================================
    // GET BY AIRCRAFT TYPE
    // =========================================================

    @Override
    public Page<AircraftResponse> getAircraftsByAircraftTypeId(
            Long aircraftTypeId,
            Pageable pageable) {

        // Validate aircraft type exists
        aircraftTypeService.getAircraftTypeById(aircraftTypeId);

        return aircraftRepository
                .findByAircraftTypeId(aircraftTypeId, pageable)
                .map(AircraftMapper::toResponse);
    }

    // =========================================================
    // GET BY STATUS
    // =========================================================

    @Override
    public Page<AircraftResponse> getAircraftsByStatus(
            AircraftStatus status,
            Pageable pageable) {

        return aircraftRepository
                .findByStatus(status, pageable)
                .map(AircraftMapper::toResponse);
    }

    // =========================================================
    // GET BY AIRLINE + STATUS
    // =========================================================

    @Override
    public List<AircraftResponse> getAircraftsByAirlineIdAndStatus(
            Long airlineId,
            AircraftStatus status) {

        return aircraftRepository
                .findByAirlineIdAndStatus(airlineId, status)
                .stream()
                .map(AircraftMapper::toResponse)
                .toList();
    }

    // =========================================================
    // GET AVAILABLE AIRCRAFT
    // =========================================================

    @Override
    public List<AircraftResponse> getAvailableAircrafts() {

        return aircraftRepository
                .findByAvailableTrue()
                .stream()
                .map(AircraftMapper::toResponse)
                .toList();
    }

    // =========================================================
    // GET AVAILABLE AIRCRAFT BY AIRLINE
    // =========================================================

    @Override
    public List<AircraftResponse> getAvailableAircraftsByAirlineId(
            Long airlineId) {

        return aircraftRepository
                .findByAirlineIdAndAvailableTrue(airlineId)
                .stream()
                .map(AircraftMapper::toResponse)
                .toList();
    }

    // =========================================================
    // CHANGE STATUS
    // =========================================================

    @Override
    @Transactional
    public AircraftResponse changeStatus(
            Long id,
            AircraftStatus status) {

        Aircraft aircraft = findAircraftById(id);

        aircraft.setStatus(status);

        // Keep state consistent
        if (status == AircraftStatus.MAINTENANCE ||
                status == AircraftStatus.GROUNDED ||
                status == AircraftStatus.RETIRED) {

            aircraft.setAvailable(false);
        }

        Aircraft updatedAircraft =
                aircraftRepository.save(aircraft);

        return toEnrichedResponse(updatedAircraft);
    }

    // =========================================================
    // CHANGE AVAILABILITY
    // =========================================================

    @Override
    @Transactional
    public AircraftResponse changeAvailability(
            Long id,
            Boolean available) {

        Aircraft aircraft = findAircraftById(id);

        if (available == null) {
            throw new IllegalArgumentException(
                    "Availability cannot be null"
            );
        }

        if (available &&
                (aircraft.getStatus() == AircraftStatus.MAINTENANCE ||
                        aircraft.getStatus() == AircraftStatus.GROUNDED ||
                        aircraft.getStatus() == AircraftStatus.RETIRED)) {

            throw new IllegalStateException(
                    "Aircraft cannot be made available while status is "
                            + aircraft.getStatus()
            );
        }

        aircraft.setAvailable(available);

        Aircraft updatedAircraft =
                aircraftRepository.save(aircraft);

        return toEnrichedResponse(updatedAircraft);
    }

    // =========================================================
    // DROPDOWN
    // =========================================================

    @Override
    public List<AircraftResponse> getAircraftDropdown() {

        return aircraftRepository
                .findByAvailableTrue()
                .stream()
                .map(AircraftMapper::toResponse)
                .toList();
    }

    // =========================================================
    // PRIVATE METHODS
    // =========================================================

    private Aircraft findAircraftById(Long id) {

        return aircraftRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Aircraft",
                                "id",
                                id
                        )
                );
    }

    private void validateUniqueFields(
            AircraftRequest request) {

        if (aircraftRepository
                .existsByRegistrationNumber(
                        request.getRegistrationNumber())) {

            throw new DuplicateResourceException(
                    "Aircraft already exists with registration number: "
                            + request.getRegistrationNumber()
            );
        }

        if (aircraftRepository
                .existsBySerialNumber(
                        request.getSerialNumber())) {

            throw new DuplicateResourceException(
                    "Aircraft already exists with serial number: "
                            + request.getSerialNumber()
            );
        }

        if (request.getIcao24() != null &&
                aircraftRepository.existsByIcao24(
                        request.getIcao24())) {

            throw new DuplicateResourceException(
                    "Aircraft already exists with ICAO24: "
                            + request.getIcao24()
            );
        }
    }

    private void validateUniqueFieldsForUpdate(
            AircraftRequest request,
            Long id) {

        if (aircraftRepository
                .existsByRegistrationNumberAndIdNot(
                        request.getRegistrationNumber(),
                        id)) {

            throw new DuplicateResourceException(
                    "Aircraft already exists with registration number: "
                            + request.getRegistrationNumber()
            );
        }

        if (aircraftRepository
                .existsBySerialNumberAndIdNot(
                        request.getSerialNumber(),
                        id)) {

            throw new DuplicateResourceException(
                    "Aircraft already exists with serial number: "
                            + request.getSerialNumber()
            );
        }

        if (request.getIcao24() != null &&
                aircraftRepository.existsByIcao24AndIdNot(
                        request.getIcao24(),
                        id)) {

            throw new DuplicateResourceException(
                    "Aircraft already exists with ICAO24: "
                            + request.getIcao24()
            );
        }
    }

    private AircraftResponse toEnrichedResponse(
            Aircraft aircraft) {

        AircraftResponse response =
                AircraftMapper.toResponse(aircraft);

        AircraftTypeResponse aircraftType =
                aircraftTypeService.getAircraftTypeById(
                        aircraft.getAircraftTypeId()
                );

        AirlineResponse airline =
                airlineService.getAirlineById(
                        aircraft.getAirlineId()
                );

        response.setAircraftType(aircraftType);
        response.setAirline(airline);

        response.setRequiresMaintenance(
                requiresMaintenance(aircraft)
        );

        return response;
    }

    private AircraftResponse toEnrichedResponse(
            Aircraft aircraft,
            AircraftTypeResponse aircraftType,
            AirlineResponse airline) {

        AircraftResponse response =
                AircraftMapper.toResponse(aircraft);

        response.setAircraftType(aircraftType);
        response.setAirline(airline);

        response.setRequiresMaintenance(
                requiresMaintenance(aircraft)
        );

        return response;
    }

    private boolean requiresMaintenance(Aircraft aircraft) {

        return aircraft.getNextMaintenanceDate() != null
                && !aircraft.getNextMaintenanceDate()
                .isAfter(LocalDate.now());
    }

    private void validateSeatConfiguration(AircraftRequest request) {

        int totalSeats = request.getTotalSeats() != null
                ? request.getTotalSeats()
                : 0;

        int economySeats = request.getEconomySeats() != null
                ? request.getEconomySeats()
                : 0;

        int premiumEconomySeats = request.getPremiumEconomySeats() != null
                ? request.getPremiumEconomySeats()
                : 0;

        int businessSeats = request.getBusinessSeats() != null
                ? request.getBusinessSeats()
                : 0;

        int firstClassSeats = request.getFirstClassSeats() != null
                ? request.getFirstClassSeats()
                : 0;

        int configuredSeats =
                economySeats
                        + premiumEconomySeats
                        + businessSeats
                        + firstClassSeats;

        if (configuredSeats != totalSeats) {
            throw new IllegalArgumentException(
                    "Seat configuration does not match total seats. " +
                            "Total seats: " + totalSeats +
                            ", configured seats: " + configuredSeats
            );
        }
    }

    private void validateMaintenanceDate(AircraftRequest request) {

        if (request.getNextMaintenanceDate() != null
                && request.getDeliveryDate() != null
                && request.getNextMaintenanceDate()
                .isBefore(request.getDeliveryDate())) {

            throw new IllegalArgumentException(
                    "Next maintenance date cannot be before delivery date"
            );
        }
    }
}