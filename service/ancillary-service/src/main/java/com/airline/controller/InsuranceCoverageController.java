package com.airline.controller;

import com.airline.playload.request.InsuranceCoverageRequest;
import com.airline.playload.response.InsuranceConverageResponse;
import com.airline.service.InsuranceCoverageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/v1/insurance/coverages",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Tag(
        name = "Insurance Coverage",
        description = "Operations for managing airline insurance coverages"
)
public class InsuranceCoverageController {

    private final InsuranceCoverageService insuranceCoverageService;

    // CREATE
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create insurance coverage",
            description = "Creates a new insurance coverage for an ancillary product."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Coverage created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Ancillary product not found")
    })
    public ResponseEntity<InsuranceConverageResponse> createCoverage(
            @Valid @RequestBody InsuranceCoverageRequest request) {

        InsuranceConverageResponse response =
                insuranceCoverageService.createCoverage(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Get insurance coverage by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Coverage retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Coverage not found")
    })
    public ResponseEntity<InsuranceConverageResponse> getCoverageById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                insuranceCoverageService.getCoverageById(id)
        );
    }

    // GET ALL
    @GetMapping
    @Operation(summary = "Get all insurance coverages")
    @ApiResponse(responseCode = "200", description = "Coverage list retrieved successfully")
    public ResponseEntity<List<InsuranceConverageResponse>> getAllCoverages() {

        return ResponseEntity.ok(
                insuranceCoverageService.getAllCoverages()
        );
    }

    // GET BY ANCILLARY ID
    @GetMapping("/ancillary/{ancillaryId}")
    @Operation(summary = "Get coverages by ancillary ID")
    @ApiResponse(responseCode = "200", description = "Coverage list retrieved successfully")
    public ResponseEntity<List<InsuranceConverageResponse>> getCoveragesByAncillaryId(
            @PathVariable Long ancillaryId) {

        return ResponseEntity.ok(
                insuranceCoverageService.getCoveragesByAncillaryId(ancillaryId)
        );
    }

    // GET ACTIVE COVERAGES BY ANCILLARY ID
    @GetMapping("/ancillary/{ancillaryId}/active")
    @Operation(summary = "Get active coverages by ancillary ID")
    @ApiResponse(responseCode = "200", description = "Active coverage list retrieved successfully")
    public ResponseEntity<List<InsuranceConverageResponse>> getActiveCoverages(
            @PathVariable Long ancillaryId) {

        return ResponseEntity.ok(
                insuranceCoverageService.getActiveCoverage(ancillaryId)
        );
    }

    // UPDATE
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update insurance coverage")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Coverage updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Coverage or ancillary product not found")
    })
    public ResponseEntity<InsuranceConverageResponse> updateCoverage(
            @PathVariable Long id,
            @Valid @RequestBody InsuranceCoverageRequest request) {

        return ResponseEntity.ok(
                insuranceCoverageService.updateCoverage(id, request)
        );
    }

    // DEACTIVATE
    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate insurance coverage")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Coverage deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Coverage not found")
    })
    public ResponseEntity<Void> deactivateCoverage(
            @PathVariable Long id) throws Exception {

        insuranceCoverageService.deactivateCoverage(id);

        return ResponseEntity.noContent().build();
    }

    // ACTIVATE
    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate insurance coverage")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Coverage activated successfully"),
            @ApiResponse(responseCode = "404", description = "Coverage not found")
    })
    public ResponseEntity<Void> activateCoverage(
            @PathVariable Long id) throws Exception {

        insuranceCoverageService.activateCoverage(id);

        return ResponseEntity.noContent().build();
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete insurance coverage",
            description = "Permanently deletes an insurance coverage."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Coverage deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Coverage not found")
    })
    public ResponseEntity<Void> deleteCoverage(
            @PathVariable Long id) {

        insuranceCoverageService.deleteCoverage(id);

        return ResponseEntity.noContent().build();
    }
}
