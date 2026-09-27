package com.airline.controller;

import com.airline.enums.AircraftStatus;
import com.airline.playload.request.AircraftRequest;
import com.airline.playload.response.AircraftResponse;
import com.airline.service.AircraftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/v1/aircraft",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Tag(
        name = "Aircraft",
        description = "Operations for managing aircraft"
)
public class AircraftController {

    private final AircraftService aircraftService;

    // =========================================================
    // CREATE
    // =========================================================

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Create aircraft",
            description = "Creates a new physical aircraft."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Aircraft created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aircraft type or airline not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Aircraft with the same registration, serial number or ICAO24 already exists"
            )
    })
    public ResponseEntity<AircraftResponse> saveAircraft(
            @Valid @RequestBody AircraftRequest request) {

        AircraftResponse response =
                aircraftService.saveAircraft(request);

        return ResponseEntity
                .status(201)
                .body(response);
    }

    // =========================================================
    // GET BY ID
    // =========================================================

    @GetMapping("/{id}")
    @Operation(
            summary = "Get aircraft by ID",
            description = "Retrieves an aircraft along with its aircraft type and airline information."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Aircraft retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aircraft not found"
            )
    })
    public ResponseEntity<AircraftResponse> getAircraftById(
            @Parameter(description = "Aircraft ID")
            @PathVariable Long id) {

        return ResponseEntity.ok(
                aircraftService.getAircraft(id)
        );
    }

    // =========================================================
    // GET ALL
    // =========================================================

    @GetMapping
    @Operation(
            summary = "Get all aircraft",
            description = "Retrieves paginated aircraft records."
    )
    public ResponseEntity<Page<AircraftResponse>> getAllAircraft(
            @PageableDefault(
                    size = 20,
                    sort = "id",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable) {

        return ResponseEntity.ok(
                aircraftService.getAllAircrafts(pageable)
        );
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update aircraft",
            description = "Updates an existing aircraft."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Aircraft updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aircraft, aircraft type or airline not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Duplicate aircraft information"
            )
    })
    public ResponseEntity<AircraftResponse> updateAircraft(
            @PathVariable Long id,
            @Valid @RequestBody AircraftRequest request) {

        return ResponseEntity.ok(
                aircraftService.updateAircraft(request, id)
        );
    }

    // =========================================================
    // DELETE
    // =========================================================

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete aircraft",
            description = "Deletes an aircraft by ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Aircraft deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aircraft not found"
            )
    })
    public ResponseEntity<Void> deleteAircraft(
            @PathVariable Long id) {

        aircraftService.deleteAircraft(id);

        return ResponseEntity.noContent().build();
    }

    // =========================================================
    // GET BY REGISTRATION NUMBER
    // =========================================================

    @GetMapping("/registration/{registrationNumber}")
    @Operation(
            summary = "Get aircraft by registration number"
    )
    public ResponseEntity<AircraftResponse> getByRegistrationNumber(
            @PathVariable String registrationNumber) {

        return ResponseEntity.ok(
                aircraftService
                        .getAircraftByRegistrationNumber(registrationNumber)
        );
    }

    // =========================================================
    // GET BY SERIAL NUMBER
    // =========================================================

    @GetMapping("/serial/{serialNumber}")
    @Operation(
            summary = "Get aircraft by serial number"
    )
    public ResponseEntity<AircraftResponse> getBySerialNumber(
            @PathVariable String serialNumber) {

        return ResponseEntity.ok(
                aircraftService
                        .getAircraftBySerialNumber(serialNumber)
        );
    }

    // =========================================================
    // GET BY ICAO24
    // =========================================================

    @GetMapping("/icao24/{icao24}")
    @Operation(
            summary = "Get aircraft by ICAO24"
    )
    public ResponseEntity<AircraftResponse> getByIcao24(
            @PathVariable String icao24) {

        return ResponseEntity.ok(
                aircraftService.getAircraftByIcao24(icao24)
        );
    }

    // =========================================================
    // GET BY AIRLINE
    // =========================================================

    @GetMapping("/airline/{airlineId}")
    @Operation(
            summary = "Get aircraft by airline"
    )
    public ResponseEntity<Page<AircraftResponse>> getByAirline(
            @PathVariable Long airlineId,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {

        return ResponseEntity.ok(
                aircraftService
                        .getAircraftsByAirlineId(
                                airlineId,
                                pageable
                        )
        );
    }

    // =========================================================
    // GET BY AIRCRAFT TYPE
    // =========================================================

    @GetMapping("/type/{aircraftTypeId}")
    @Operation(
            summary = "Get aircraft by aircraft type"
    )
    public ResponseEntity<Page<AircraftResponse>> getByAircraftType(
            @PathVariable Long aircraftTypeId,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {

        return ResponseEntity.ok(
                aircraftService
                        .getAircraftsByAircraftTypeId(
                                aircraftTypeId,
                                pageable
                        )
        );
    }

    // =========================================================
    // GET BY STATUS
    // =========================================================

    @GetMapping("/status/{status}")
    @Operation(
            summary = "Get aircraft by status"
    )
    public ResponseEntity<Page<AircraftResponse>> getByStatus(
            @PathVariable AircraftStatus status,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {

        return ResponseEntity.ok(
                aircraftService
                        .getAircraftsByStatus(
                                status,
                                pageable
                        )
        );
    }

    // =========================================================
    // AIRLINE + STATUS
    // =========================================================

    @GetMapping("/airline/{airlineId}/status/{status}")
    @Operation(
            summary = "Get aircraft by airline and status"
    )
    public ResponseEntity<List<AircraftResponse>> getByAirlineAndStatus(
            @PathVariable Long airlineId,
            @PathVariable AircraftStatus status) {

        return ResponseEntity.ok(
                aircraftService
                        .getAircraftsByAirlineIdAndStatus(
                                airlineId,
                                status
                        )
        );
    }

    // =========================================================
    // AVAILABLE AIRCRAFT
    // =========================================================

    @GetMapping("/available")
    @Operation(
            summary = "Get available aircraft"
    )
    public ResponseEntity<List<AircraftResponse>> getAvailableAircrafts() {

        return ResponseEntity.ok(
                aircraftService.getAvailableAircrafts()
        );
    }

    // =========================================================
    // AVAILABLE BY AIRLINE
    // =========================================================

    @GetMapping("/airline/{airlineId}/available")
    @Operation(
            summary = "Get available aircraft by airline"
    )
    public ResponseEntity<List<AircraftResponse>>
    getAvailableAircraftsByAirline(
            @PathVariable Long airlineId) {

        return ResponseEntity.ok(
                aircraftService
                        .getAvailableAircraftsByAirlineId(airlineId)
        );
    }

    // =========================================================
    // CHANGE STATUS
    // =========================================================

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Change aircraft status"
    )
    public ResponseEntity<AircraftResponse> changeStatus(
            @PathVariable Long id,
            @RequestParam AircraftStatus status) {

        return ResponseEntity.ok(
                aircraftService.changeStatus(id, status)
        );
    }

    // =========================================================
    // CHANGE AVAILABILITY
    // =========================================================

    @PatchMapping("/{id}/availability")
    @Operation(
            summary = "Change aircraft availability"
    )
    public ResponseEntity<AircraftResponse> changeAvailability(
            @PathVariable Long id,
            @RequestParam Boolean available) {

        return ResponseEntity.ok(
                aircraftService.changeAvailability(
                        id,
                        available
                )
        );
    }

    // =========================================================
    // DROPDOWN
    // =========================================================

    @GetMapping("/dropdown")
    @Operation(
            summary = "Get aircraft dropdown"
    )
    public ResponseEntity<List<AircraftResponse>> getAircraftDropdown() {

        return ResponseEntity.ok(
                aircraftService.getAircraftDropdown()
        );
    }
}