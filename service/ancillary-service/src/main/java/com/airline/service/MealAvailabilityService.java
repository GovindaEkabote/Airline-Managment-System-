package com.airline.service;

import java.time.Instant;

public interface MealAvailabilityService {

    boolean isMealAvailable(Long mealId);

    boolean canBookMeal(
            Long mealId,
            Instant departureTime,
            Instant bookingTime
    );

    void markMealAvailable(Long mealId);

    void markMealUnavailable(Long mealId);
}
