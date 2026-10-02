package com.airline.mapper;

import com.airline.embeddable.*;
import com.airline.model.Fare;
import com.airline.playload.request.FareRequest;
import com.airline.playload.response.FareResponse;

import java.math.BigDecimal;

public class FareMapper {

    public static Fare toEntity(FareRequest fareRequest ){


        if (fareRequest == null) {
            return null;
        }
        BigDecimal currentPrice = calculateCurrentPrice(fareRequest);

            SeatBenifites seatBenifites = SeatBenifites.builder()
                    .extraSeatSpace(bool(fareRequest.getExtraSeatSpace()))
                    .preferredSeatChoice(bool(fareRequest.getPreferredSeatChoice()))
                    .advancedSeatSelection(bool(fareRequest.getAdvancedSeatSelection()))
                    .guaranteedSeatTogether(bool(fareRequest.getGuaranteedSeatTogether()))
                    .build();

            BordingBenifites bordingBenifites = BordingBenifites.builder()
                    .priorityBoarding(bool(fareRequest.getPriorityBoarding()))
                    .priorityCheckin(bool(fareRequest.getPriorityCheckin()))
                    .prioritySecurity(fareRequest.getPrioritySecurity())
                    .build();

            InFlightsBenifites inFlightsBenifites = InFlightsBenifites.builder()
                    .complimentaryMeals(bool(fareRequest.getComplimentaryMeals()))
                    .premiumMealsChoice(bool(fareRequest.getPremiumMealsChoice()))
                    .inFlightInternet(bool(fareRequest.getInFlightInternet()))
                    .inFlightEntertainment(bool(fareRequest.getInFlightEntertainment()))
                    .complimentaryBeverages(bool(fareRequest.getComplimentaryBeverages()))
                    .build();

            FlexibilityBenifites flexibilityBenifites = FlexibilityBenifites.builder()
                    .freeDateChange(bool(fareRequest.getFreeDateChange()))
                    .partialRefund(bool(fareRequest.getPartialRefund()))
                    .fullRefund(bool(fareRequest.getFullRefund()))
                    .build();

            PremiumServiceBenifites premiumServiceBenifites =
                    PremiumServiceBenifites.builder()
                            .loungeAccess(bool(fareRequest.getLoungeAccess()))
                            .airportTransfer(bool(fareRequest.getAirportTransfer()))
                            .build();

        return Fare.builder()
                .name(fareRequest.getName())
                .rbdCode(fareRequest.getRbdCode())
                .flightId(fareRequest.getFlightId())
                .cabinClassId(fareRequest.getCabinClassId())
                .cabinClass(fareRequest.getCabinClass())
                .currency(fareRequest.getCurrency())
                .baseFare(fareRequest.getBaseFare())
                .taxesAndFees(fareRequest.getTaxesAndFees())
                .airlineFees(fareRequest.getAirlineFees())
                .currentPrice(fareRequest.getCurrentPrice())
                .totalPrice(currentPrice)
                .fareLabel(fareRequest.getFareLabel())
                .seatBenifites(seatBenifites)
                .bordingBenifites(bordingBenifites)
                .inFlightsBenifites(inFlightsBenifites)
                .flexibilityBenifites(flexibilityBenifites)
                .premiumServiceBenifites(premiumServiceBenifites)
                .build();
    }

    public static FareResponse toResponse(Fare fare) {

        if (fare == null) {
            return null;
        }

        SeatBenifites seatBenifites = fare.getSeatBenifites();
        BordingBenifites bordingBenifites = fare.getBordingBenifites();
        InFlightsBenifites inFlightsBenifites = fare.getInFlightsBenifites();
        FlexibilityBenifites flexibilityBenifites = fare.getFlexibilityBenifites();
        PremiumServiceBenifites premiumServiceBenifites = fare.getPremiumServiceBenifites();

        return FareResponse.builder()
                .id(fare.getId())
                .name(fare.getName())
                .rbdCode(fare.getRbdCode())
                .flightId(fare.getFlightId())
                .cabinClassId(fare.getCabinClassId())
                .cabinClass(fare.getCabinClass())
                .currency(fare.getCurrency())
                .totalPrice(fare.getTotalPrice())
                .baseFare(fare.getBaseFare())
                .taxesAndFees(fare.getTaxesAndFees())
                .airlineFees(fare.getAirlineFees())
                .currentPrice(fare.getCurrentPrice())
                .fareLabel(fare.getFareLabel())

                // Seat Benefits
                .extraSeatSpace(
                        seatBenifites != null
                        ? seatBenifites.getExtraSeatSpace()
                        : null
                )
                .preferredSeatChoice(
                        seatBenifites != null
                                ? seatBenifites.getPreferredSeatChoice()
                                : null
                )
                .advancedSeatSelection(
                        seatBenifites != null
                                ? seatBenifites.getAdvancedSeatSelection()
                                : null
                )
                .guaranteedSeatTogether(
                        seatBenifites != null
                                ? seatBenifites.getGuaranteedSeatTogether()
                                : null
                )

                .priorityBoarding(
                        bordingBenifites != null
                                ? bordingBenifites.getPriorityBoarding()
                                : null
                )
                .priorityCheckin(
                        bordingBenifites != null
                                ? bordingBenifites.getPriorityCheckin()
                                : null
                )
                .prioritySecurity(
                        bordingBenifites != null
                                ? bordingBenifites.getPrioritySecurity()
                                : null
                )

                .complimentaryMeals(
                        inFlightsBenifites != null
                                ? inFlightsBenifites.getComplimentaryMeals()
                                : null
                )
                .premiumMealsChoice(
                        inFlightsBenifites != null
                        ? inFlightsBenifites.getPremiumMealsChoice()
                        : null
                )
                .inFlightInternet(
                        inFlightsBenifites != null
                                ? inFlightsBenifites.getInFlightInternet()
                                : null
                )
                .inFlightEntertainment(
                        inFlightsBenifites != null
                                ? inFlightsBenifites.getInFlightEntertainment()
                                : null
                )
                .complimentaryBeverages(
                        inFlightsBenifites != null
                                ? inFlightsBenifites.getComplimentaryBeverages()
                                : null
                )

                .freeDateChange(
                        flexibilityBenifites != null
                                ? flexibilityBenifites.getFreeDateChange()
                                : null
                )
                .partialRefund(
                        flexibilityBenifites != null
                                ? flexibilityBenifites.getPartialRefund()
                                : null
                )
                .fullRefund(
                        flexibilityBenifites != null
                                ? flexibilityBenifites.getFullRefund()
                                : null
                )

                .loungeAccess(
                        premiumServiceBenifites != null
                                ? premiumServiceBenifites.getLoungeAccess()
                                : null
                )
                .airportTransfer(
                        premiumServiceBenifites != null
                                ? premiumServiceBenifites.getAirportTransfer()
                                : null
                )

//                .fareRulesId(fare.getFareRulesId())
//                .fareRule(FareRuleMapper.toResponse(fare.getFareRule()))
//                .baggagePolicy(BaggagePolicyMapper.toResponse(fare.getBaggagePolicy()))

                .createdAt(fare.getCreatedAt())
                .updatedAt(fare.getUpdatedAt())
                .build();
    }


    public static Fare updateEntity(
            Fare fare,
            FareRequest request){

        if (fare == null || request == null) {
            return fare;
        }

        // Basic Information
        if (request.getName() != null) {
            fare.setName(request.getName());
        }

        if (request.getRbdCode() != null) {
            fare.setRbdCode(request.getRbdCode());
        }

        if (request.getFlightId() != null) {
            fare.setFlightId(request.getFlightId());
        }

        if (request.getCabinClassId() != null) {
            fare.setCabinClassId(request.getCabinClassId());
        }

        if (request.getCabinClass() != null) {
            fare.setCabinClass(request.getCabinClass());
        }

        if (request.getCurrency() != null) {
            fare.setCurrency(request.getCurrency());
        }

        if (request.getBaseFare() != null) {
            fare.setBaseFare(request.getBaseFare());
        }

        if (request.getTaxesAndFees() != null) {
            fare.setTaxesAndFees(request.getTaxesAndFees());
        }

        if (request.getAirlineFees() != null) {
            fare.setAirlineFees(request.getAirlineFees());
        }

        if (request.getFareLabel() != null) {
            fare.setFareLabel(request.getFareLabel());
        }

        // Price
        if (request.getCurrentPrice() != null){
            fare.setCurrentPrice(request.getCurrentPrice());
        }else {
            fare.setCurrentPrice(calculateCurrentPrice(
                    fare.getBaseFare(),
                    fare.getTaxesAndFees(),
                    fare.getAirlineFees()
            ));
        }

        // Seat Benefits
        SeatBenifites seatBenifites = fare.getSeatBenifites();
        if (seatBenifites == null){
            seatBenifites = new SeatBenifites();
            fare.setSeatBenifites(seatBenifites);
        }

        if (request.getExtraSeatSpace() != null) {
            seatBenifites.setExtraSeatSpace(request.getExtraSeatSpace());
        }

        if (request.getPreferredSeatChoice() != null) {
            seatBenifites.setPreferredSeatChoice(request.getPreferredSeatChoice());
        }

        if (request.getAdvancedSeatSelection() != null) {
            seatBenifites.setAdvancedSeatSelection(request.getAdvancedSeatSelection());
        }

        if (request.getGuaranteedSeatTogether() != null) {
            seatBenifites.setGuaranteedSeatTogether(request.getGuaranteedSeatTogether());
        }

        // Boarding
        BordingBenifites boarding = fare.getBordingBenifites();
        if (boarding == null){
            boarding = new BordingBenifites();
            fare.setBordingBenifites(boarding);
        }
        if (request.getPriorityBoarding() != null){
            boarding.setPriorityBoarding(request.getPriorityBoarding());
        }
        if(request.getPriorityCheckin() != null){
            boarding.setPriorityCheckin(request.getPriorityCheckin());
        }
        if (request.getPrioritySecurity() != null){
            boarding.setPrioritySecurity(request.getPrioritySecurity());
        }

        InFlightsBenifites inFlightsBenifites = fare.getInFlightsBenifites();
        if (inFlightsBenifites == null){
            inFlightsBenifites = new InFlightsBenifites();
            fare.setInFlightsBenifites(inFlightsBenifites);
        }
        if (request.getComplimentaryMeals() != null){
            inFlightsBenifites.setComplimentaryMeals(request.getComplimentaryMeals());
        }
        if (request.getPremiumMealsChoice() != null){
            inFlightsBenifites.setPremiumMealsChoice(request.getPremiumMealsChoice());
        }
        if (request.getInFlightInternet() != null){
            inFlightsBenifites.setInFlightInternet(request.getInFlightInternet());
        }
        if (request.getInFlightEntertainment() != null){
            inFlightsBenifites.setInFlightEntertainment(request.getInFlightEntertainment());
        }
        if (request.getComplimentaryBeverages() != null){
            inFlightsBenifites.setComplimentaryBeverages(request.getComplimentaryBeverages());
        }

        FlexibilityBenifites flexibility = fare.getFlexibilityBenifites();

        if (flexibility == null) {
            flexibility = new FlexibilityBenifites();
            fare.setFlexibilityBenifites(flexibility);
        }

        if (request.getFreeDateChange() != null) {
            flexibility.setFreeDateChange(request.getFreeDateChange());
        }

        if (request.getPartialRefund() != null) {
            flexibility.setPartialRefund(request.getPartialRefund());
        }

        if (request.getFullRefund() != null) {
            flexibility.setFullRefund(request.getFullRefund());
        }

        PremiumServiceBenifites premium = fare.getPremiumServiceBenifites();

        if (premium == null) {
            premium = new PremiumServiceBenifites();
            fare.setPremiumServiceBenifites(premium);
        }

        if (request.getLoungeAccess() != null) {
            premium.setLoungeAccess(request.getLoungeAccess());
        }

        if (request.getAirportTransfer() != null) {
            premium.setAirportTransfer(request.getAirportTransfer());
        }

        return fare;
    }


    // Boolean Helper
    private static Boolean bool(Boolean value){
        return value != null ? value : false;
    }

    private static  BigDecimal calculateCurrentPrice(FareRequest request){
        if (request.getCurrentPrice() != null){
            return request.getCurrentPrice();
        }

        return calculateCurrentPrice(
                request.getBaseFare(),
                request.getTaxesAndFees(),
                request.getAirlineFees()
        );
    }

    private static BigDecimal calculateCurrentPrice(
            BigDecimal baseFare,
            BigDecimal taxesAndFees,
            BigDecimal airlineFees
    ){
        BigDecimal price = BigDecimal.ZERO;

        if (baseFare != null){
            price = price.add(baseFare);
        }

        if (taxesAndFees != null){
            price = price.add(taxesAndFees);
        }

        if (airlineFees != null){
            price = price.add(airlineFees);
        }

        return price;
    }
}
