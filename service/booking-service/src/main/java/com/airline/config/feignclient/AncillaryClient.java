package com.airline.config.feignclient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@FeignClient(name = "ANCILLARY-SERVICE")
public interface AncillaryClient {

    @PostMapping(
            value = "/api/v1/flight/cabin/ancillary/calculate-price",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Calculate Total Ancillary Price")
    Double calculateAncillaryPrice(@RequestBody Set<Long> ancillaryIds);


    @PostMapping(
            value = "/api/v1/flight/meal/calculate-price",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Calculate Meal Price",
            description = "Calculates the total price of the selected meals."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Meal price calculated successfully"),
            @ApiResponse(responseCode = "400",
                    description = "Invalid meal IDs"),
            @ApiResponse(responseCode = "404",
                    description = "One or more meals not found")
    })
    Double calculateMealPrice(
            @RequestBody Set<Long> mealIds
    );
}
