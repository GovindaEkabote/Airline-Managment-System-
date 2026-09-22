package com.airline.controller;

import com.airline.playload.request.CityRequest;
import com.airline.playload.response.CityResponse;
import com.airline.service.CityService;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(
        value = "/api/v1/cities",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Cities",
        description = "Operations for managing airline city locations"
)
public class CityController {

    private final CityService cityService;

    // ============================================================
    // CREATE
    // ============================================================

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new city")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "City created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "City code already exists"
            )
    })
    public ResponseEntity<CityResponse> createCity(
            @Valid @RequestBody CityRequest request) {

        CityResponse created =
                cityService.createCity(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(created);
    }

    // ============================================================
    // BULK CREATE
    // ============================================================

    @PostMapping(
            value = "/bulk",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create multiple cities in one request")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Cities created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "One or more city codes already exist"
            )
    })
    public ResponseEntity<List<CityResponse>> bulkCreateCity(
            @Valid @RequestBody List<@Valid CityRequest> requests) {

        List<CityResponse> created =
                cityService.bulkCreateCity(requests);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    // ============================================================
    // READ ALL
    // ============================================================

    @GetMapping
    @Operation(
            summary = "List all cities",
            description = "Returns cities with pagination and sorting"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Page of cities"
    )
    public ResponseEntity<Page<CityResponse>> getAllCities(
            @PageableDefault(
                    size = 20,
                    sort = "id"
            )
            @Parameter(hidden = true)
            Pageable pageable) throws Exception {

        return ResponseEntity.ok(
                cityService.getAllCities(pageable)
        );
    }

    // ============================================================
    // READ BY ID
    // ============================================================

    @GetMapping("/{id}")
    @Operation(summary = "Get a city by its ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "City found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "City not found"
            )
    })
    public ResponseEntity<CityResponse> getCityById(
            @PathVariable Long id) throws Exception {

        return ResponseEntity.ok(
                cityService.getCityId(id)
        );
    }

    // ============================================================
    // SEARCH
    // ============================================================

    @GetMapping("/search")
    @Operation(summary = "Search cities by name keyword")
    @ApiResponse(
            responseCode = "200",
            description = "Matching cities"
    )
    public ResponseEntity<Page<CityResponse>> searchCities(
            @RequestParam String name,
            @PageableDefault(size = 20)
            @Parameter(hidden = true)
            Pageable pageable) throws Exception {

        return ResponseEntity.ok(
                cityService.searchCities(
                        name,
                        pageable
                )
        );
    }

    // ============================================================
    // FILTER BY COUNTRY
    // ============================================================

    @GetMapping("/country/{countryCode}")
    @Operation(
            summary = "Get cities by country code",
            description = "Example: IN, US, GB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Cities in country"
    )
    public ResponseEntity<Page<CityResponse>>
    getCitiesByCountryCode(
            @PathVariable String countryCode,
            @PageableDefault(size = 20)
            @Parameter(hidden = true)
            Pageable pageable) {

        return ResponseEntity.ok(
                cityService.getCitiesByCountryCode(
                        countryCode,
                        pageable
                )
        );
    }

    // ============================================================
    // FILTER BY ACTIVE
    // ============================================================

    @GetMapping("/active/{active}")
    @Operation(summary = "Get cities filtered by active flag")
    @ApiResponse(
            responseCode = "200",
            description = "Cities by active state"
    )
    public ResponseEntity<Page<CityResponse>>
    getCitiesByActive(
            @PathVariable Boolean active,
            @PageableDefault(size = 20)
            @Parameter(hidden = true)
            Pageable pageable) {

        return ResponseEntity.ok(
                cityService.getCitiesByActive(
                        active,
                        pageable
                )
        );
    }

    // ============================================================
    // CHECK EXISTS
    // ============================================================

    @GetMapping("/exists/{cityCode}")
    @Operation(
            summary = "Check whether a city code exists"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Existence result"
    )
    public ResponseEntity<Boolean> cityExists(
            @PathVariable String cityCode) {

        return ResponseEntity.ok(
                cityService.cityExists(cityCode)
        );
    }

    // ============================================================
    // UPDATE
    // ============================================================

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update a city",
            description = "Updates the provided city fields"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "City updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "City not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "City code conflict"
            )
    })
    public ResponseEntity<CityResponse> updateCity(
            @PathVariable Long id,
            @Valid @RequestBody CityRequest request) throws Exception {

        return ResponseEntity.ok(
                cityService.updateCity(
                        id,
                        request
                )
        );
    }

    // ============================================================
    // ACTIVATE
    // ============================================================

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate a city")
    @ApiResponse(
            responseCode = "204",
            description = "City activated"
    )
    public ResponseEntity<Void> activateCity(
            @PathVariable Long id) {

        cityService.activateCity(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    // ============================================================
    // DEACTIVATE
    // ============================================================

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate a city")
    @ApiResponse(
            responseCode = "204",
            description = "City deactivated"
    )
    public ResponseEntity<Void> deactivateCity(
            @PathVariable Long id) {

        cityService.deactivateCity(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    // ============================================================
    // DELETE
    // ============================================================

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a city by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "City deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "City not found"
            )
    })
    public ResponseEntity<Void> deleteCity(
            @PathVariable Long id) {

        cityService.deleteCity(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}

