package com.airline.controller;

import com.airline.playload.request.FlightInstanceRequest;
import com.airline.playload.response.FlightInstanceResponse;
import com.airline.service.FlightInstanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/api/v1/flights-instance",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Tag(
        name = "Flight Instance",
        description = "Operations for managing flight instances"
)
public class FlightInstanceController {

    private final FlightInstanceService flightInstanceService;

    // =========================
    // CREATE
    // =========================

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a new flight instance",
            description = "Create a new flight instance for an airline"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Flight instance created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Flight not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Flight instance already exists"
            )
    })
    public ResponseEntity<FlightInstanceResponse> createFlightInstance(
            @RequestHeader("airlineId") Long airlineId,
            @RequestBody FlightInstanceRequest request
    ) {

        FlightInstanceResponse response =
                flightInstanceService.createFlightInstance(
                        airlineId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // =========================
    // GET BY ID
    // =========================

    @GetMapping("/{id}")
    @Operation(
            summary = "Get flight instance by ID",
            description = "Retrieve a flight instance by its ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Flight instance retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Flight instance not found"
            )
    })
    public ResponseEntity<FlightInstanceResponse> getFlightInstanceById(
            @PathVariable Long id
    ) {

        FlightInstanceResponse response =
                flightInstanceService.getFlightInstanceById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
            summary = "Get all flight instances",
            description = "Retrieve all flight instances with pagination"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Flight instances retrieved successfully"
            )
    })
    public ResponseEntity<Page<FlightInstanceResponse>> getAllFlightInstances(
            Pageable pageable
    ) {

        Page<FlightInstanceResponse> response =
                flightInstanceService.getAllFlightInstances(pageable);

        return ResponseEntity.ok(response);
    }

    // =========================
    // GET BY FLIGHT ID
    // =========================

    @GetMapping("/flight/{flightId}")
    @Operation(
            summary = "Get flight instances by flight ID",
            description = "Retrieve all instances belonging to a flight"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Flight instances retrieved successfully"
            )
    })
    public ResponseEntity<Page<FlightInstanceResponse>> getFlightInstancesByFlightId(
            @PathVariable Long flightId,
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                flightInstanceService.getFlightInstancesByFlightId(
                        flightId,
                        pageable
                )
        );
    }

    // =========================
    // GET BY SCHEDULE ID
    // =========================

    @GetMapping("/schedule/{scheduleId}")
    @Operation(
            summary = "Get flight instances by schedule ID",
            description = "Retrieve all flight instances belonging to a schedule"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Flight instances retrieved successfully"
            )
    })
    public ResponseEntity<Page<FlightInstanceResponse>> getFlightInstancesByScheduleId(
            @PathVariable Long scheduleId,
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                flightInstanceService.getFlightInstancesByScheduleId(
                        scheduleId,
                        pageable
                )
        );
    }

    // =========================
    // GET BY AIRLINE + FILTERS
    // =========================

    @GetMapping("/search")
    @Operation(
            summary = "Search flight instances",
            description = """
                    Retrieve paginated flight instances for an airline.
                    Departure airport, arrival airport, flight ID and date
                    are optional filters.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Flight instances retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Airline not found"
            )
    })
    public ResponseEntity<Page<FlightInstanceResponse>> getByAirlineId(
            @RequestHeader("airlineId") Long airlineId,

            @RequestParam(required = false)
            Long departureAirportId,

            @RequestParam(required = false)
            Long arrivalAirportId,

            @RequestParam(required = false)
            Long flightId,

            @RequestParam(required = false)
            java.time.LocalDate onDate,

            Pageable pageable
    ) {

        return ResponseEntity.ok(
                flightInstanceService.getByAirlineId(
                        airlineId,
                        departureAirportId,
                        arrivalAirportId,
                        flightId,
                        onDate,
                        pageable
                )
        );
    }

    // =========================
    // UPDATE
    // =========================

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update flight instance",
            description = "Update an existing flight instance"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Flight instance updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Flight instance not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            )
    })
    public ResponseEntity<FlightInstanceResponse> updateFlightInstance(
            @PathVariable Long id,
            @RequestBody FlightInstanceRequest request
    ) {

        return ResponseEntity.ok(
                flightInstanceService.updateFlightInstance(
                        id,
                        request
                )
        );
    }

    // =========================
    // DELETE
    // =========================

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete flight instance",
            description = "Delete an existing flight instance"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Flight instance deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Flight instance not found"
            )
    })
    public ResponseEntity<Void> deleteFlightInstance(
            @PathVariable Long id
    ) {

        flightInstanceService.deleteFlightInstance(id);

        return ResponseEntity.noContent().build();
    }

    // =========================
    // CHANGE STATUS
    // =========================

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Change flight instance status",
            description = "Change the status of a flight instance"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Flight instance status updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Flight instance not found"
            )
    })
    public ResponseEntity<FlightInstanceResponse> changeStatus(
            @PathVariable Long id,
            @RequestParam com.airline.enums.FlightStatus status
    ) {

        return ResponseEntity.ok(
                flightInstanceService.changeStatus(
                        id,
                        status
                )
        );
    }
}