package com.airline.playload.request;

import com.airline.enums.PaymentGateway;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentInitiatRequest {

    @NotNull(message = "user Id is required")
    private Long userId;

    @NotNull(message = "booking Id is required")
    private Long bookingId;

    @NotNull(message = "Payment gateway is required")
    private PaymentGateway paymentGateway;

    private String providerPaymentId;

    private BigDecimal amount;

    private String description;



}
