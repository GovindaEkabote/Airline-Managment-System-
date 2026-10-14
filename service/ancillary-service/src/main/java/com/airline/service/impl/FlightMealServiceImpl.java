package com.airline.service.impl;

import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.FlightMealMapper;
import com.airline.model.FlightMeal;
import com.airline.model.Meal;
import com.airline.playload.request.FlightMealRequest;
import com.airline.playload.response.FlightMealResponse;
import com.airline.repository.FlightMealRepository;
import com.airline.repository.MealRepository;
import com.airline.service.FlightMealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/*
  * Flight Meal Service Implementation....
*/

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightMealServiceImpl implements FlightMealService {

    private final FlightMealRepository flightMealRepository;
    private final MealRepository mealRepository;

    @Override
    public FlightMealResponse createFlightMeal(FlightMealRequest request) {
        Meal meal  = mealRepository.findById(request.getMealId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Not Found")
                );
        if (flightMealRepository.existsByFlightIdAndMealId(request.getFlightId(), request.getMealId())){
            throw new IllegalArgumentException(
                    "Meal Already Exist for this Flight"
            );
        }
        FlightMeal flightMeal = FlightMealMapper.toEntity(request, meal);

        FlightMeal saved = flightMealRepository.save(flightMeal);

        return FlightMealMapper.toResponse(saved);
    }


    @Override
    public FlightMealResponse getFlightMealById(Long id) {
        FlightMeal meal = flightMealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        return FlightMealMapper.toResponse(meal);
    }

    @Override
    public List<FlightMealResponse> getFlightId(Long flightId) {
        return flightMealRepository.findByFlightId(flightId)
                .stream()
                .map(FlightMealMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightMealResponse> getAllByIds(List<Long> ids) {
        return flightMealRepository.findAllById(ids)
                .stream()
                .map(FlightMealMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FlightMealResponse updateFlightMeal(Long id, FlightMealRequest request) {
        FlightMeal flightMeal = flightMealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        flightMeal.setFlightId(request.getFlightId());

        if (request.getMealId() != null){
            Meal meal=mealRepository.findById(request.getMealId())
                    .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        }
        flightMeal.setAvailable(request.getAvailable());
        flightMeal.setPrice(request.getPrice());
        flightMeal.setDisplayOrder(request.getDisplayOrder());
        FlightMeal saved = flightMealRepository.save(flightMeal);
        return FlightMealMapper.toResponse(saved);
    }

    @Override
    public void deleteFlightMeal(Long id) {
        FlightMeal flightMeal = flightMealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));

        flightMealRepository.delete(flightMeal);
    }

    @Override
    public FlightMealResponse updateFlightMealAvailability(Long id, Boolean availability) {
        FlightMeal flightMeal = flightMealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        flightMeal.setAvailable(availability);
        flightMealRepository.save(flightMeal);
        return FlightMealMapper.toResponse(flightMeal);
    }

    @Override
    public Double calculateMealPrice(List<Long> meadIds) {
        List<FlightMeal> meals =flightMealRepository.findAllById(meadIds);
        double price = 0.0;
        for (FlightMeal meal:meals){
            price = price + meal.getPrice();
        }
        return price;
    }
}
