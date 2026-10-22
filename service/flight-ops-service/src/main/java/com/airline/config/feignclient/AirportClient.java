package com.airline.config.feignclient;

import com.airline.playload.response.AirportResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "LOCATION-SERVICE")
public interface AirportClient {


    @GetMapping("/api/v1/airports/{id}")
    @Operation(summary = "Get an Airport by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Airport found"),
            @ApiResponse(responseCode = "404", description = "Airport not found")
    })
    AirportResponse getAirport(
            @Parameter(description = "Airport id", required = true)
            @PathVariable Long id);
}
