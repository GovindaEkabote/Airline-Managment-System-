package com.airline.config.feignclient;

import com.airline.playload.response.AircraftResponse;
import com.airline.playload.response.AirlineResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AIRLINE-SERVICE")
public interface AirlineClient {

    @GetMapping("/api/v1/airlines/{id}")
    @Operation(summary = "Get airline by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Airline found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Airline not found"
            )
    })
    AirlineResponse getAirlineById(
            @PathVariable Long id);


    @GetMapping("/api/v1/aircraft/{id}")
    @Operation(
            summary = "Get aircraft by ID",
            description = "Retrieves an aircraft along with its aircraft type and airline information."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Aircraft retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aircraft not found"
            )
    })
    AircraftResponse getAircraftById(
            @Parameter(description = "Aircraft ID")
            @PathVariable Long id);
}
