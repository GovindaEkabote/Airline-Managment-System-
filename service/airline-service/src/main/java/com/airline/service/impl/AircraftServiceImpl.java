package com.airline.service.impl;

import com.airline.enums.AircraftStatus;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.AircraftMapper;
import com.airline.model.Aircraft;
import com.airline.playload.request.AircraftRequest;
import com.airline.playload.response.AircraftResponse;
import com.airline.repository.AircraftRepository;
import com.airline.service.AircraftService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;

    @Override
    public AircraftResponse saveAircraft(AircraftRequest request) {

        Aircraft aircraft = AircraftMapper.toEntity(request);

        Aircraft savedAircraft = aircraftRepository.save(aircraft);

        return AircraftMapper.toResponse(savedAircraft);
    }

    @Override
    public AircraftResponse getAircraft(Long id) {

        Aircraft aircraft = findAircraftById(id);

        return AircraftMapper.toResponse(aircraft);
    }

    @Override
    public Page<AircraftResponse> getAllAircrafts(Pageable pageable) {

        return aircraftRepository
                .findAll(pageable)
                .map(AircraftMapper::toResponse);
    }

    @Override
    public AircraftResponse updateAircraft(
            AircraftRequest request,
            Long id
    ) {

        Aircraft aircraft = findAircraftById(id);

        AircraftMapper.updateEntity(aircraft, request);

        Aircraft updatedAircraft = aircraftRepository.save(aircraft);

        return AircraftMapper.toResponse(updatedAircraft);
    }

    @Override
    public void deleteAircraft(Long id) {

        Aircraft aircraft = findAircraftById(id);

        aircraftRepository.delete(aircraft);
    }

    @Override
    public AircraftResponse getAircraftByRegistrationNumber(
            String registrationNumber
    ) {

        Aircraft aircraft = aircraftRepository
                .findByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "registrationNumber",
                        "registrationNumber",
                        registrationNumber
                ));

        return AircraftMapper.toResponse(aircraft);
    }

    @Override
    public AircraftResponse getAircraftBySerialNumber(
            String serialNumber
    ) {

        Aircraft aircraft = aircraftRepository
                .findBySerialNumber(serialNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "serialNumber",
                        "serialNumber",
                        serialNumber
                ));

        return AircraftMapper.toResponse(aircraft);
    }

    @Override
    public AircraftResponse getAircraftByIcao24(String icao24) {

        Aircraft aircraft = aircraftRepository
                .findByIcao24(icao24)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "icao24",
                        "icao24",
                        icao24
                ));

        return AircraftMapper.toResponse(aircraft);
    }

    @Override
    public Page<AircraftResponse> getAircraftsByAirlineId(
            Long airlineId,
            Pageable pageable
    ) {

        return aircraftRepository
                .findByAirlineId(airlineId, pageable)
                .map(AircraftMapper::toResponse);
    }

    @Override
    public Page<AircraftResponse> getAircraftsByAircraftTypeId(
            Long aircraftTypeId,
            Pageable pageable
    ) {

        return aircraftRepository
                .findByAircraftTypeId(aircraftTypeId, pageable)
                .map(AircraftMapper::toResponse);
    }

    @Override
    public Page<AircraftResponse> getAircraftsByStatus(
            AircraftStatus status,
            Pageable pageable
    ) {

        return aircraftRepository
                .findByStatus(status, pageable)
                .map(AircraftMapper::toResponse);
    }

    @Override
    public List<AircraftResponse> getAircraftsByAirlineIdAndStatus(
            Long airlineId,
            AircraftStatus status
    ) {

        return aircraftRepository
                .findByAirlineIdAndStatus(airlineId, status)
                .stream()
                .map(AircraftMapper::toResponse)
                .toList();
    }

    @Override
    public List<AircraftResponse> getAvailableAircrafts() {

        return aircraftRepository
                .findByAvailableTrue()
                .stream()
                .map(AircraftMapper::toResponse)
                .toList();
    }

    @Override
    public List<AircraftResponse> getAvailableAircraftsByAirlineId(
            Long airlineId
    ) {

        return aircraftRepository
                .findByAirlineIdAndAvailableTrue(airlineId)
                .stream()
                .map(AircraftMapper::toResponse)
                .toList();
    }

    @Override
    public AircraftResponse changeStatus(
            Long id,
            AircraftStatus status
    ) {

        Aircraft aircraft = findAircraftById(id);

        aircraft.setStatus(status);

        Aircraft updatedAircraft = aircraftRepository.save(aircraft);

        return AircraftMapper.toResponse(updatedAircraft);
    }

    @Override
    public AircraftResponse changeAvailability(
            Long id,
            Boolean available
    ) {

        Aircraft aircraft = findAircraftById(id);

        aircraft.setAvailable(available);

        Aircraft updatedAircraft = aircraftRepository.save(aircraft);

        return AircraftMapper.toResponse(updatedAircraft);
    }

    @Override
    public List<AircraftResponse> getAircraftDropdown() {

        return aircraftRepository
                .findByAvailableTrue()
                .stream()
                .map(AircraftMapper::toResponse)
                .toList();
    }

    private Aircraft findAircraftById(Long id) {

        return aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aircraft",
                        "id",
                        id
                ));
    }
}