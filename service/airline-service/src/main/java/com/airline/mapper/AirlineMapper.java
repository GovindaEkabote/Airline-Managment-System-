package com.airline.mapper;

import com.airline.embeddable.Support;
import com.airline.model.Airline;
import com.airline.playload.request.AirlineRequest;
import com.airline.playload.response.AirlineResponse;

public class AirlineMapper {

    public static Airline toEntity(
            AirlineRequest airlineRequest,
            Long ownerId
    ){
        if (airlineRequest == null){
            return null;
        }

        Airline airline = Airline.builder()
                .iataCode(airlineRequest.getIataCode())
                .icaoCode(airlineRequest.getIcaoCode())
                .name(airlineRequest.getName())
                .alias(airlineRequest.getAlias())
                .logoUrl(airlineRequest.getLogoUrl())
                .website(airlineRequest.getWebsite())
                .status(airlineRequest.getStatus())
                .alliance(airlineRequest.getAlliance())
                .headquartersCityId(airlineRequest.getHeadquartersCityId())
                .ownerId(ownerId)
                .build();

        if (airlineRequest.getSupportEmail() != null
        || airlineRequest.getSupportPhone() != null
        || airlineRequest.getSupportHours() != null){
            airline.setSupport(Support.builder()
                    .email(airlineRequest.getSupportEmail())
                    .phone(airlineRequest.getSupportPhone())
                    .hours(airlineRequest.getSupportHours())
                    .build());
        }
        return airline;
    }

    public static AirlineResponse toResponse(Airline airline){
        if (airline == null){
            return null;
        }
        return AirlineResponse.builder()
                .id(airline.getId())
                .iataCode(airline.getIataCode())
                .icaoCode(airline.getIcaoCode())
                .name(airline.getName())
                .alias(airline.getAlias())
                .logoUrl(airline.getLogoUrl())
                .website(airline.getWebsite())
                .status(airline.getStatus())
                .alliance(airline.getAlliance())
                .ownerId(airline.getOwnerId())
                .updatedById(airline.getUpdatedById())
                .headquartersCityId(airline.getHeadquartersCityId())
                .support(airline.getSupport())
                .createdAt(airline.getCreatedAt())
                .updatedAt(airline.getUpdatedAt())
                .build();
    }

    public static Airline updateEntity(
            Airline airline,
            AirlineRequest request){

        if (airline == null || request == null) {
            return airline;
        }

        if (request.getIataCode() != null) {
            airline.setIataCode(request.getIataCode());
        }

        if (request.getIcaoCode() != null) {
            airline.setIcaoCode(request.getIcaoCode());
        }

        if (request.getName() != null) {
            airline.setName(request.getName());
        }

        if (request.getAlias() != null) {
            airline.setAlias(request.getAlias());
        }

        if (request.getLogoUrl() != null) {
            airline.setLogoUrl(request.getLogoUrl());
        }

        if (request.getWebsite() != null) {
            airline.setWebsite(request.getWebsite());
        }

        if (request.getStatus() != null) {
            airline.setStatus(request.getStatus());
        }

        if (request.getAlliance() != null) {
            airline.setAlliance(request.getAlliance());
        }

        if (request.getHeadquartersCityId() != null) {
            airline.setHeadquartersCityId(
                    request.getHeadquartersCityId()
            );
        }

        // Support
        if (request.getSupportEmail() != null
                || request.getSupportPhone() != null
                || request.getSupportHours() != null) {

            if (airline.getSupport() == null) {
                airline.setSupport(new Support());
            }

            if (request.getSupportEmail() != null) {
                airline.getSupport().setEmail(
                        request.getSupportEmail()
                );
            }

            if (request.getSupportPhone() != null) {
                airline.getSupport().setPhone(
                        request.getSupportPhone()
                );
            }

            if (request.getSupportHours() != null) {
                airline.getSupport().setHours(
                        request.getSupportHours()
                );
            }
        }

        return airline;
    }
}
