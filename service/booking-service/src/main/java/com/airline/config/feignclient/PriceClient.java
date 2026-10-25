package com.airline.config.feignclient;

import com.airline.playload.response.FareResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRICING-SERVICE")
public interface PriceClient {

    @GetMapping(
            value = "/api/v1/fares/{id}"
    )
    @Operation(summary = "Get Fares details by Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fare found"),
            @ApiResponse(responseCode = "404", description = "Fare not found")
    })
    FareResponse getFareById(@PathVariable Long id);
}
