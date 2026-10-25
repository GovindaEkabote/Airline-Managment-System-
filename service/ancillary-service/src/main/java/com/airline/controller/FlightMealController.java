package com.airline.controller;

import com.airline.playload.request.FlightMealRequest;
import com.airline.playload.response.FlightMealResponse;
import com.airline.service.FlightMealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(
        value = "/api/v1/flight/meal",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Tag(
        name = "Flight Meal",
        description = "Operations for managing airline flight meals"
)
public class FlightMealController {

    private final FlightMealService flightMealService;

    // Create Flight Meal
    @PostMapping(
            value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Create Flight Meal",
            description = "Creates a new flight meal service."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Flight meal created successfully"),
            @ApiResponse(responseCode = "400",
                    description = "Validation failed"),
            @ApiResponse(responseCode = "404",
                    description = "Associated flight or meal not found"),
            @ApiResponse(responseCode = "409",
                    description = "Flight meal already exists")
    })
    public ResponseEntity<FlightMealResponse> createFlightMeal(
            @Valid @RequestBody FlightMealRequest request
    ) {
        FlightMealResponse response =
                flightMealService.createFlightMeal(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get Flight Meal by ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Get Flight Meal by ID",
            description = "Retrieves a flight meal using its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Flight meal retrieved successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Flight meal not found")
    })
    public ResponseEntity<FlightMealResponse> getFlightMealById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                flightMealService.getFlightMealById(id)
        );
    }

    // Get Flight Meals by Flight ID
    @GetMapping("/flight/{flightId}")
    @Operation(
            summary = "Get Flight Meals by Flight ID",
            description = "Retrieves all meals associated with a flight."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Flight meals retrieved successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Flight not found")
    })
    public ResponseEntity<List<FlightMealResponse>> getFlightMealsByFlightId(
            @PathVariable Long flightId
    ) {
        return ResponseEntity.ok(
                flightMealService.getFlightId(flightId)
        );
    }

    // Get Flight Meals by Multiple IDs
    @PostMapping(
            value = "/by-ids",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Get Flight Meals by IDs",
            description = "Retrieves flight meals matching the provided IDs."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Flight meals retrieved successfully"),
            @ApiResponse(responseCode = "400",
                    description = "Invalid meal IDs")
    })
    public ResponseEntity<List<FlightMealResponse>> getAllByIds(
            @RequestBody List<Long> ids
    ) {
        return ResponseEntity.ok(
                flightMealService.getAllByIds(ids)
        );
    }

    // Update Flight Meal
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update Flight Meal",
            description = "Updates an existing flight meal."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Flight meal updated successfully"),
            @ApiResponse(responseCode = "400",
                    description = "Validation failed"),
            @ApiResponse(responseCode = "404",
                    description = "Flight meal not found")
    })
    public ResponseEntity<FlightMealResponse> updateFlightMeal(
            @PathVariable Long id,
            @Valid @RequestBody FlightMealRequest request
    ) {
        return ResponseEntity.ok(
                flightMealService.updateFlightMeal(id, request)
        );
    }

    // Delete Flight Meal
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Flight Meal",
            description = "Deletes a flight meal using its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Flight meal deleted successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Flight meal not found")
    })
    public ResponseEntity<Void> deleteFlightMeal(
            @PathVariable Long id
    ) {
        flightMealService.deleteFlightMeal(id);

        return ResponseEntity.noContent().build();
    }

    // Update Flight Meal Availability
    @PatchMapping("/{id}/availability")
    @Operation(
            summary = "Update Flight Meal Availability",
            description = "Enables or disables availability of a flight meal."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Availability updated successfully"),
            @ApiResponse(responseCode = "400",
                    description = "Invalid availability value"),
            @ApiResponse(responseCode = "404",
                    description = "Flight meal not found")
    })
    public ResponseEntity<FlightMealResponse> updateFlightMealAvailability(
            @PathVariable Long id,
            @RequestParam Boolean availability
    ) {
        return ResponseEntity.ok(
                flightMealService.updateFlightMealAvailability(
                        id, availability
                )
        );
    }

    // Calculate Total Meal Price
    @PostMapping(
            value = "/calculate-price",
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
    public ResponseEntity<Double> calculateMealPrice(
            @RequestBody Set<Long> mealIds
    ) {
        return ResponseEntity.ok(
                flightMealService.calculateMealPrice(mealIds)
        );
    }
}
