package com.airline.service;

import com.airline.playload.request.FlightMealRequest;
import com.airline.playload.response.FlightMealResponse;

import java.util.List;

public interface FlightMealService {

    FlightMealResponse createFlightMeal(FlightMealRequest request);
    FlightMealResponse getFlightMealById(Long id);
    List<FlightMealResponse> getFlightId(Long flightId);
    List<FlightMealResponse> getAllByIds(List<Long> ids);
    FlightMealResponse updateFlightMeal(Long id , FlightMealRequest request);
    void deleteFlightMeal(Long id);
    FlightMealResponse updateFlightMealAvailability(Long id,Boolean availability);
    Double calculateMealPrice(List<Long> meadIds);
}
