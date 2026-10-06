package com.airline.controller;


import com.airline.playload.request.SeatMapRequest;
import com.airline.playload.response.SeatMapResponse;
import com.airline.service.SeatMapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/api/v1/seat-map",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Tag(
        name = "Seat Map",
        description = "Operations for managing seat maps"
)
public class SeatMapController {

    private final SeatMapService seatMapService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Create seat map",
            description = "Creates a new seat map for a specific airline and cabin class."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Seat map created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cabin class not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Seat map already exists for the given airline and cabin class"
            )
    })
    public ResponseEntity<SeatMapResponse> createSeatMap(
            @RequestHeader("X-Airline-Id") Long airlineId,
            @Valid @RequestBody SeatMapRequest request
    ) throws Exception {
        SeatMapResponse seatMapResponse = seatMapService.create(airlineId, request);
        return ResponseEntity
                .status(201)
                .body(seatMapResponse);
    }

    @GetMapping(
            value = "/{id}"
    )
    @Operation(
            summary = "Get seat map",
            description = "Retrieves the seat map for a specific airline and cabin class."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Seat map retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Seat map not found"
            )
    })
    public ResponseEntity<SeatMapResponse> getSeatMap(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(seatMapService.getSeatMapById(id));
    }

    // getSeatMapCabinClass
    @GetMapping(
            value = "/cabin-class/{cabinClassId}"
    )
    @Operation(
            summary = "Get seat map by cabin class",
            description = "Retrieves the seat map for a specific cabin class."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Seat map retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Seat map not found"
            )
    })
    public ResponseEntity<SeatMapResponse> getSeatMapByCabinClass(
            @PathVariable Long cabinClassId
    ) {
        return ResponseEntity.ok(seatMapService.getSeatMapCabinClass(cabinClassId));
    }

//    updateSeatMap
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update seat map",
            description = "Updates an existing seat map for a specific airline and cabin class."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Seat map updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Seat map not found"
            )
    })
    public ResponseEntity<SeatMapResponse> updateSeatMap(
            @PathVariable Long id,
            @Valid @RequestBody SeatMapRequest request
    ) {
        SeatMapResponse seatMapResponse = seatMapService.updateSeatMap(id, request);
        return ResponseEntity.ok(seatMapResponse);
    }

    // deleteSeatMap
    @DeleteMapping(
            value = "/{id}"
    )
    @Operation(
            summary = "Delete seat map",
            description = "Deletes an existing seat map for a specific airline and cabin class."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Seat map deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Seat map not found"
            )
    })
    public ResponseEntity<ApiResponse> deleteSeatMap(
            @PathVariable Long id
    ) {
        seatMapService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
