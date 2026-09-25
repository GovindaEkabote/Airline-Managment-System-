package com.airline.controller;

import com.airline.playload.response.AuthResponse;
import com.airline.playload.response.LoginRequest;
import com.airline.playload.response.SignupRequest;
import com.airline.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping(
        value = "/api/v1/auth",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Authentication",
        description = "Operations for managing user authentication"
)
public class AuthController {

    private final AuthService authService;

    // Login
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Signup",
            description = "Authenticate user and generate JWT"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User Signup in successfully"
    )
    public ResponseEntity<AuthResponse> signup(
            @RequestBody @Valid SignupRequest request
    ) {
        AuthResponse authResponse = authService.signup(request);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    @Operation(
            summary = "login",
            description = "Authenticate user and generate JWT"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User logged in successfully"
    )
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid LoginRequest request
    ) throws Exception{
        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(authResponse);
    }
}
