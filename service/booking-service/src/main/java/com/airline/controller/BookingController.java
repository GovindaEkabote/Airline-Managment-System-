package com.airline.controller;

import com.airline.playload.request.BookingRequest;
import com.airline.playload.response.BookingResponse;
import com.airline.service.BookingService;
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
        value = "/api/v1/booking",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Tag(
        name = "Ticket Booking",
        description = "Operations for managing Airline Booking services"
)
public class BookingController {

    private final BookingService bookingService;

    @PostMapping(
            value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Booking is Created",
            description = "Creates a new Booking Service for an airline."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Booking created successfully"),
            @ApiResponse(responseCode = "400",
                    description = "Validation failed"),
            @ApiResponse(responseCode = "404",
                    description = "Booking not found"),
            @ApiResponse(responseCode = "409",
                    description = "Booking already exists")
    })
    public ResponseEntity<BookingResponse> createBooking(
            @RequestParam("X-User-id") Long userId,
            @Valid @RequestBody BookingRequest request
            ){
                BookingResponse response =
                        bookingService.createBooking(request,
                                userId);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Booking by ID",
            description = "Retrieves an booking service by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Booking retrieved successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Booking not found")
    })
    public ResponseEntity<BookingResponse> getBookingById(
            @RequestParam Long id
    ){
        BookingResponse response = bookingService.getBookingById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Get Booking by User ID",
            description = "Retrieves an booking service by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Booking retrieved successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Booking not found")
    })
    public ResponseEntity<List<BookingResponse>>  getBookingsByUser(
            @RequestParam Long userId
    ){
        List<BookingResponse> response = bookingService.getBookingsByUser(userId);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @GetMapping("/cancle/{id}")
    @Operation(
            summary = "Get Booking by ID",
            description = "Retrieves an booking service by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Booking Cancle successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Booking not found")
    })
    public ResponseEntity<BookingResponse> cancleBooking(
            @RequestParam Long id
    ){
        BookingResponse response = bookingService.cancleBooking(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Booking by ID",
            description = "Retrieves an booking service by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Booking Detelted successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Booking not found")
    })
    public void deleteBooking(
            @RequestParam Long id
    ){
       bookingService.deleteBooking(id);
    }

}
