package com.airline.controller;


import com.airline.model.FlightSchedule;
import com.airline.playload.request.FlightScheduleRequest;
import com.airline.playload.response.FlightScheduleResponse;
import com.airline.service.FlightScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/api/v1/flight-schedules",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Tag(
        name = "Flight Schedule",
        description = "Flight Schedule Management"
)
public class FlightScheduleController {

    private final FlightScheduleService flightScheduleService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Create Flight Schedule",
            description = "Create a new flight schedule"
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
    public ResponseEntity<FlightScheduleResponse> createFlightSchedule(
            @RequestHeader("airlineId") Long airlineId,
            @RequestBody FlightScheduleRequest request) {
        FlightScheduleResponse flightScheduleResponse =
                flightScheduleService.createFlightSchedule(airlineId,
                        request);
        return ResponseEntity
                .status(201)
                .body(flightScheduleResponse);
    }
}
