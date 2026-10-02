package com.airline.playload.response;

import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FareRuleResponse {

    private Long id;

    private String ruleName;

    private Long airlineId;

    private Long fareId;

    private Boolean isRefundable;

    private Double changeFee;

    private Double cancellationFee;

    private Integer refundDeadlineDays;

    private Integer changeDeadlineHours;

    private Boolean isChangeable;
    private Instant createdAt;
    private Instant updatedAt;
}
