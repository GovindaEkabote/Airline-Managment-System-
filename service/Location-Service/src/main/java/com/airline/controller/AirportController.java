package com.airline.controller;

import com.airline.playload.request.AirportRequest;
import com.airline.playload.response.AirportResponse;
import com.airline.service.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(
        value = "/api/v1/airports",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Airports",
        description = "Operations for managing airline airports"
)
public class AirportController {

    private final AirportService airportService;

    // ---------------------------------------------------------------
    // CREATE
    // ---------------------------------------------------------------
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new Airport")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Airport created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "City not found"),
            @ApiResponse(responseCode = "409", description = "IATA or ICAO code already exists")
    })
    public ResponseEntity<AirportResponse> createAirport(
            @Valid @RequestBody AirportRequest airportRequest) {

        AirportResponse created = airportService.createAirport(airportRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    // ---------------------------------------------------------------
    // READ — single
    // ---------------------------------------------------------------
    @GetMapping("/{id}")
    @Operation(summary = "Get an Airport by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Airport found"),
            @ApiResponse(responseCode = "404", description = "Airport not found")
    })
    public ResponseEntity<AirportResponse> getAirport(
            @Parameter(description = "Airport id", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(airportService.getAirport(id));
    }

    // ---------------------------------------------------------------
    // READ — all (active only, paged)
    // ---------------------------------------------------------------
    @GetMapping
    @Operation(summary = "Get all active airports (paginated)")
    @ApiResponse(responseCode = "200", description = "Page of airports")
    public ResponseEntity<Page<AirportResponse>> getAllAirports(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            @Parameter(hidden = true)
            Pageable pageable) {
        return ResponseEntity.ok(airportService.getAllAirports(pageable));
    }

    // ---------------------------------------------------------------
    // READ — by city
    // ---------------------------------------------------------------
    @GetMapping("/city/{cityId}")
    @Operation(summary = "Get active airports by city id (paginated)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page of airports"),
            @ApiResponse(responseCode = "404", description = "City not found")
    })
    public ResponseEntity<Page<AirportResponse>> getAirportsByCityId(
            @Parameter(description = "City id", required = true)
            @PathVariable Long cityId,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            @Parameter(hidden = true)
            Pageable pageable) {
        return ResponseEntity.ok(airportService.getAirportsByCityId(cityId, pageable));
    }

    // ---------------------------------------------------------------
    // READ — search
    // ---------------------------------------------------------------
    @GetMapping("/search")
    @Operation(summary = "Search airports by keyword (name / IATA / ICAO)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page of matching airports"),
            @ApiResponse(responseCode = "400", description = "Blank keyword")
    })
    public ResponseEntity<Page<AirportResponse>> searchAirports(
            @RequestParam("keyword") @NotBlank String keyword,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            @Parameter(hidden = true)
            Pageable pageable) {
        return ResponseEntity.ok(airportService.searchAirports(keyword, pageable));
    }

    // ---------------------------------------------------------------
    // UPDATE
    // ---------------------------------------------------------------
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update an existing Airport")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Airport updated"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Airport or City not found"),
            @ApiResponse(responseCode = "409", description = "IATA or ICAO code already exists")
    })
    public ResponseEntity<AirportResponse> updateAirport(
            @Parameter(description = "Airport id", required = true)
            @PathVariable Long id,
            @Valid @RequestBody AirportRequest airportRequest) {
        return ResponseEntity.ok(airportService.updateAirport(id, airportRequest));
    }

    // ---------------------------------------------------------------
    // DELETE
    // ---------------------------------------------------------------
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an Airport")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Airport deleted"),
            @ApiResponse(responseCode = "404", description = "Airport not found"),
            @ApiResponse(responseCode = "409", description = "Airport is referenced and cannot be deleted")
    })
    public ResponseEntity<Void> deleteAirport(
            @Parameter(description = "Airport id", required = true)
            @PathVariable Long id) {
        airportService.deleteAirport(id);
        return ResponseEntity.noContent().build();
    }

    // ---------------------------------------------------------------
    // ACTIVATE
    // ---------------------------------------------------------------
    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate an Airport")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Airport activated"),
            @ApiResponse(responseCode = "404", description = "Airport not found")
    })
    public ResponseEntity<Void> activateAirport(
            @Parameter(description = "Airport id", required = true)
            @PathVariable Long id) {
        airportService.activateAirport(id);
        return ResponseEntity.noContent().build();
    }

    // ---------------------------------------------------------------
    // DEACTIVATE
    // ---------------------------------------------------------------
    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate an Airport")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Airport deactivated"),
            @ApiResponse(responseCode = "404", description = "Airport not found")
    })
    public ResponseEntity<Void> deactivateAirport(
            @Parameter(description = "Airport id", required = true)
            @PathVariable Long id) {
        airportService.deactivateAirport(id);
        return ResponseEntity.noContent().build();
    }
}