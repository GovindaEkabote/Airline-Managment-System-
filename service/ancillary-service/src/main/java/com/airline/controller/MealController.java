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
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create Meal",
            description = "Creates a new meal catalog item."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Meal created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Ancillary not found"),
            @ApiResponse(responseCode = "409", description = "Meal already exists")
    })
    public ResponseEntity<MealResponse> createMeal(
            @Valid @RequestBody MealRequest request,
            @RequestHeader("X-User-Id") Long userId
    ) {
        MealResponse response = mealService.createMeal(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update Meal",
            description = "Updates an existing meal catalog item."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meal updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Meal or ancillary not found")
    })
    public ResponseEntity<MealResponse> updateMeal(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id,
            @Valid @RequestBody MealRequest request
    ) throws AccessDeniedException {
        return ResponseEntity.ok(mealService.updateMeal(userId, id, request));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Meal By ID",
            description = "Retrieves a meal by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meal retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Meal not found")
    })
    public ResponseEntity<MealResponse> getMealById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(mealService.getMealById(id));
    }

    @GetMapping
    @Operation(
            summary = "Get All Meals",
            description = "Retrieves all meal catalog items."
    )
    @ApiResponse(responseCode = "200", description = "Meals retrieved successfully")
    public ResponseEntity<List<MealResponse>> getAllMeals() {
        return ResponseEntity.ok(mealService.getAllMeals());
    }

    @GetMapping("/airline/{airlineId}")
    @Operation(
            summary = "Get Meals By Airline",
            description = "Retrieves meals belonging to a specific airline."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meals retrieved successfully")
    })
    public ResponseEntity<List<MealResponse>> getMealsByAirlineId(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return ResponseEntity.ok(
                mealService.getMealsByAirlineId(userId)
        );
    }

    @GetMapping("/ancillary/{ancillaryId}")
    @Operation(
            summary = "Get Meals By Ancillary",
            description = "Retrieves meals associated with an ancillary catalog item."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meals retrieved successfully")
    })
    public ResponseEntity<List<MealResponse>> getMealsByAncillaryId(
            @RequestHeader("X-Ancillary-Id") Long ancillaryId
    ) {
        return ResponseEntity.ok(
                mealService.getMealsByAncillaryId(ancillaryId)
        );
    }

    @GetMapping("/airline/{airlineId}/available")
    @Operation(
            summary = "Get Available Meals",
            description = "Retrieves active and available meals for an airline."
    )
    @ApiResponse(responseCode = "200", description = "Available meals retrieved successfully")
    public ResponseEntity<List<MealResponse>> getAvailableMeals(
            @RequestHeader("X-Airline-Id")  Long airlineId
    ) {
        return ResponseEntity.ok(
                mealService.getAvailableMeals(airlineId)
        );
    }

    @PatchMapping("/{id}/activate")
    @Operation(
            summary = "Activate Meal",
            description = "Activates a meal catalog item."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Meal activated successfully"),
            @ApiResponse(responseCode = "404", description = "Meal not found")
    })
    public ResponseEntity<Void> activateMeal(
            @PathVariable Long id
    ) {
        mealService.activateMeal(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(
            summary = "Deactivate Meal",
            description = "Deactivates a meal catalog item."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Meal deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Meal not found")
    })
    public ResponseEntity<Void> deactivateMeal(
            @PathVariable Long id
    ) {
        mealService.deactivateMeal(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Meal",
            description = "Deletes a meal catalog item by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Meal deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Meal not found")
    })
    public ResponseEntity<Void> deleteMeal(
            @PathVariable Long id
    ) {
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }
}
