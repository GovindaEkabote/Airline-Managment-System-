package com.airline.service;

import com.airline.enums.SeatAvailabilityStatus;
import com.airline.playload.response.SeatInstanceResponse;

import java.math.BigDecimal;
import java.util.List;

public interface SeatInstanceService {

    BigDecimal calculateSeatPrice(List<Long> seatInstance);

    SeatInstanceResponse updateSeatInstanceResponse(Long seatInstanceId, SeatAvailabilityStatus status);
}
