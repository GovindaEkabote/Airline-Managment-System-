package com.airline.service;

import com.airline.playload.request.AirportRequest;
import com.airline.playload.response.AirportResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AirportService {

    AirportResponse createAirport(AirportRequest airportRequest);

    AirportResponse updateAirport(Long id, AirportRequest airportRequest);

    void deleteAirport(Long id);

    AirportResponse getAirport(Long id);

    Page<AirportResponse> getAllAirports(Pageable pageable);

    Page<AirportResponse> getAirportsByCityId(Long cityId, Pageable pageable);

    Page<AirportResponse> searchAirports(String keyword, Pageable pageable);

    void activateAirport(Long id);

    void deactivateAirport(Long id);
}