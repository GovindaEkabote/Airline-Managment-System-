
package com.airline.controller;

import com.airline.playload.request.AncillaryRequest;
import com.airline.playload.response.AncillaryResponse;
import com.airline.service.AncillaryService;
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
        value = "/api/v1/ancillaries",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Tag(
        name = "Ancillary",
        description = "Operations for managing airline ancillary services"
)
public class AncillaryController {

    private final AncillaryService ancillaryService;

    @PostMapping(
            value = "/airline/{userId}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Create ancillary",
            description = "Creates a new ancillary service for an airline."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Ancillary created successfully"),
            @ApiResponse(responseCode = "400",
                    description = "Validation failed"),
            @ApiResponse(responseCode = "404",
                    description = "Airline not found"),
            @ApiResponse(responseCode = "409",
                    description = "Ancillary already exists")
    })
    public ResponseEntity<AncillaryResponse> createAncillary(
            @RequestHeader("X-User-id") Long userId,
            @Valid @RequestBody AncillaryRequest request) {

        AncillaryResponse response =
                ancillaryService.createAncillary(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get ancillary by ID",
            description = "Retrieves an ancillary service by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Ancillary retrieved successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Ancillary not found")
    })
    public ResponseEntity<AncillaryResponse> getAncillaryById(
            @PathVariable Long id) throws Exception {

        return ResponseEntity.ok(ancillaryService.getbyId(id));
    }

    @GetMapping("/airline/{userId}")
    @Operation(
            summary = "Get ancillaries by airline",
            description = "Retrieves all ancillary services belonging to an airline."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Ancillaries retrieved successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Airline not found")
    })
    public ResponseEntity<List<AncillaryResponse>> getAncillariesByAirline(
            @RequestHeader("X-User-Id") Long userId) {

        return ResponseEntity.ok(
                ancillaryService.getAirlineId(userId));
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update ancillary",
            description = "Updates an existing ancillary service."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Ancillary updated successfully"),
            @ApiResponse(responseCode = "400",
                    description = "Validation failed"),
            @ApiResponse(responseCode = "404",
                    description = "Ancillary not found")
    })
    public ResponseEntity<AncillaryResponse> updateAncillary(
            @PathVariable Long id,
            @Valid @RequestBody AncillaryRequest request) {

        return ResponseEntity.ok(
                ancillaryService.updateAncillary(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete ancillary",
            description = "Deletes an existing ancillary service."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Ancillary deleted successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Ancillary not found")
    })
    public ResponseEntity<Void> deleteAncillary(
            @PathVariable Long id) {

        ancillaryService.deleteAncillary(id);

        return ResponseEntity.noContent().build();
    }
}
