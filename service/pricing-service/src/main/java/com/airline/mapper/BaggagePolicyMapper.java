package com.airline.mapper;

import com.airline.model.BaggagePolicy;
import com.airline.model.Fare;
import com.airline.playload.request.BaggagePolicyRequest;
import com.airline.playload.response.BaggagePolicyResponse;

public class BaggagePolicyMapper {

    public static BaggagePolicy toEntity(BaggagePolicyRequest request,
                                         Fare fare){
        if(request == null){
            return null;
        }

        return BaggagePolicy.builder()
                .fare(fare.getId() != null ? fare : null)
                .name(request.getName())
                .description(request.getDescription())
                .cabinBaggageMaxWeightLimit(request.getCabinBaggageMaxWeightLimit())
                .cabinBaggagePieces(request.getCabinBaggagePieces())
                .cabinBaggageWeightPerPiece(request.getCabinBaggageWeightPerPiece())
                .cabinBaggageMaxDimensions(request.getCabinBaggageMaxDimensions())
                .checkInBaggageMaxWeightLimit(request.getCheckInBaggageMaxWeightLimit())
                .checkInBaggagePieces(request.getCheckInBaggagePieces())
                .checkInBaggageWeightPerPiece(request.getCheckInBaggageWeightPerPiece())
                .freeCheckBaggageAllowance(request.getFreeCheckBaggageAllowance())
                .priorityBaggage(request.getPriorityBaggage())
                .extraBaggageAllowed(request.getExtraBaggageAllowed())
                .airlineId(request.getAirlineId())
                .build();
    }

    public static BaggagePolicyResponse toResponse(BaggagePolicy baggagePolicy) {
        if (baggagePolicy == null) {
            return null;
        }

        return BaggagePolicyResponse.builder()
                .id(baggagePolicy.getId())
                .fareId(baggagePolicy.getFare() != null ? baggagePolicy.getFare().getId() : null)
                .description(baggagePolicy.getDescription())
                .cabinBaggageMaxWeightLimit(baggagePolicy.getCabinBaggageMaxWeightLimit())
                .cabinBaggagePieces(baggagePolicy.getCabinBaggagePieces())
                .cabinBaggageWeightPerPiece(baggagePolicy.getCabinBaggageWeightPerPiece())
                .cabinBaggageMaxDimensions(baggagePolicy.getCabinBaggageMaxDimensions())
                .checkInBaggageMaxWeightLimit(baggagePolicy.getCheckInBaggageMaxWeightLimit())
                .checkInBaggagePieces(baggagePolicy.getCheckInBaggagePieces())
                .checkInBaggageWeightPerPiece(baggagePolicy.getCheckInBaggageWeightPerPiece())
                .freeCheckBaggageAllowance(baggagePolicy.getFreeCheckBaggageAllowance())
                .priorityBaggage(baggagePolicy.getPriorityBaggage())
                .extraBaggageAllowed(baggagePolicy.getExtraBaggageAllowed())
                .airlineId(baggagePolicy.getAirlineId())
                .build();
    }

    public static void updateEntity(BaggagePolicy existingBaggagePolicy, BaggagePolicyRequest request) {
        if (existingBaggagePolicy == null || request == null) {
            return;
        }

        if (request.getDescription() != null) {
            existingBaggagePolicy.setDescription(request.getDescription());
        }
        if (request.getCabinBaggageMaxWeightLimit() != null) {
            existingBaggagePolicy.setCabinBaggageMaxWeightLimit(request.getCabinBaggageMaxWeightLimit());
        }
        if (request.getCabinBaggagePieces() != null) {
            existingBaggagePolicy.setCabinBaggagePieces(request.getCabinBaggagePieces());
        }
        if (request.getCabinBaggageWeightPerPiece() != null) {
            existingBaggagePolicy.setCabinBaggageWeightPerPiece(request.getCabinBaggageWeightPerPiece());
        }
        if (request.getCabinBaggageMaxDimensions() != null) {
            existingBaggagePolicy.setCabinBaggageMaxDimensions(request.getCabinBaggageMaxDimensions());
        }
        if (request.getCheckInBaggageMaxWeightLimit() != null) {
            existingBaggagePolicy.setCheckInBaggageMaxWeightLimit(request.getCheckInBaggageMaxWeightLimit());
        }
        if (request.getCheckInBaggagePieces() != null) {
            existingBaggagePolicy.setCheckInBaggagePieces(request.getCheckInBaggagePieces());
        }
        if (request.getCheckInBaggageWeightPerPiece() != null) {
            existingBaggagePolicy.setCheckInBaggageWeightPerPiece(request.getCheckInBaggageWeightPerPiece());
        }
        if (request.getFreeCheckBaggageAllowance() != null) {
            existingBaggagePolicy.setFreeCheckBaggageAllowance(request.getFreeCheckBaggageAllowance());
        }
        if (request.getPriorityBaggage() != null) {
            existingBaggagePolicy.setPriorityBaggage(request.getPriorityBaggage());
        }
        if (request.getExtraBaggageAllowed() != null) {
            existingBaggagePolicy.setExtraBaggageAllowed(request.getExtraBaggageAllowed());
        }
    }
}
