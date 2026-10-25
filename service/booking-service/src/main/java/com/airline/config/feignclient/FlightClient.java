package com.airline.config.feignclient;

import com.airline.playload.response.FlightResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("FLIGHT-OPS-SERVICE")
public interface FlightClient {

    @GetMapping("/api/v1/flights/{id}")
    @Operation(
            summary = "Get flight by ID",
            description = "Retrieve a flight by its unique ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Flight retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Flight not found"
            )
    })
    FlightResponse getFlightById(
            @PathVariable Long id
    );
}
