package com.airline.service.impl;

import com.airline.playload.request.FlightSearchRequest;
import com.airline.playload.response.FlightInstanceResponse;
import com.airline.service.FlightSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightSearchServiceImpl implements FlightSearchService {




    @Override
    public Page<FlightInstanceResponse> searchFlight(FlightSearchRequest request, Pageable pageable) {
        return null;
    }
}
