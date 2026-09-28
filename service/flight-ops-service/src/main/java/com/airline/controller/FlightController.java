package com.airline.controller;

import com.airline.enums.FlightStatus;
import com.airline.playload.request.FlightRequest;
import com.airline.playload.response.FlightResponse;
import com.airline.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/v1/flights",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Tag(
        name = "Flight",
        description = "Operations for managing flights"
)
public class FlightController {

    private final FlightService flightService;

    // =========================================================
    // CREATE FLIGHT
    // =========================================================

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Create a new flight",
            description = "Create a new flight for an airline"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Flight created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Airline, aircraft or airport not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Flight number already exists"
            )
    })
    public ResponseEntity<FlightResponse> createFlight(
            @RequestHeader("Airline-Id") Long airlineId,
            @RequestBody FlightRequest flightRequest
    ) {

        FlightResponse flightResponse =
                flightService.createFlight(
                        airlineId,
                        flightRequest
                );

        return ResponseEntity
                .status(201)
                .body(flightResponse);
    }


    // =========================================================
    // GET FLIGHT BY ID
    // =========================================================

    @GetMapping("/{id}")
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
    public ResponseEntity<FlightResponse> getFlightById(
            @PathVariable Long id
    ) {

        FlightResponse flightResponse =
                flightService.getFlightById(id);

        return ResponseEntity.ok(flightResponse);
    }


    // =========================================================
    // GET FLIGHTS BY AIRLINE
    // =========================================================

    @GetMapping("/airline")
    @Operation(
            summary = "Get flights by airline",
            description = "Retrieve paginated flights belonging to an airline with optional departure and arrival airport filters"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Flights retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Airline not found"
            )
    })
    public ResponseEntity<Page<FlightResponse>> getFlightByAirlineId(
            @RequestHeader("Airline-Id") Long airlineId,
            @RequestParam(required = false) Long departureAirportId,
            @RequestParam(required = false) Long arrivalAirportId,
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                flightService.getFlightByAirline(
                        airlineId,
                        departureAirportId,
                        arrivalAirportId,
                        pageable
                )
        );
    }


    // =========================================================
    // GET ALL FLIGHTS
    // =========================================================

    @GetMapping
    @Operation(
            summary = "Get all flights",
            description = "Retrieve all flights"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Flights retrieved successfully"
            )
    })
    public ResponseEntity<List<FlightResponse>> getAllFlights() {

        return ResponseEntity.ok(
                flightService.getAllFlights()
        );
    }


    // =========================================================
    // UPDATE FLIGHT
    // =========================================================

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update flight",
            description = "Update an existing flight"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Flight updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Flight, aircraft or airport not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Flight number already exists"
            )
    })
    public ResponseEntity<FlightResponse> updateFlight(
            @PathVariable Long id,
            @Valid @RequestBody FlightRequest request
    ) {

        return ResponseEntity.ok(
                flightService.updateFlight(
                        id,
                        request
                )
        );
    }


    // =========================================================
    // CHANGE FLIGHT STATUS
    // =========================================================

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Change flight status",
            description = "Update the status of an existing flight"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Flight status updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Flight not found"
            )
    })
    public ResponseEntity<FlightResponse> changeStatus(
            @PathVariable Long id,
            @RequestParam FlightStatus status
    ) {

        return ResponseEntity.ok(
                flightService.changeStatus(
                        id,
                        status
                )
        );
    }


    // =========================================================
    // DELETE FLIGHT
    // =========================================================

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete flight",
            description = "Delete an existing flight belonging to an airline"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Flight deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Flight not found for the specified airline"
            )
    })
    public ResponseEntity<Void> deleteFlight(
            @RequestHeader("Airline-Id") Long airlineId,
            @PathVariable Long id
    ) {

        flightService.deleteFlight(
                airlineId,
                id
        );

        return ResponseEntity.noContent().build();
    }
}
