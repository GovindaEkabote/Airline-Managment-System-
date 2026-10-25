package com.airline.service.integration;

import com.airline.config.feignclient.PriceClient;
import com.airline.playload.response.FareResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CalculatesFareTotal {

    private final PriceClient priceClient;

    public BigDecimal calculateFareTotal(Long fareId){
        FareResponse fareResponse = priceClient.getFareById(fareId);

        BigDecimal baseFare = fareResponse.getBaseFare();

        BigDecimal taxesAndFees = Objects.requireNonNullElse(
                fareResponse.getTaxesAndFees(), BigDecimal.ZERO);

        BigDecimal airlineFees = Objects.requireNonNullElse(
                fareResponse.getAirlineFees(), BigDecimal.ZERO);

        return baseFare.add(taxesAndFees).add(airlineFees);
    }
}
