package com.airline.controller;

import com.airline.model.FlightInstanceCabin;
import com.airline.playload.request.FlightInstanceCabinRequest;
import com.airline.playload.response.FlightInstanceCabinResponse;
import com.airline.service.FlightInstanceCabinService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/api/v1/flight/instance/cabin",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Tag(
        name = "Flight Instance Cabin Class",
        description = "Operations for managing Flight Instance Cabin Class"
)
public class FlightInstanceCabinController {

    private final FlightInstanceCabinService flightInstanceCabinService;

    @PostMapping("/create")
    public ResponseEntity<FlightInstanceCabinResponse> createFlightInstanceCabin(
            @Valid @RequestBody FlightInstanceCabinRequest request
            ){
        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(flightInstanceCabinService.createFlightInstanceCabin(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightInstanceCabinResponse> getFlightInstanceCabinById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(flightInstanceCabinService.getFlightInstanceCabinById(id));
    }

    @GetMapping("/flight/instance/{id}")
    public ResponseEntity<Page<FlightInstanceCabinResponse>> getFlightInstance(
            @PathVariable Long id,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                flightInstanceCabinService.getFlightInstance(id, pageable)
        );
    }

    @GetMapping("/flight-instance/{flightInstanceId}/cabin-class/{cabinClassId}")
    public ResponseEntity<FlightInstanceCabinResponse> getByFlightInstanceIdAndCabinClassId(
            @PathVariable Long cabinClassId,
            @PathVariable Long flightInstanceId
    ){
        return ResponseEntity.ok(
                flightInstanceCabinService.getByFlightInstanceIdAndCabinClassId(
                        flightInstanceId,cabinClassId
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightInstanceCabinResponse> updateFlightInstanceCabin(
            @PathVariable Long id,
            @Valid @RequestBody FlightInstanceCabinRequest request
    ){
        return ResponseEntity.ok(
                flightInstanceCabinService.updateFlightInstanceCabin(
                        id, request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FlightInstanceCabinResponse> deleteFlightInstanceCabin(
            @PathVariable Long id
    ){
        flightInstanceCabinService.deleteFlightInstanceCabin(id);
        return ResponseEntity.noContent().build();
    }
}
