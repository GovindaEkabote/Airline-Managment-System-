package com.airline.config.feignclient;

import com.airline.playload.response.FlightInstanceResponse;
import com.airline.playload.response.FlightResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("FLIGHT-OPS-SERVICE")
public interface FlightClient {

    @GetMapping("/api/v1/flights/{id}")
    FlightResponse getFlightById(
            @PathVariable Long id
    );

    @GetMapping("/api/v1/flights-instance/{id}")
    public FlightInstanceResponse getFlightInstanceById(
            @PathVariable Long id
    );
}
