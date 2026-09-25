package com.airline.controller;

import com.airline.playload.response.UpdateUserRequest;
import com.airline.playload.response.UserResponse;
import com.airline.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/api/v1/users",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "User",
        description = "Operations for managing users"
)
public class UserController {

    private final UserService userService;


    // =========================================================
    // GET USER BY ID
    // =========================================================

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by ID",
            description = "Fetch a user using their unique ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id
    ) {

        log.info("Fetching user with id: {}", id);

        return ResponseEntity.ok(
                userService.getUserById(id)
        );
    }


    // =========================================================
    // GET USER BY EMAIL
    // =========================================================

    @GetMapping("/email/{email}")
    @Operation(
            summary = "Get user by email",
            description = "Fetch a user using their email address"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    public ResponseEntity<UserResponse> getUserByEmail(
            @PathVariable String email
    ) {

        log.info("Fetching user with email: {}", email);

        return ResponseEntity.ok(
                userService.getUserByEmail(email)
        );
    }


    // =========================================================
    // GET ALL USERS
    // =========================================================

    @GetMapping
    @Operation(
            summary = "Get all users",
            description = "Get paginated list of users"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Users fetched successfully"
    )
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @Parameter(description = "Page number, starting from 0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of users per page")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Field used for sorting")
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(description = "Sort direction: asc or desc")
            @RequestParam(defaultValue = "asc") String direction
    ) {

        Sort.Direction sortDirection =
                direction.equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortDirection, sortBy)
        );

        return ResponseEntity.ok(
                userService.getAllUsers(pageable)
        );
    }


    // =========================================================
    // SEARCH USERS
    // =========================================================

    @GetMapping("/search")
    @Operation(
            summary = "Search users",
            description = "Search users by name, email or phone"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Search completed successfully"
    )
    public ResponseEntity<Page<UserResponse>> searchUsers(

            @RequestParam String query,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction
    ) {

        Sort.Direction sortDirection =
                direction.equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortDirection, sortBy)
        );

        return ResponseEntity.ok(
                userService.searchUsers(query, pageable)
        );
    }


    // =========================================================
    // UPDATE USER
    // =========================================================

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update user",
            description = "Update user information by ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email already registered"
            )
    })
    public ResponseEntity<UserResponse> updateUser(

            @PathVariable Long id,

            @Valid
            @RequestBody UpdateUserRequest request
    ) {

        log.info("Updating user with id: {}", id);

        return ResponseEntity.ok(
                userService.updateUser(id, request)
        );
    }


    // =========================================================
    // DELETE USER
    // =========================================================

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete user",
            description = "Delete user by ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "User deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    ) {

        log.info("Deleting user with id: {}", id);

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}