package com.airline.service;

import com.airline.playload.request.MealRequest;
import com.airline.playload.response.MealResponse;

import java.util.List;

public interface MealService {

    MealResponse createMeal(MealRequest request);
    MealResponse updateMeal(Long id, MealRequest request);
    MealResponse getMealById(Long id);
    List<MealResponse> getAllMeals();
    List<MealResponse> getMealsByAirlineId(Long airlineId);

    List<MealResponse> getMealsByAncillaryId(Long ancillaryId);

    List<MealResponse> getAvailableMeals(Long airlineId);

    void activateMeal(Long id);

    void deactivateMeal(Long id);

    void deleteMeal(Long id);

}
