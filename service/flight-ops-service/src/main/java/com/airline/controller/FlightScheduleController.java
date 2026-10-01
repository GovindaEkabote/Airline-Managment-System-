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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> getFlightScheduleById(
            @PathVariable Long id
    ){
        FlightScheduleResponse flightScheduleResponse =
                flightScheduleService.getFlightScheduleById(id);
        return ResponseEntity.ok(flightScheduleResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> updateFlightSchedule(
           @PathVariable Long id,
           @RequestBody FlightScheduleRequest  request
    ){
        FlightScheduleResponse flightScheduleResponse =
                flightScheduleService.updateFlightSchedule(id, request);
        return ResponseEntity.ok(flightScheduleResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<com.airline.playload.response.ApiResponse> deleteFlightSchedule(
            @PathVariable Long id){
        flightScheduleService.deleteFlightSchedule(id);
        com.airline.playload.response.ApiResponse apiResponses = new com.airline.playload.response.ApiResponse();
        return ResponseEntity.ok(apiResponses);
    }

    @GetMapping("/airlineId/{airlineId}")
    public ResponseEntity<List<FlightScheduleResponse>> getFlightSchedulesByAirline(
            @PathVariable Long airlineId
    ){
        List<FlightScheduleResponse> flightScheduleResponse =
                flightScheduleService.getFlightSchedulesByAirline(airlineId);
        return ResponseEntity.ok(flightScheduleResponse);
    }

    @GetMapping("/day/{dayOfWeek}")
    @Operation(
            summary = "Get schedules by operating day"
    )
    public ResponseEntity<Page<FlightScheduleResponse>> getSchedulesByDay(
            @PathVariable DayOfWeek dayOfWeek,
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                flightScheduleService.getSchedulesByDay(
                        dayOfWeek,
                        pageable
                )
        );
    }

    @GetMapping("/arrival-airport/{airportId}")
    @Operation(
            summary = "Get schedules by arrival airport"
    )
    public ResponseEntity<Page<FlightScheduleResponse>> getSchedulesByArrivalAirport(
            @PathVariable Long airportId,
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                flightScheduleService.getSchedulesByArrivalAirport(
                        airportId,
                        pageable
                )
        );
    }


    @GetMapping("/departure-airport/{airportId}")
    @Operation(
            summary = "Get schedules by departure airport"
    )
    public ResponseEntity<Page<FlightScheduleResponse>> getSchedulesByDepartureAirport(
            @PathVariable Long airportId,
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                flightScheduleService.getSchedulesByDepartureAirport(
                        airportId,
                        pageable
                )
        );
    }

    @GetMapping
    @Operation(
            summary = "Get all flight schedules",
            description = "Returns paginated flight schedules."
    )
    public ResponseEntity<Page<FlightScheduleResponse>> getAllFlightSchedules(
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                flightScheduleService.getAllFlightSchedules(pageable)
        );
    }

    @GetMapping("/flight/{flightId}")
    @Operation(
            summary = "Get flight schedules by flight"
    )
    public ResponseEntity<Page<FlightScheduleResponse>> getSchedulesByFlight(
            @PathVariable Long flightId,
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                flightScheduleService.getSchedulesByFlight(
                        flightId,
                        pageable
                )
        );
    }

}
