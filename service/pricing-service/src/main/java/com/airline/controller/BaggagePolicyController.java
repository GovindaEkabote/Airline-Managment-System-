package com.airline.controller;

import com.airline.playload.request.BaggagePolicyRequest;
import com.airline.playload.response.BaggagePolicyResponse;
import com.airline.service.BaggagePolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/v1/baggage-policies",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Baggage Policy Management",
        description = "APIs for creating, retrieving, updating and deleting baggage policies"
)
public class BaggagePolicyController {

    private final BaggagePolicyService baggagePolicyService;

    /**
     * Create a new baggage policy.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create baggage policy",
            description = "Creates a new baggage policy based on the supplied request"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Baggage policy created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request or validation failed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Required fare or airline not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Baggage policy already exists"
            )
    })
    public ResponseEntity<BaggagePolicyResponse> createBaggagePolicy(
            @Valid @RequestBody BaggagePolicyRequest request
    ) {
        log.info("Creating baggage policy");

        BaggagePolicyResponse response =
                baggagePolicyService.calculateBaggagePolicy(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Get baggage policy by ID.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get baggage policy by ID",
            description = "Retrieves a baggage policy using its unique identifier"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Baggage policy retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid baggage policy ID"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Baggage policy not found"
            )
    })
    public ResponseEntity<BaggagePolicyResponse> getBaggagePolicyById(
            @Parameter(
                    description = "Unique baggage policy ID",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        log.info("Fetching baggage policy with id={}", id);

        BaggagePolicyResponse response =
                baggagePolicyService.getBaggagePolicyById(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Update an existing baggage policy.
     */
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update baggage policy",
            description = "Updates an existing baggage policy"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Baggage policy updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request or validation failed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Baggage policy not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Baggage policy already exists"
            )
    })
    public ResponseEntity<BaggagePolicyResponse> updateBaggagePolicy(
            @Parameter(
                    description = "Unique baggage policy ID",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,

            @Valid @RequestBody BaggagePolicyRequest request
    ) {
        log.info("Updating baggage policy with id={}", id);

        BaggagePolicyResponse response =
                baggagePolicyService.updateBaggagePolicy(id, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete baggage policy by ID.
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete baggage policy",
            description = "Deletes an existing baggage policy by its unique identifier"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Baggage policy deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid baggage policy ID"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Baggage policy not found"
            )
    })
    public ResponseEntity<Void> deleteBaggagePolicy(
            @Parameter(
                    description = "Unique baggage policy ID",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        log.info("Deleting baggage policy with id={}", id);

        baggagePolicyService.deleteBaggagePolicy(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Get all baggage policies for an airline.
     */
    @GetMapping("/airline/{airlineId}")
    @Operation(
            summary = "Get baggage policies by airline",
            description = "Retrieves all baggage policies associated with an airline"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Baggage policies retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid airline ID"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Airline not found"
            )
    })
    public ResponseEntity<List<BaggagePolicyResponse>> getBaggagePoliciesByAirlineId(
            @Parameter(
                    description = "Unique airline ID",
                    example = "1",
                    required = true
            )
            @PathVariable Long airlineId
    ) {
        log.info("Fetching baggage policies for airlineId={}", airlineId);

        List<BaggagePolicyResponse> responses =
                baggagePolicyService.getBaggagePoliciesByAirlineId(airlineId);

        return ResponseEntity.ok(responses);
    }
}
