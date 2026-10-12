package com.airline.service;

import com.airline.playload.request.MealOrderRequest;
import com.airline.playload.response.MealOrderResponse;

import java.util.List;

public interface MealOrderService {

    MealOrderResponse createMealOrder(MealOrderRequest request);

    MealOrderResponse getMealOrderById(Long id);

    List<MealOrderResponse> getMealOrdersByBookingId(Long bookingId);

    MealOrderResponse updateMealOrder(
            Long id,
            MealOrderRequest request
    );

    void cancelMealOrder(Long id);
}
