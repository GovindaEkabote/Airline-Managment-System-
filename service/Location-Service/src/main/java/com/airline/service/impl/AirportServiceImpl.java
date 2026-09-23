package com.airline.service.impl;

import com.airline.exception.DuplicateResourceException;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.AirportMapper;
import com.airline.model.Airport;
import com.airline.model.City;
import com.airline.playload.request.AirportRequest;
import com.airline.playload.response.AirportResponse;
import com.airline.repository.AirportRepository;
import com.airline.repository.CityRepository;
import com.airline.service.AirportService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    @Override
    public AirportResponse createAirport(AirportRequest airportRequest) {
        if (airportRequest == null) throw new IllegalArgumentException("Airport request must not be null");
        if (airportRequest.getCityId() == null)
            throw new IllegalArgumentException("cityId must not be null");

        String iataCode = normalize(airportRequest.getIataCode());
        String icaoCode = normalize(airportRequest.getIcaoCode());
        if (airportRepository.existsByIataCodeIgnoreCase(iataCode)){
            throw new DuplicateResourceException(
                    "iata",
                    "iataCode",
                    iataCode
            );
        }
        if (airportRepository.existsByIcaoCodeIgnoreCase(icaoCode)){
            throw new DuplicateResourceException(
                    "icao",
                    "icaoCode",
                    icaoCode
            );
        }
        City city = cityRepository.findById(airportRequest.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "City",
                        "id",
                        airportRequest.getCityId()
                ));
        Airport airport = AirportMapper.toEntity(airportRequest, city);
        airport.setIataCode(iataCode);
        airport.setIcaoCode(icaoCode);
        Airport saveedAirport = airportRepository.save(airport);
        return AirportMapper.toResonse(saveedAirport);
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest airportRequest) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Airport",
                        "id",
                        id
                ));
        //check ICAO belongs to another airport?
        String iata = normalize(airportRequest.getIataCode());     // ← #1
        String icao = normalize(airportRequest.getIcaoCode());     // ← #1

        if (iata != null
                && airportRepository.existsByIataCodeIgnoreCaseAndIdNot(iata, id)) {
            throw new DuplicateResourceException("iata", "iataCode", iata);
        }
        if (icao != null
                && airportRepository.existsByIcaoCodeIgnoreCaseAndIdNot(icao, id)) {
            throw new DuplicateResourceException("icao", "icaoCode", icao);
        }

        AirportMapper.updateEntity(airport, airportRequest);
        if (airportRequest.getCityId() != null){
            City city = cityRepository.findById(airportRequest.getCityId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "City Not Found with id",
                            "id",
                            airportRequest.getCityId()
                    ));
            airport.setCity(city);
        }
        Airport saved = airportRepository.save(airport);
        return AirportMapper.toResonse(saved);
    }

    @Override
    public void deleteAirport(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Airport",
                        "id",
                        id
                ));
        airportRepository.delete(airport);
    }

    @Override
    public AirportResponse getAirport(Long id) {
        Airport airport
                = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Airport Not Found",
                        "id",
                        id
                ));

        return AirportMapper.toResonse(airport);
    }

    @Override
    public Page<AirportResponse> getAllAirports(Pageable pageable) {
        return airportRepository.findByActiveTrue(pageable)
                .map(AirportMapper::toResonse);
    }

    @Override
    public Page<AirportResponse> getAirportsByCityId(Long cityId, Pageable pageable) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "City",
                        "id",
                        cityId
                ));
        Page<Airport> airports = airportRepository.findByCityIdAndActiveTrue(city.getId(), pageable);
        return airports.map(AirportMapper::toResonse);
    }

    @Override
    public Page<AirportResponse> searchAirports(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Search keyword must not be blank");
        }
        keyword = keyword.trim();
        return airportRepository.searchAirports(keyword, pageable)
                .map(AirportMapper::toResonse);
    }

    @Override
    public void activateAirport(Long id) {
        Airport airport  = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Airport",
                        "id",
                        id
                ));
        airport.setActive(true);
        airportRepository.save(airport);
    }

    @Override
    public void deactivateAirport(Long id) {
        Airport airport  = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Airport",
                        "id",
                        id
                ));
        airport.setActive(false);
        airportRepository.save(airport);
    }

    private static String normalize(String code) {
        return code == null ? null : code.trim().toUpperCase();
    }
}
