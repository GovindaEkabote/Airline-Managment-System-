package com.airline.playload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FareRolesRequest {


    @NotBlank(message = "Rule name is required")
    private String ruleName;
    @NotNull(message = "Airline ID is required")
    private Long airlineId;
    private Long fareId;
    private Boolean isRefundable;
    @Positive(message = "Change fee must be a positive value")
    private Double changeFee;
    @Positive(message = "Cancellation fee must be a positive value")
    private Double cancellationFee;
    @Positive(message = "Refund deadline days must be a positive value")
    private Integer refundDeadlineDays;

    @Positive(message = "Change deadline hours must be a positive value")
    private Integer changeDeadlineHours;

    private Boolean isChangeable;

}
