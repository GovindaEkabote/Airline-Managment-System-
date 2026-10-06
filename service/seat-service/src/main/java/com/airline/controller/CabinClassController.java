package com.airline.controller;

import com.airline.enums.CabinClassType;
import com.airline.model.CabinClass;
import com.airline.playload.request.CabinClassRequest;
import com.airline.playload.response.CabinClassResponse;
import com.airline.service.CabinClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/v1/cabins",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Tag(
        name = "Cabin Class",
        description = "Operations for managing cabin classes"
)
public class CabinClassController {

    private final CabinClassService cabinClassService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Create cabin",
            description = "Creates a new cabin class."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Cabin created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cabin type or aircraft not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Cabin already exists"
            )
    })
    public ResponseEntity<CabinClassResponse> create(
            @Valid @RequestBody CabinClassRequest request
    ) {
        CabinClassResponse cabinClassResponse =
                cabinClassService.create(request);

        return ResponseEntity
                .status(201)
                .body(cabinClassResponse);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get cabin by ID",
            description = "Retrieves a cabin class by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cabin retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cabin not found"
            )
    })
    public ResponseEntity<CabinClassResponse> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                cabinClassService.getById(id)
        );
    }

    @GetMapping("/aircraft/{aircraftId}")
    @Operation(
            summary = "Get cabins by aircraft ID",
            description = "Retrieves all active cabin classes belonging to an aircraft."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cabins retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aircraft or cabins not found"
            )
    })
    public ResponseEntity<List<CabinClassResponse>> findByAircraftId(
            @PathVariable Long aircraftId
    ) {
        return ResponseEntity.ok(
                cabinClassService.getAircraftById(aircraftId)
        );
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update cabin",
            description = "Updates an existing cabin class."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cabin updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cabin, aircraft, or cabin type not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Duplicate cabin information"
            )
    })
    public ResponseEntity<CabinClassResponse> updateCabinClass(
            @PathVariable Long id,
            @Valid @RequestBody CabinClassRequest request
    ) {
        return ResponseEntity.ok(
                cabinClassService.update(id, request)
        );
    }

    @PutMapping("/{id}/status")
    @Operation(
            summary = "Update cabin status",
            description = "Activates or deactivates a cabin class."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cabin status updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid cabin status"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cabin not found"
            )
    })
    public ResponseEntity<CabinClassResponse> setActive(
            @PathVariable Long id,
            @RequestParam boolean active
            ) {
        return ResponseEntity.ok(
                cabinClassService.setActive(id, active)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete cabin",
            description = "Deletes a cabin class by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Cabin deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cabin class not found"
            )
    })
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        cabinClassService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
