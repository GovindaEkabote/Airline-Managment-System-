package com.airline.playload.response;


import com.airline.embeddable.*;
import com.airline.enums.CabinClassType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FareResponse {

    private Long id;
    private String name;
    private String rbdCode;
    private Long flightId;
    private Long cabinClassId;
    private CabinClassType cabinClass;
    private String currency;
    private BigDecimal baseFare;
    private BigDecimal taxesAndFees;
    private BigDecimal airlineFees;
    private BigDecimal currentPrice;
    private BigDecimal totalPrice;
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

    private Long fareRulesId;

    private FareRuleResponse fareRule;
    private BaggagePolicyResponse baggagePolicy;
    
    private Instant createdAt;
    private Instant updatedAt;
}
