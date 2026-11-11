package com.airline.service;


import com.airline.playload.request.FlightSearchRequest;
import com.airline.playload.response.FlightInstanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightSearchService {

    Page<FlightInstanceResponse> searchFlight(
            FlightSearchRequest request,
            Pageable pageable);
}
