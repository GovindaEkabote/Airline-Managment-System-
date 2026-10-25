package com.airline.service;

import java.math.BigDecimal;
import java.util.List;

public interface SeatInstanceService {

    BigDecimal calculateSeatPrice(List<Long> seatInstance);
}
