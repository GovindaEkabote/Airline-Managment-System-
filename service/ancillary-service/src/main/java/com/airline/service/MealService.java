package com.airline.service;

import com.airline.playload.request.MealRequest;
import com.airline.playload.response.MealResponse;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface MealService {

    MealResponse createMeal(MealRequest request, Long userId);
    MealResponse updateMeal(Long userId, Long id, MealRequest request) throws AccessDeniedException;
    MealResponse getMealById(Long id);
    List<MealResponse> getAllMeals();
    List<MealResponse> getMealsByAirlineId(Long userId);

    List<MealResponse> getMealsByAncillaryId(Long ancillaryId);

    List<MealResponse> getAvailableMeals(Long airlineId);

    void activateMeal(Long id);

    void deactivateMeal(Long id);

    void deleteMeal(Long id);

}
