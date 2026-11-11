package com.airline.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCompletedEvent {

    private Long paymentId;
    private Long  bookingId;
    private Long userId;
    private BigDecimal amount;
    private String transactionId;
    private String providerPaymentId;
    private LocalDateTime paidAt;
}
