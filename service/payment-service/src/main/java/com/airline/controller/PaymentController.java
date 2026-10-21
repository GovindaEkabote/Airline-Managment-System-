
package com.airline.controller;

import com.airline.playload.dto.PaymentDTO;
import com.airline.playload.request.PaymentInitiatRequest;
import com.airline.playload.request.PaymentVerifyRequest;
import com.airline.playload.response.PaymentInitiatResponse;
import com.airline.service.PaymentService;
import com.razorpay.RazorpayException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(
        value = "/api/v1/payment",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Tag(
        name = "Payment",
        description = "Operations for initiating and managing payments"
)
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Initiate payment",
            description = "Initiates a payment for an airline booking."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Payment initiated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid payment request"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Booking or user not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Payment conflict or duplicate request"
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "Payment gateway failure"
            )
    })
    public ResponseEntity<PaymentInitiatResponse> initiatePayment(
            @Valid @RequestBody PaymentInitiatRequest request
    ) throws RazorpayException {

        PaymentInitiatResponse response =
                paymentService.initiatePayment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @PostMapping(
            value = "/verify",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Verify payment",
            description = "Verifies a Razorpay payment and updates the payment status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment verified successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid payment details or signature"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Payment record not found"
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "Payment gateway verification failure"
            )
    })
    public ResponseEntity<PaymentDTO> verifyPayment(
            @Valid @RequestBody PaymentVerifyRequest request
    ) throws RazorpayException {

        PaymentDTO paymentDTO = paymentService.verifyPayment(request);

        return ResponseEntity.ok(paymentDTO);
    }


    @PostMapping(
            value = "/batch/booking",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Get payments by booking IDs",
            description = "Retrieves payment details for multiple airline bookings."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Payments retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid or empty booking ID list"
            )
    })
    public ResponseEntity<Map<Long, PaymentDTO>> getPaymentByBookingIds(
            @Valid @RequestBody List<@NotNull @Positive Long> bookingIds
    ) {
        if (bookingIds == null || bookingIds.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Booking IDs must not be empty"
            );
        }

        return ResponseEntity.ok(
                paymentService.getPaymentsByBookingIds(bookingIds)
        );
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get all payments",
            description = "Returns paginated payment records with sorting."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Payments retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination or sorting parameters"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Insufficient permissions"
            )
    })
    public ResponseEntity<Page<PaymentDTO>> getAllPayments(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    ) {
        Set<String> allowedSortFields =
                Set.of("createdAt", "updatedAt", "amount", "status");

        if (!allowedSortFields.contains(sortBy)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid sort field: " + sortBy
            );
        }

        Sort.Direction direction;

        try {
            direction = Sort.Direction.fromString(sortDirection);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "sortDirection must be ASC or DESC"
            );
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(direction, sortBy)
        );

        Page<PaymentDTO> payments =
                paymentService.getAllPayments(pageable);

        return ResponseEntity.ok(payments);
    }



}
