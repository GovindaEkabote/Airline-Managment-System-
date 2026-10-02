package com.airline.controller;

import com.airline.playload.request.FareRequest;
import com.airline.playload.response.FareResponse;
import com.airline.service.FareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(
        value = "/api/v1/fares",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Fare Management",
        description = "Operations for managing fares"
)
public class FareController {

    private final FareService fareService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new Fare Management")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Fare created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Fare not found"),
            @ApiResponse(responseCode = "409", description = "Fare already exists")
    })
    public ResponseEntity<FareResponse> createFare(
            @Valid @RequestBody FareRequest fareRequest) {
        log.info("Creating fare: {}", fareRequest);
        FareResponse created =  fareService.createFare(fareRequest);
        return ResponseEntity.ok(created);
    }


    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update an existing Fare")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fare updated"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Fare not found"),
            @ApiResponse(responseCode = "409", description = "Fare already exists")
    })
    public ResponseEntity<FareResponse> updateFare(
            @PathVariable Long id,
            @Valid @RequestBody FareRequest request
    ){
        return ResponseEntity.ok(
                fareService.updateFare(id, request)
        );
    }

    @GetMapping(
            value = "/{id}"
    )
    @Operation(summary = "Get Fares details by Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fare found"),
            @ApiResponse(responseCode = "404", description = "Fare not found")
    })
    public ResponseEntity<FareResponse> getFareById(@PathVariable Long id) {
        return ResponseEntity.ok(fareService.getFareById(id));
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<FareResponse>> getFaresByFlightCabinClassId(
            @PathVariable Long flightId,
            @PathVariable Long cabinClassId
    ){
        return ResponseEntity.ok(fareService.getFaresByFlightCabinClassId(flightId, cabinClassId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FareResponse>> getAllFares() {
        return ResponseEntity.ok(fareService.getAllFares());
    }

    @DeleteMapping("/{id}")
    public void deleteFare(@PathVariable Long id) {
        fareService.deleteFare(id);
    }

    @GetMapping("/lowest")
    @Operation(
            summary = "Get lowest fare per flight",
            description = "Returns the lowest fare for each flight for the given cabin class"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lowest fares retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<Map<Long, FareResponse>> getLowestFarePerFlight(
            @RequestParam List<Long> flightIds,
            @RequestParam Long cabinClassId) {
        log.info("Getting lowest fare per flight: {}", flightIds);
        Map<Long, FareResponse> response = fareService
                .getLowestFarePerFlight(flightIds, cabinClassId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-ids")
    public ResponseEntity<Map<Long, FareResponse>> getFaresByIds(
            @RequestParam List<Long> ids) {

        Map<Long, FareResponse> response =
                fareService.getFaresByIds(ids);

        return ResponseEntity.ok(response);
    }


}
