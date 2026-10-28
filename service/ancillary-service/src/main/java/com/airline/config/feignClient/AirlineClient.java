package com.airline.config.feignClient;

import com.airline.playload.response.AirlineResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "AIRLINE-SERVICE")
public interface AirlineClient {

    @GetMapping("/api/v1/airlines/owner/{ownerId}")
    AirlineResponse getAirlineByOwnerId(@RequestHeader("X-User-Id") Long ownerId);

}
