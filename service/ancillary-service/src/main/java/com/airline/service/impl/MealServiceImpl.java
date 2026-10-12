package com.airline.service.impl;

import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.AncillaryMapper;
import com.airline.mapper.MealMapper;
import com.airline.model.Ancillary;
import com.airline.model.Meal;
import com.airline.playload.request.MealRequest;
import com.airline.playload.response.AirlineResponse;
import com.airline.playload.response.AncillaryResponse;
import com.airline.playload.response.MealResponse;
import com.airline.repository.AncillaryRepository;
import com.airline.repository.MealRepository;
import com.airline.service.MealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final AncillaryRepository ancillaryRepository;

    @Override
    public MealResponse createMeal(MealRequest request) {

        Ancillary ancillary = null;

        if (request.getAncillaryId() != null) {
            ancillary = ancillaryRepository.findById(request.getAncillaryId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Ancillary not found with ID: " + request.getAncillaryId()
                            )
                    );
        }

        Meal meal = MealMapper.toEntity(request, ancillary);

        Meal savedMeal = mealRepository.save(meal);

        AncillaryResponse ancillaryResponse = ancillary != null
                ? AncillaryMapper.toResponse(ancillary, null)
                : null;

        // Meal stores airlineId, not an Airline entity.
        // Fetch AirlineResponse through your airline service/client if required.
        AirlineResponse airlineResponse = null;

        return MealMapper.toResponse(
                savedMeal,
                ancillaryResponse,
                airlineResponse
        );
    }

    @Override
    public MealResponse updateMeal(Long id, MealRequest request) {
        return null;
    }

    @Override
    public MealResponse getMealById(Long id) {
        return null;
    }

    @Override
    public List<MealResponse> getAllMeals() {
        return List.of();
    }

    @Override
    public List<MealResponse> getMealsByAirlineId(Long airlineId) {
        return List.of();
    }

    @Override
    public List<MealResponse> getMealsByAncillaryId(Long ancillaryId) {
        return List.of();
    }

    @Override
    public List<MealResponse> getAvailableMeals(Long airlineId) {
        return List.of();
    }

    @Override
    public void activateMeal(Long id) {

    }

    @Override
    public void deactivateMeal(Long id) {

    }

    @Override
    public void deleteMeal(Long id) {

    }
}
