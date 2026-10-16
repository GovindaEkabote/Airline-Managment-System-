
package com.airline.controller;

import com.airline.enums.AncillaryType;

import com.airline.playload.request.FlightCabinAncillaryRequest;
import com.airline.playload.response.FlightCabinAncillaryResponse;
import com.airline.service.FlightCabinAncillaryService;
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
        value = "/api/v1/flight/cabin/ancillary",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Tag(
        name = "Flight Cabin Ancillary",
        description = "Operations for managing flight cabin ancillary services"
)
public class FlightCabinAncillaryController {

    private final FlightCabinAncillaryService flightCabinAncillaryService;

    @PostMapping(
            value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create Flight Cabin Ancillary")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Related resource not found"),
            @ApiResponse(responseCode = "409", description = "Resource already exists")
    })
    public ResponseEntity<FlightCabinAncillaryResponse> create(
            @Valid @RequestBody FlightCabinAncillaryRequest request) {

        FlightCabinAncillaryResponse response =
                flightCabinAncillaryService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Flight Cabin Ancillary by ID")
    public ResponseEntity<FlightCabinAncillaryResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                flightCabinAncillaryService.getById(id)
        );
    }

    @GetMapping("/flight/{flightId}/cabin-class/{cabinClassId}")
    @Operation(summary = "Get Ancillaries by Flight and Cabin Class")
    public ResponseEntity<List<FlightCabinAncillaryResponse>> getByFlightAndCabinClass(
            @PathVariable Long flightId,
            @PathVariable Long cabinClassId) {

        return ResponseEntity.ok(
                flightCabinAncillaryService.getByFlightAndCabinClass(
                        flightId, cabinClassId
                )
        );
    }

    @PostMapping(
            value = "/by-ids",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get Flight Cabin Ancillaries by IDs")
    public ResponseEntity<List<FlightCabinAncillaryResponse>> getAllByIds(
            @RequestBody List<Long> ids) {

        return ResponseEntity.ok(
                flightCabinAncillaryService.getAllByIds(ids)
        );
    }

    @GetMapping("/flight/{flightId}/cabin-class/{cabinClassId}/type/{ancillaryType}")
    @Operation(summary = "Get Ancillary by Flight, Cabin Class and Type")
    public ResponseEntity<FlightCabinAncillaryResponse>
    getByFlightIdAndCabinClassAndType(
            @PathVariable Long flightId,
            @PathVariable Long cabinClassId,
            @PathVariable AncillaryType ancillaryType) {

        return ResponseEntity.ok(
                flightCabinAncillaryService
                        .getByFlightIdAndCabinClassAndtype(
                                flightId, cabinClassId, ancillaryType
                        )
        );
    }

    @GetMapping("/flight/{flightId}/cabin-class/{cabinClassId}/type/{ancillaryType}/all")
    @Operation(summary = "Get All Ancillaries by Flight, Cabin Class and Type")
    public ResponseEntity<List<FlightCabinAncillaryResponse>>
    getAllByFlightIdAndCabinClassIdAndType(
            @PathVariable Long flightId,
            @PathVariable Long cabinClassId,
            @PathVariable AncillaryType ancillaryType) {

        return ResponseEntity.ok(
                flightCabinAncillaryService
                        .getAllByFlightIdAndCabinClassIdAndType(
                                flightId, cabinClassId, ancillaryType
                        )
        );
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update Flight Cabin Ancillary")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "409", description = "Duplicate resource")
    })
    public ResponseEntity<FlightCabinAncillaryResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody FlightCabinAncillaryRequest request) {

        return ResponseEntity.ok(
                flightCabinAncillaryService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Flight Cabin Ancillary")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        flightCabinAncillaryService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/calculate-price",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Calculate Total Ancillary Price")
    public ResponseEntity<Double> calculateAncillaryPrice(
            @RequestBody List<Long> ancillaryIds) {

        return ResponseEntity.ok(
                flightCabinAncillaryService.calculateAncillaryPrice(
                        ancillaryIds
                )
        );
    }
}
