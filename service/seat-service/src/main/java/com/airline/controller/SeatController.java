package com.airline.controller;

import com.airline.playload.request.SeatRequest;
import com.airline.playload.response.SeatResponse;
import com.airline.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seats")
@RequiredArgsConstructor
@Tag(
        name = "Seat Management",
        description = "APIs for generating, retrieving and updating airline seats"
)
public class SeatController {

    private final SeatService seatService;

    @Operation(
            summary = "Generate seats for a seat map",
            description = "Generates all seats based on the seat map configuration"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Seats generated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Seat map not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Seats already exist or seat map configuration is invalid"
            )
    })
    @PostMapping("/generate/{seatMapId}")
    public ResponseEntity<Void> generateSeats(
            @PathVariable Long seatMapId) {

        seatService.generateSeatsForSeatMap(seatMapId);

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Get all seats",
            description = "Returns all seats"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Seats retrieved successfully"
            )
    })
    @GetMapping
    public ResponseEntity<List<SeatResponse>> getAllSeats() {

        return ResponseEntity.ok(
                seatService.getAllSeats()
        );
    }

    @Operation(
            summary = "Update seat",
            description = "Updates seat configuration and pricing"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Seat updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Seat not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid seat data"
            )
    })
    @PutMapping("/{seatId}")
    public ResponseEntity<SeatResponse> updateSeat(
            @PathVariable Long seatId,
            @Valid @RequestBody SeatRequest request) {

        return ResponseEntity.ok(
                seatService.updateSeat(seatId, request)
        );
    }
}