package com.airline.controller;


import com.airline.playload.request.FareRolesRequest;
import com.airline.playload.response.FareRuleResponse;
import com.airline.service.FareRolesService;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping(
        value = "/api/v1/fares/roles",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Fare Roles Management",
        description = "Operations for managing fare roles"
)
public class FareRolesController {

    private final FareRolesService fareRolesService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new Fare Roles")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Fare roles created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Fare not found"),
            @ApiResponse(responseCode = "409", description = "Fare roles already exist")
    })
    public ResponseEntity<FareRuleResponse> createFareRoles(
            @Valid @RequestBody FareRolesRequest fareRolesRequest) {
        log.info("Creating fare roles: {}", fareRolesRequest);
        FareRuleResponse created = fareRolesService.createFareRoles(fareRolesRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Fare Roles by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fare roles retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Fare roles not found")
    })
    public ResponseEntity<FareRuleResponse> getFareRolesById(@PathVariable Long id) {
        log.info("Fetching fare roles with id: {}", id);
        FareRuleResponse fareRoles = fareRolesService.getFareRolesById(id);
        return ResponseEntity.ok(fareRoles);
    }
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update Fare Roles by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fare roles updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Fare roles not found")
    })
    public ResponseEntity<FareRuleResponse> updateFareRoles(@PathVariable Long id, @Valid @RequestBody FareRolesRequest fareRolesRequest) {
        log.info("Updating fare roles with id: {}", id);
        FareRuleResponse updated = fareRolesService.updateFareRoles(id, fareRolesRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Fare Roles by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Fare roles deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Fare roles not found")
    })
    public ResponseEntity<Void> deleteFareRoles(@PathVariable Long id) {
        log.info("Deleting fare roles with id: {}", id);
        fareRolesService.deleteFareRoles(id);
        return ResponseEntity.noContent().build();
    }

}
