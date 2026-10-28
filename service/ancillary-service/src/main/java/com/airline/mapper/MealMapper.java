package com.airline.mapper;

import com.airline.model.Ancillary;
import com.airline.model.Meal;
import com.airline.playload.request.MealRequest;
import com.airline.playload.response.AirlineResponse;
import com.airline.playload.response.AncillaryResponse;
import com.airline.playload.response.MealResponse;

public final class MealMapper {

    private MealMapper() {
        // Utility class
    }

    // CREATE
    public static Meal toEntity(
            MealRequest request,
            Ancillary ancillary,
            Long userId) {

        if (request == null) {
            return null;
        }

        return Meal.builder()
                .ancillary(ancillary)
                .code(request.getCode())
                .airlineId(userId)
                .name(request.getName())
                .description(request.getDescription())
                .mealType(request.getMealType())
                .available(
                        request.getAvailable() != null
                                ? request.getAvailable()
                                : true
                )
                .price(request.getPrice())
                .vegetarian(request.isVegetarian())
                .vegan(request.isVegan())
                .glutenFree(request.isGlutenFree())
                .lactoseFree(request.isLactoseFree())
                .containsNuts(request.isContainsNuts())
                .cuisine(request.getCuisine())
                .requiresAdvanceBooking(
                        request.getRequiresAdvanceBooking() != null
                                ? request.getRequiresAdvanceBooking()
                                : false
                )
                .advanceBookingHours(
                        request.getAdvanceBookingHours())
                .imageUrl(request.getImageUrl())
                .displayOrder(request.getDisplayOrder())
                .active(
                        request.getActive() != null
                                ? request.getActive()
                                : true
                )
                .build();
    }

    // RESPONSE
    public static MealResponse toResponse(
            Meal meal,
            AncillaryResponse ancillaryResponse,
            AirlineResponse airlineResponse) {

        if (meal == null) {
            return null;
        }

        return MealResponse.builder()
                .id(meal.getId())
                .ancillary(ancillaryResponse)
                .code(meal.getCode())
                .airlineResponse(airlineResponse)
                .name(meal.getName())
                .description(meal.getDescription())
                .mealType(meal.getMealType())
                .available(meal.getAvailable())
                .price(meal.getPrice())
                .vegetarian(meal.isVegetarian())
                .vegan(meal.isVegan())
                .glutenFree(meal.isGlutenFree())
                .lactoseFree(meal.isLactoseFree())
                .containsNuts(meal.isContainsNuts())
                .cuisine(meal.getCuisine())
                .requiresAdvanceBooking(
                        meal.getRequiresAdvanceBooking())
                .advanceBookingHours(
                        meal.getAdvanceBookingHours())
                .imageUrl(meal.getImageUrl())
                .displayOrder(meal.getDisplayOrder())
                .active(meal.isActive())
                .createdAt(meal.getCreatedAt())
                .updatedAt(meal.getUpdatedAt())
                .build();
    }

    // UPDATE
    public static Meal updateEntity(
            Meal meal,
            MealRequest request,
            Ancillary ancillary) {

        if (meal == null || request == null) {
            return meal;
        }

        if (ancillary != null) {
            meal.setAncillary(ancillary);
        }

        if (request.getCode() != null) {
            meal.setCode(request.getCode());
        }

        if (request.getAirlineId() != null) {
            meal.setAirlineId(request.getAirlineId());
        }

        if (request.getName() != null) {
            meal.setName(request.getName());
        }

        if (request.getDescription() != null) {
            meal.setDescription(request.getDescription());
        }

        if (request.getMealType() != null) {
            meal.setMealType(request.getMealType());
        }

        if (request.getAvailable() != null) {
            meal.setAvailable(request.getAvailable());
        }

        if (request.getPrice() != null) {
            meal.setPrice(request.getPrice());
        }

        meal.setVegetarian(request.isVegetarian());
        meal.setVegan(request.isVegan());
        meal.setGlutenFree(request.isGlutenFree());
        meal.setLactoseFree(request.isLactoseFree());
        meal.setContainsNuts(request.isContainsNuts());

        if (request.getCuisine() != null) {
            meal.setCuisine(request.getCuisine());
        }

        if (request.getRequiresAdvanceBooking() != null) {
            meal.setRequiresAdvanceBooking(
                    request.getRequiresAdvanceBooking());
        }

        if (request.getAdvanceBookingHours() != null) {
            meal.setAdvanceBookingHours(
                    request.getAdvanceBookingHours());
        }

        if (request.getImageUrl() != null) {
            meal.setImageUrl(request.getImageUrl());
        }

        if (request.getDisplayOrder() != null) {
            meal.setDisplayOrder(request.getDisplayOrder());
        }

        if (request.getActive() != null) {
            meal.setActive(request.getActive());
        }

        return meal;
    }
}