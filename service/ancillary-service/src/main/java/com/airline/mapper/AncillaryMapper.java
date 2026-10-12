package com.airline.mapper;

import com.airline.model.Ancillary;
import com.airline.playload.request.AncillaryRequest;
import com.airline.playload.response.AirlineResponse;
import com.airline.playload.response.AncillaryResponse;
import com.airline.playload.response.InsuranceConverageResponse;

import java.util.List;

public class AncillaryMapper {

    private AncillaryMapper() {
        // Utility class
    }

    public static Ancillary toEntity(AncillaryRequest request, Long airlineID){

        if (request == null){
            return null;
        }

        return Ancillary.builder()
                .ancillaryType(request.getAncillaryType())
                .subType(request.getSubType())
                .rfisc(request.getRfisc())
                .name(request.getName())
                .description(request.getDescription())
                .ancillaryMataData(request.getAncillaryMataData())
                .displayOrder(request.getDisplayOrder())
                .airlineId(airlineID)
                .build();
    }

    public static AncillaryResponse toResponse(Ancillary entity,
                                               List<InsuranceConverageResponse> converageResponses){

        if (entity == null){
            return null;
        }

        return AncillaryResponse.builder()
                .id(entity.getId())
                .airlineId(entity.getAirlineId())
                .ancillaryType(entity.getAncillaryType())
                .subType(entity.getSubType())
                .rfisc(entity.getRfisc())
                .name(entity.getName())
                .description(entity.getDescription())
                .ancillaryMataData(entity.getAncillaryMataData())
                .displayOrder(entity.getDisplayOrder())
                .converageResponses(converageResponses)
                .build();
    }

    public static Ancillary  updateEntity(
            Ancillary ancillary,
            AncillaryRequest request
    ){

        if (request == null || ancillary == null){
            return  null;
        }

        if (request.getAncillaryType() != null){
            ancillary.setAncillaryType(request.getAncillaryType());
        }
        if(request.getSubType() != null){
            ancillary.setSubType(request.getSubType());
        }

        if (request.getRfisc() != null){
            ancillary.setRfisc(request.getRfisc());
        }

        if (request.getName() != null){
            ancillary.setName(request.getName());
        }
        if (request.getDescription() != null){
            ancillary.setDescription(request.getDescription());
        }
        if (request.getIconUrl() != null){
            ancillary.setIconUrl(request.getIconUrl());
        }

        if (request.getAncillaryMataData() != null){
            ancillary.setAncillaryMataData(request.getAncillaryMataData());
        }

        if (request.getDisplayOrder() != null){
            ancillary.setDisplayOrder(request.getDisplayOrder());
        }


        return ancillary;
    }
}
