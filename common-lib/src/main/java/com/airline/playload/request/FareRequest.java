package com.airline.playload.request;

import com.airline.embeddable.*;
import com.airline.enums.CabinClassType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FareRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    private String rbdCode;

    @NotNull
    private Long flightId;

    @NotNull
    private Long cabinClassId;

    private  CabinClassType cabinClass;

    @NotBlank
    @Size(min = 3, max = 3)
    private String currency;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal baseFare;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal taxesAndFees;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal airlineFees;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal currentPrice;

    private BigDecimal totalPrice;

    @Size(max = 100)
    private String fareLabel;

    private Boolean extraSeatSpace;
    private Boolean preferredSeatChoice;
    private Boolean advancedSeatSelection;
    private Boolean guaranteedSeatTogether;

    private Boolean priorityBoarding;
    private Boolean priorityCheckin;
    private Boolean prioritySecurity;

    private Boolean complimentaryMeals;
    private Boolean premiumMealsChoice;
    private Boolean inFlightInternet;
    private Boolean inFlightEntertainment;
    private Boolean complimentaryBeverages;

    private Boolean freeDateChange;
    private Boolean partialRefund;
    private Boolean fullRefund;

    private Boolean loungeAccess;
    private Boolean airportTransfer;
}