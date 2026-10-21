package com.airline.playload.dto;

import com.airline.enums.PaymentGateway;
import com.airline.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {

    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;

    private Long bookingId;
    private PaymentStatus paymentStatus;
    private PaymentGateway paymentGateway;
    private BigDecimal amount;
    private String transactionId;
    private String gatewayPaymentId;
    private String getwayOrderId;
    private String gatewaySignature;

    private String description;

    private LocalDateTime initiatedAt;
    private  LocalDateTime completedAt;

    private Boolean notificationSent;
    private Boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



}
