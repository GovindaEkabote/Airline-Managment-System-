package com.airline.service;

import com.airline.playload.request.SeatRequest;
import com.airline.playload.response.SeatResponse;

import java.util.List;

public interface SeatService {

    void generateSeatsForSeatMap(
            Long seatMapId);

    SeatResponse updateSeat(Long seatId, SeatRequest request);


    // getAll
    List<SeatResponse> getAllSeats();
}
