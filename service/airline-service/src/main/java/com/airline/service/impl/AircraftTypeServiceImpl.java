package com.airline.service.impl;

import com.airline.enums.AircraftTypeStatus;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.AircraftTypeMapper;
import com.airline.model.AircraftType;
import com.airline.playload.request.AircraftTypeRequest;
import com.airline.playload.response.AircraftTypeResponse;
import com.airline.repository.AircraftTypeRepository;
import com.airline.service.AircraftTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AircraftTypeServiceImpl implements AircraftTypeService {

    private final AircraftTypeRepository aircraftTypeRepository;

    @Override
    public AircraftTypeResponse createAircraftType(AircraftTypeRequest request) {
        AircraftType aircraftType = AircraftTypeMapper.toEntity(
                request
        );

        AircraftType savedAircraftType = aircraftTypeRepository.save(aircraftType);
        return AircraftTypeMapper.toResponse(savedAircraftType);
    }

    @Override
    public AircraftTypeResponse getAircraftTypeById(Long id) {
        AircraftType aircraftType = aircraftTypeRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "id",
                                "id",
                                id
                        )
                );
        return AircraftTypeMapper.toResponse(aircraftType);
    }

    @Override
    public Page<AircraftTypeResponse> getAllAircraftTypes(Pageable pageable) {
        return aircraftTypeRepository
                .findAll(pageable)
                .map(AircraftTypeMapper::toResponse);
    }

    @Override
    public AircraftTypeResponse updateAircraftType(
            AircraftTypeRequest request,
            Long id) {
        AircraftType aircraftType = aircraftTypeRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "id",
                                "id",
                                id
                        )
                );

        aircraftType = AircraftTypeMapper.updateEntity(aircraftType, request);
        return AircraftTypeMapper.toResponse(aircraftTypeRepository.save(aircraftType));
    }

    @Override
    public void deleteAircraftType(Long id) {
        AircraftType aircraftType = aircraftTypeRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "id",
                                "id",
                                id
                        )
                );
        aircraftTypeRepository.delete(aircraftType);
    }

    @Override
    public AircraftTypeResponse getAircraftTypeByIcaoCode(
            String icaoTypeCode) {
        AircraftType aircraftType = aircraftTypeRepository.findByIcaoTypeCode(icaoTypeCode)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "icaoTypeCode",
                                "icaoTypeCode",
                                icaoTypeCode
                        )
                );
        return AircraftTypeMapper.toResponse(aircraftType);
    }

    @Override
    public AircraftTypeResponse getAircraftTypeByIataCode(String iataTypeCode) {
        AircraftType aircraftType = aircraftTypeRepository.findByIataTypeCode(iataTypeCode)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "iataTypeCode",
                                "iataTypeCode",
                                iataTypeCode
                        )
                );
        return AircraftTypeMapper.toResponse(aircraftType);
    }

    @Override
    public List<AircraftTypeResponse> getAircraftTypesByManufacturer(String manufacturer) {

        return aircraftTypeRepository.findByManufacturerIgnoreCase(manufacturer)
                .stream()
                .map(AircraftTypeMapper::toResponse)
                .toList();
    }

    @Override
    public List<AircraftTypeResponse> getAircraftTypesByStatus(AircraftTypeStatus status) {
        return aircraftTypeRepository.findByStatus(status)
                .stream()
                .map(AircraftTypeMapper::toResponse)
                .toList();
    }

    @Override
    public AircraftTypeResponse changeStatus(Long id, AircraftTypeStatus status) {
       AircraftType aircraftType = aircraftTypeRepository.findById(id)
               .orElseThrow(
                       () -> new ResourceNotFoundException(
                               "id",
                               "id",
                               id
                       )
               );
       aircraftType.setStatus(status);
       AircraftType savedAircraftType =
               aircraftTypeRepository.save(aircraftType);
        return AircraftTypeMapper.toResponse(savedAircraftType);
    }

    @Override
    public List<AircraftTypeResponse> getAircraftTypeDropdown() {
        return aircraftTypeRepository
                .findByStatus(AircraftTypeStatus.ACTIVE)
                .stream()
                .map(AircraftTypeMapper::toResponse)
                .toList();
    }
}
