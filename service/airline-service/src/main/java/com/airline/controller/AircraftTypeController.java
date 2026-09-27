package com.airline.controller;

import com.airline.enums.AircraftTypeStatus;
import com.airline.playload.request.AircraftTypeRequest;
import com.airline.playload.response.AircraftTypeResponse;
import com.airline.service.AircraftTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/v1/aircraft-types",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "AircraftType",
        description = "Operations for managing aircraft types"
)
public class AircraftTypeController {

    private final AircraftTypeService aircraftTypeService;


    // =========================================================
    // CREATE
    // =========================================================

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Create a new aircraft type",
            description = "Creates a new aircraft type with manufacturer, model, codes, engine and capacity details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Aircraft type created successfully"
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
    public ResponseEntity<AircraftTypeResponse> createAircraftType(
            @Valid @RequestBody AircraftTypeRequest request
    ) {

        log.info(
                "Creating aircraft type with IATA code: {} and ICAO code: {}",
                request.getIataTypeCode(),
                request.getIcaoTypeCode()
        );

        AircraftTypeResponse response =
                aircraftTypeService.createAircraftType(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // =========================================================
    // GET BY ID
    // =========================================================

    @GetMapping("/{id}")
    @Operation(
            summary = "Get aircraft type by ID",
            description = "Returns an aircraft type using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Aircraft type found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aircraft type not found"
            )
    })
    public ResponseEntity<AircraftTypeResponse> getAircraftTypeById(
            @Parameter(
                    description = "Aircraft type ID",
                    example = "1"
            )
            @PathVariable Long id
    ) {

        AircraftTypeResponse response =
                aircraftTypeService.getAircraftTypeById(id);

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // GET ALL - PAGINATED
    // =========================================================

    @GetMapping
    @Operation(
            summary = "Get all aircraft types",
            description = "Returns a paginated list of aircraft types."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Aircraft types retrieved successfully"
            )
    })
    public ResponseEntity<Page<AircraftTypeResponse>> getAllAircraftTypes(
            @PageableDefault(size = 20)
            Pageable pageable
    ) {

        Page<AircraftTypeResponse> response =
                aircraftTypeService.getAllAircraftTypes(pageable);

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // UPDATE
    // =========================================================

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update aircraft type",
            description = "Updates an existing aircraft type."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Aircraft type updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aircraft type not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "IATA or ICAO code already exists"
            )
    })
    public ResponseEntity<AircraftTypeResponse> updateAircraftType(
            @Parameter(
                    description = "Aircraft type ID",
                    example = "1"
            )
            @PathVariable Long id,

            @Valid @RequestBody AircraftTypeRequest request
    ) {

        AircraftTypeResponse response =
                aircraftTypeService.updateAircraftType(request, id);

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // DELETE
    // =========================================================

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete aircraft type",
            description = "Deletes an aircraft type using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Aircraft type deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aircraft type not found"
            )
    })
    public ResponseEntity<Void> deleteAircraftType(
            @Parameter(
                    description = "Aircraft type ID",
                    example = "1"
            )
            @PathVariable Long id
    ) {

        aircraftTypeService.deleteAircraftType(id);

        return ResponseEntity.noContent().build();
    }


    // =========================================================
    // GET BY ICAO CODE
    // =========================================================

    @GetMapping("/icao/{icaoTypeCode}")
    @Operation(
            summary = "Get aircraft type by ICAO code",
            description = "Returns an aircraft type using its ICAO aircraft type code."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Aircraft type found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aircraft type not found"
            )
    })
    public ResponseEntity<AircraftTypeResponse> getAircraftTypeByIcaoCode(
            @Parameter(
                    description = "ICAO aircraft type code",
                    example = "A320"
            )
            @PathVariable String icaoTypeCode
    ) {

        AircraftTypeResponse response =
                aircraftTypeService.getAircraftTypeByIcaoCode(
                        icaoTypeCode
                );

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // GET BY IATA CODE
    // =========================================================

    @GetMapping("/iata/{iataTypeCode}")
    @Operation(
            summary = "Get aircraft type by IATA code",
            description = "Returns an aircraft type using its IATA aircraft type code."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Aircraft type found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aircraft type not found"
            )
    })
    public ResponseEntity<AircraftTypeResponse> getAircraftTypeByIataCode(
            @Parameter(
                    description = "IATA aircraft type code",
                    example = "320"
            )
            @PathVariable String iataTypeCode
    ) {

        AircraftTypeResponse response =
                aircraftTypeService.getAircraftTypeByIataCode(
                        iataTypeCode
                );

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // GET BY MANUFACTURER
    // =========================================================

    @GetMapping("/manufacturer/{manufacturer}")
    @Operation(
            summary = "Get aircraft types by manufacturer",
            description = "Returns all aircraft types belonging to a manufacturer."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Aircraft types retrieved successfully"
            )
    })
    public ResponseEntity<List<AircraftTypeResponse>> getAircraftTypesByManufacturer(
            @Parameter(
                    description = "Aircraft manufacturer",
                    example = "Airbus"
            )
            @PathVariable String manufacturer
    ) {

        List<AircraftTypeResponse> response =
                aircraftTypeService.getAircraftTypesByManufacturer(
                        manufacturer
                );

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // GET BY STATUS
    // =========================================================

    @GetMapping("/status/{status}")
    @Operation(
            summary = "Get aircraft types by status",
            description = "Returns all aircraft types having the specified status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Aircraft types retrieved successfully"
            )
    })
    public ResponseEntity<List<AircraftTypeResponse>> getAircraftTypesByStatus(
            @Parameter(
                    description = "Aircraft type status",
                    example = "ACTIVE"
            )
            @PathVariable AircraftTypeStatus status
    ) {

        List<AircraftTypeResponse> response =
                aircraftTypeService.getAircraftTypesByStatus(status);

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // CHANGE STATUS
    // =========================================================

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Change aircraft type status",
            description = "Changes the status of an existing aircraft type."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Aircraft type status changed successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aircraft type not found"
            )
    })
    public ResponseEntity<AircraftTypeResponse> changeStatus(
            @Parameter(
                    description = "Aircraft type ID",
                    example = "1"
            )
            @PathVariable Long id,

            @Parameter(
                    description = "New aircraft type status",
                    example = "ACTIVE"
            )
            @RequestParam AircraftTypeStatus status
    ) {

        AircraftTypeResponse response =
                aircraftTypeService.changeStatus(id, status);

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // DROPDOWN
    // =========================================================

    @GetMapping("/dropdown")
    @Operation(
            summary = "Get aircraft type dropdown",
            description = "Returns aircraft types required for dropdown selection."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Aircraft type dropdown retrieved successfully"
            )
    })
    public ResponseEntity<List<AircraftTypeResponse>> getAircraftTypeDropdown() {

        List<AircraftTypeResponse> response =
                aircraftTypeService.getAircraftTypeDropdown();

        return ResponseEntity.ok(response);
    }
}
