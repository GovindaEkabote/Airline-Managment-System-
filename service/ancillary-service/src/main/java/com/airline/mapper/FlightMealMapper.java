package com.airline.mapper;

import com.airline.model.FlightMeal;
import com.airline.model.Meal;
import com.airline.playload.request.FlightMealRequest;
import com.airline.playload.response.FlightMealResponse;
import com.airline.playload.response.MealResponse;

public class FlightMealMapper {

    private void FlightMealMapper(){

    }

    public static FlightMeal toEntity(FlightMealRequest request, Meal meal){

        if (request == null){
            return  null;
        }

        return FlightMeal.builder()
                .flightId(request.getFlightId())
                .meal(meal)
                .available(request.getAvailable())
                .price(request.getPrice())
                .displayOrder(request.getDisplayOrder())
                .build();
    }

    public static FlightMealResponse toResponse(FlightMeal flightMeal){
        if (flightMeal == null){
            return null;
        }

        MealResponse mealResponse = MealMapper.toResponse(
                flightMeal.getMeal(),
                flightMeal.getMeal() != null
                        && flightMeal.getMeal().getAncillary() != null
                        ? AncillaryMapper.toResponse(
                        flightMeal.getMeal().getAncillary(),
                        null
                )
                        : null,
                null
        );
        return FlightMealResponse.builder()
                .id(flightMeal.getId())
                .flightId(flightMeal.getFlightId())
                .available(flightMeal.getAvailable())
                .price(flightMeal.getPrice())
                .meal(mealResponse)
                .displayOrder(flightMeal.getDisplayOrder())
                .build();
    }

    public static FlightMeal updateEntity(
            FlightMeal flightMeal,
            FlightMealRequest request
    ){
        if (flightMeal == null || request == null){
            return null;
        }
        if (request.getAvailable() != null){
            flightMeal.setAvailable(request.getAvailable());
        }
        if (request.getPrice() != null){
            flightMeal.setPrice(request.getPrice());
        }
        if (request.getDisplayOrder() != null){
            flightMeal.setDisplayOrder(request.getDisplayOrder());
        }
        return flightMeal;
    }
}
