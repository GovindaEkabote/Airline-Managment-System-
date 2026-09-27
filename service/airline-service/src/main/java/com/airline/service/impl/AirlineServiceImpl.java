package com.airline.service.impl;

import com.airline.enums.AirlineStatus;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.AirlineMapper;
import com.airline.model.Airline;
import com.airline.playload.request.AirlineRequest;
import com.airline.playload.response.AirlineDropdownItem;
import com.airline.playload.response.AirlineResponse;
import com.airline.repository.AirlineRepository;
import com.airline.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;

    @Override
    public AirlineResponse createAirline(
            AirlineRequest airlineRequest,
            Long ownerId) {

        Airline airline = AirlineMapper.toEntity(
                airlineRequest,
                ownerId
        );

        Airline savedAirline = airlineRepository.save(airline);

        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    public AirlineResponse getAirlineById(Long id) {

        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Airline not found with id: " + id
                        )
                );

        return AirlineMapper.toResponse(airline);
    }

    @Override
    public AirlineResponse getAirlineByOwnerId(Long ownerId) {

        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Airline not found with owner id: " + ownerId
                        )
                );

        return AirlineMapper.toResponse(airline);
    }

    @Override
    public AirlineResponse getAirlineByIataCode(String iataCode) {

        Airline airline = airlineRepository.findByIataCode(iataCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Airline not found with IATA code: " + iataCode
                        )
                );

        return AirlineMapper.toResponse(airline);
    }

    @Override
    public AirlineResponse getAirlineByIcaoCode(String icaoCode) {

        Airline airline = airlineRepository.findByIcaoCode(icaoCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Airline not found with ICAO code: " + icaoCode
                        )
                );

        return AirlineMapper.toResponse(airline);
    }

    @Override
    public Page<AirlineResponse> getAllAirlines(Pageable pageable) {

        return airlineRepository
                .findAll(pageable)
                .map(AirlineMapper::toResponse);
    }

    @Override
    public AirlineResponse updateAirline(
            AirlineRequest request,
            Long ownerId) {

        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Airline not found with owner id: " + ownerId
                        )
                );

        AirlineMapper.updateEntity(airline, request);

        Airline updatedAirline =
                airlineRepository.save(airline);

        return AirlineMapper.toResponse(updatedAirline);
    }

    @Override
    public void deleteAirline(Long id) {

        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Airline not found with id: " + id
                        )
                );

        airlineRepository.delete(airline);
    }

    @Override
    public AirlineResponse changeStatusByAdmin(
            Long airlineId,
            AirlineStatus status) {

        Airline airline = airlineRepository.findById(airlineId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Airline not found with id: " + airlineId
                        )
                );

        airline.setStatus(status);

        Airline savedAirline =
                airlineRepository.save(airline);

        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    public List<AirlineDropdownItem> getAirlineDropdown() {

        return airlineRepository
                .findByStatus(AirlineStatus.ACTIVE)
                .stream()
                .map(airline ->
                        AirlineDropdownItem.builder()
                                .id(airline.getId())
                                .name(airline.getName())
                                .iataCode(airline.getIataCode())
                                .icaoCode(airline.getIcaoCode())
                                .logoUrl(airline.getLogoUrl())
                                .build()
                )
                .toList();
    }
}