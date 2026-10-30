package com.airline.service;

import com.airline.enums.AirlineStatus;
import com.airline.playload.request.AirlineRequest;
import com.airline.playload.response.AirlineDropdownItem;
import com.airline.playload.response.AirlineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirlineService {

    AirlineResponse createAirline(AirlineRequest airlineRequest, Long ownerId);
    AirlineResponse getAirlineById(Long id);
    AirlineResponse getAirlineByOwnerId(Long userId);
    AirlineResponse getAirlineByIataCode(String iataCode);
    AirlineResponse getAirlineByIcaoCode(String icaoCode);
    Page<AirlineResponse> getAllAirlines(Pageable pageable);
    AirlineResponse updateAirline(AirlineRequest request,Long ownerId);
    void deleteAirline(Long id);
    AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status);
    List<AirlineDropdownItem> getAirlineDropdown();
}
