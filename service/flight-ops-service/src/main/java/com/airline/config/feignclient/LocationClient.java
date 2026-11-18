package com.airline.config.feignclient;

import com.airline.playload.response.AirportResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "LOCATION-SERVICE", contextId = "locationClient")
public interface LocationClient {

    @GetMapping("/api/v1/airports/{id}")
    AirportResponse getAirport(
            @Parameter(description = "Airport id", required = true)
            @PathVariable Long id);
}
