package com.airline.mapper;

import com.airline.model.Fare;
import com.airline.model.FareRoles;
import com.airline.playload.request.FareRolesRequest;
import com.airline.playload.response.FareRuleResponse;

public class FareRolesMapper {

    public static FareRoles toEntity(FareRolesRequest request,
                                     Fare fare) {

        if (request == null) {
            return null;
        }

        return FareRoles.builder()
                .ruleName(request.getRuleName())
                .airlineId(request.getAirlineId())
                .fare(fare.getId() != null ? fare : null)
                .isRefundable(request.getIsRefundable())
                .changeFee(request.getChangeFee())
                .cancellationFee(request.getCancellationFee())
                .refundDeadlineDays(request.getRefundDeadlineDays())
                .changeDeadlineHours(request.getChangeDeadlineHours())
                .isChangeable(request.getIsChangeable() != null ? request.getIsChangeable() : false)
                .build();
    }

    public static FareRuleResponse toResponse(FareRoles fareRoles) {
        if (fareRoles == null) {
            return null;
        }

        return FareRuleResponse.builder()
                .id(fareRoles.getId())
                .ruleName(fareRoles.getRuleName())
                .airlineId(fareRoles.getAirlineId())
                .fareId(fareRoles.getFare() != null ? fareRoles.getFare().getId() : null)
                .isRefundable(fareRoles.getIsRefundable())
                .changeFee(fareRoles.getChangeFee())
                .cancellationFee(fareRoles.getCancellationFee())
                .refundDeadlineDays(fareRoles.getRefundDeadlineDays())
                .changeDeadlineHours(fareRoles.getChangeDeadlineHours())
                .isChangeable(fareRoles.getIsChangeable())
                .build();
    }

    public  static void updateEntity(FareRoles existingFareRoles, FareRolesRequest existing) {
        if (existingFareRoles == null || existing == null) {
            return;
        }

        if (existing.getRuleName() != null) {
            existingFareRoles.setRuleName(existing.getRuleName());
        }
        if (existing.getAirlineId() != null) {
            existingFareRoles.setAirlineId(existing.getAirlineId());
        }
        if (existing.getIsRefundable() != null) {
            existingFareRoles.setIsRefundable(existing.getIsRefundable());
        }
        if (existing.getChangeFee() != null) {
            existingFareRoles.setChangeFee(existing.getChangeFee());
        }
        if (existing.getCancellationFee() != null) {
            existingFareRoles.setCancellationFee(existing.getCancellationFee());
        }
        if (existing.getRefundDeadlineDays() != null) {
            existingFareRoles.setRefundDeadlineDays(existing.getRefundDeadlineDays());
        }
        if (existing.getChangeDeadlineHours() != null) {
            existingFareRoles.setChangeDeadlineHours(existing.getChangeDeadlineHours());
        }
        if (existing.getIsChangeable() != null) {
            existingFareRoles.setIsChangeable(existing.getIsChangeable());
        }
    }
}
