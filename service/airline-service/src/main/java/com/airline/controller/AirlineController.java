package com.airline.controller;

import com.airline.enums.AirlineStatus;
import com.airline.playload.request.AirlineRequest;
import com.airline.playload.response.AirlineDropdownItem;
import com.airline.playload.response.AirlineResponse;
import com.airline.service.AirlineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/v1/airlines",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Airline",
        description = "Operations for managing airlines"
)
public class AirlineController {

    private final AirlineService airlineService;

    // =========================================================
    // CREATE
    // =========================================================

    @PostMapping
    @Operation(summary = "Create a new airline")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Airline created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "IATA or ICAO code already exists"
            )
    })
    public ResponseEntity<AirlineResponse> createAirline(
            @Valid @RequestBody AirlineRequest request,
            @RequestParam Long ownerId) {

        AirlineResponse response =
                airlineService.createAirline(request, ownerId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // =========================================================
    // GET BY ID
    // =========================================================

    @GetMapping("/{id}")
    @Operation(summary = "Get airline by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Airline found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Airline not found"
            )
    })
    public ResponseEntity<AirlineResponse> getAirlineById(
            @PathVariable Long id) {

        AirlineResponse response =
                airlineService.getAirlineById(id);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // GET BY OWNER
    // =========================================================

    @GetMapping("/owner/{ownerId}")
    @Operation(summary = "Get airline by owner ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Airline found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Airline not found"
            )
    })
    public ResponseEntity<AirlineResponse> getAirlineByOwnerId(
            @PathVariable Long ownerId) {

        AirlineResponse response =
                airlineService.getAirlineByOwnerId(ownerId);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // GET BY IATA
    // =========================================================

    @GetMapping("/iata/{iataCode}")
    @Operation(summary = "Get airline by IATA code")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Airline found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Airline not found"
            )
    })
    public ResponseEntity<AirlineResponse> getAirlineByIataCode(
            @PathVariable String iataCode) {

        AirlineResponse response =
                airlineService.getAirlineByIataCode(iataCode);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // GET BY ICAO
    // =========================================================

    @GetMapping("/icao/{icaoCode}")
    @Operation(summary = "Get airline by ICAO code")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Airline found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Airline not found"
            )
    })
    public ResponseEntity<AirlineResponse> getAirlineByIcaoCode(
            @PathVariable String icaoCode) {

        AirlineResponse response =
                airlineService.getAirlineByIcaoCode(icaoCode);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // GET ALL - PAGINATION
    // =========================================================

    @GetMapping
    @Operation(summary = "Get all airlines")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Airlines retrieved successfully"
            )
    })
    public ResponseEntity<Page<AirlineResponse>> getAllAirlines(
            Pageable pageable) {

        Page<AirlineResponse> response =
                airlineService.getAllAirlines(pageable);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @PutMapping("/{id}")
    @Operation(summary = "Update airline")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Airline updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Airline not found"
            )
    })
    public ResponseEntity<AirlineResponse> updateAirline(
            @PathVariable Long id,
            @Valid @RequestBody AirlineRequest request) {

        AirlineResponse response =
                airlineService.updateAirline(request, id);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // DELETE
    // =========================================================

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete airline")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Airline deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Airline not found"
            )
    })
    public ResponseEntity<Void> deleteAirline(
            @PathVariable Long id) {

        airlineService.deleteAirline(id);

        return ResponseEntity.noContent().build();
    }

    // =========================================================
    // CHANGE STATUS - ADMIN
    // =========================================================

    @PatchMapping("/{airlineId}/status")
    @Operation(summary = "Change airline status")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Airline status updated"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Airline not found"
            )
    })
    public ResponseEntity<AirlineResponse> changeStatusByAdmin(
            @PathVariable Long airlineId,
            @RequestParam AirlineStatus status) {

        AirlineResponse response =
                airlineService.changeStatusByAdmin(
                        airlineId,
                        status
                );

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // DROPDOWN
    // =========================================================

    @GetMapping("/dropdown")
    @Operation(summary = "Get active airlines for dropdown")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Airline dropdown retrieved successfully"
            )
    })
    public ResponseEntity<List<AirlineDropdownItem>> getAirlineDropdown() {

        List<AirlineDropdownItem> response =
                airlineService.getAirlineDropdown();

        return ResponseEntity.ok(response);
    }
}