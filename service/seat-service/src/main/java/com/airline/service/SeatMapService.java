package com.airline.service;

import com.airline.model.SeatGenerationRequest;
import com.airline.playload.request.SeatMapRequest;
import com.airline.playload.response.SeatMapResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SeatMapService {

    SeatMapResponse create(Long userId,SeatMapRequest request) throws Exception;  // one map per cabin class
    SeatMapResponse getSeatMapById(Long id);    // includes seats
    SeatMapResponse getSeatMapCabinClass(Long cabinClassId);
    Page<SeatMapResponse> getByAirlineId(Long airlineId, Pageable pageable);
    SeatMapResponse updateSeatMap(Long id, SeatMapRequest request);
    void  delete(Long id);  // reject if flight already use it..
}
