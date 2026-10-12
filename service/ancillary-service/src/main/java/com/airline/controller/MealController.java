package com.airline.controller;

import com.airline.playload.request.MealRequest;
import com.airline.playload.response.MealResponse;
import com.airline.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/api/v1/meal",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Tag(
        name = "Meal",
        description = "Operations for managing airline meal services"
)
public class MealController {

    private final MealService mealService;

    @PostMapping(
            value = "/meal",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Create Meal",
            description = "Creates a new ancillary service for an Meal."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Meal created successfully"),
            @ApiResponse(responseCode = "400",
                    description = "Validation failed"),
            @ApiResponse(responseCode = "404",
                    description = "Meal not found"),
            @ApiResponse(responseCode = "409",
                    description = "Meal already exists")
    })
    public ResponseEntity<MealResponse> createMeal(
            @Valid @RequestBody MealRequest request
            ){
        MealResponse response = mealService.createMeal(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
