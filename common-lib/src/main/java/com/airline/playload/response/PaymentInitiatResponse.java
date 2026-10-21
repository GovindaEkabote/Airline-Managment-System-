package com.airline.playload.response;

import com.airline.enums.PaymentGateway;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentInitiatResponse {

    private Long id;
    private PaymentGateway paymentGateway;
    private BigDecimal amount;
    private String transactionId;
    private String razorpayOrderId;
    private String descriptiom;
    private String checkoutUrl;
    private String message;
    private Boolean success;
}
