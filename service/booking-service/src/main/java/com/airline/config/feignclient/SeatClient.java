package com.airline.config.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "SEAT-SERVICE")
public interface SeatClient {

    @PostMapping("/api/v1/seat/price")
    BigDecimal calculatePrice(@RequestBody List<Long> seatInstanceIds);

}
