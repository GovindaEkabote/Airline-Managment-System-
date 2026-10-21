
package com.airline.model;

import com.airline.enums.PaymentGateway;
import com.airline.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "payment",
        indexes = {
                @Index(name = "idx_payment_user_id", columnList = "user_id"),
                @Index(name = "idx_payment_booking_id", columnList = "booking_id"),
                @Index(name = "idx_payment_status", columnList = "payment_status")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_gateway", nullable = false, length = 30)
    private PaymentGateway paymentGateway;

    @Column(name = "provider_payment_id", length = 150)
    private String providerPaymentId;

    @Column(name = "transaction_id", nullable = false,
            unique = true, length = 100)
    private String transactionId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 30)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(name = "payment_failure_reason", length = 1000)
    private String paymentFailureReason;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "refund_id", length = 150)
    private String refundId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
