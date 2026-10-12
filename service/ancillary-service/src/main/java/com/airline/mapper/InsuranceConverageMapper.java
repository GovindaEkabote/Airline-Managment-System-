package com.airline.mapper;

import com.airline.model.Ancillary;
import com.airline.model.InsuranceCoverage;
import com.airline.playload.request.InsuranceCoverageRequest;
import com.airline.playload.response.InsuranceConverageResponse;

public class InsuranceConverageMapper {


    private InsuranceConverageMapper(){
        // Utility class
    }

    public static InsuranceCoverage toEntity(InsuranceCoverageRequest request, Ancillary ancillary){
        if (request == null){
            return null;
        }

        return InsuranceCoverage.builder()
                .ancillary(ancillary)
                .coverageType(request.getCoverageType())
                .name(request.getName())
                .description(request.getDescription())
                .isFlat(request.getIsFlat() !=  null ? request.getIsFlat() : true)
                .claimCondition(request.getClaimCondition())
                .displayOrder(request.getDisplayOrder())
                .emergencyContact(request.getEmergencyContact())
                .active(request.getActive() != null ? request.getActive() : true)
                .build();
    }

    public static InsuranceConverageResponse toResponse(InsuranceCoverage request){
        if (request == null){
            return null;
        }

        return InsuranceConverageResponse.builder()
                .id(request.getId())
                .ancillaryId(request.getId())
                .ancillaryName(request.getName())
                .coverageType(request.getCoverageType())
                .name(request.getName())
                .description(request.getDescription())
                .coverageAmount(request.getCoverageAmount())
                .emergencyContact(request.getEmergencyContact())
                .isFlat(request.getIsFlat())
                .claimCondition(request.getClaimCondition())
                .displayOrder(request.getDisplayOrder())
                .active(request.getActive())
                .build();
    }

    public static InsuranceCoverage updateEntity(
            InsuranceCoverage coverage,
            InsuranceCoverageRequest request,
            Ancillary ancillary
    ){

        if (ancillary != null){
            coverage.setAncillary(ancillary);
        }
        if (request == null || coverage == null){
            return null;
        }

        if (request.getName() != null){
            coverage.setName(request.getName());
        }

        if (request.getCoverageType() != null){
            coverage.setCoverageType(request.getCoverageType());
        }

        if (request.getDescription() != null){
            coverage.setCoverageType(request.getCoverageType());
        }

        if (request.getCoverageAmount() != null){
            coverage.setCoverageAmount(request.getCoverageAmount());
        }

        if (request.getIsFlat() != null){
            coverage.setIsFlat(request.getIsFlat());
        }

        if (request.getClaimCondition() != null){
            coverage.setClaimCondition(request.getClaimCondition());
        }

        if (request.getDisplayOrder() != null){
            coverage.setDisplayOrder(request.getDisplayOrder());
        }
        if (request.getEmergencyContact() != null){
            coverage.setEmergencyContact(request.getEmergencyContact());
        }
        if (request.getActive() != null){
            coverage.setActive(request.getActive());
        }

        return coverage;
    }

}
