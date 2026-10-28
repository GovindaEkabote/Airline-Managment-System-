package com.airline.service.impl;

import com.airline.config.feignClient.AirlineClient;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.AncillaryMapper;
import com.airline.mapper.InsuranceConverageMapper;
import com.airline.mapper.MealMapper;
import com.airline.model.Ancillary;
import com.airline.model.InsuranceCoverage;
import com.airline.model.Meal;
import com.airline.playload.request.MealRequest;
import com.airline.playload.response.AirlineResponse;
import com.airline.playload.response.AncillaryResponse;
import com.airline.playload.response.InsuranceConverageResponse;
import com.airline.playload.response.MealResponse;
import com.airline.repository.AncillaryRepository;
import com.airline.repository.InsuranceCoverageRepository;
import com.airline.repository.MealRepository;
import com.airline.service.MealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final AncillaryRepository ancillaryRepository;
    private final InsuranceCoverageRepository insuranceCoverageRepository;
    private final AirlineClient airlineClient;

    @Override
    public MealResponse createMeal(MealRequest request, Long userId) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Meal request must not be null"
            );
        }

        AirlineResponse airlineResponse = airlineClient.getAirlineByOwnerId(userId);

        if (airlineResponse == null) {
            throw new ResourceNotFoundException(
                    "Airline not found for owner ID: " + userId
            );
        }

        Ancillary ancillary = null;

        if (request.getAncillaryId() != null) {
            ancillary = ancillaryRepository.findById(request.getAncillaryId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Ancillary not found with ID: " + request.getAncillaryId()
                            )
                    );
        }

        Meal meal = MealMapper.toEntity(request, ancillary,airlineResponse.getOwnerId());

        Meal savedMeal = mealRepository.save(meal);

        // TODo:
        List<InsuranceCoverage> converageResponses =
                insuranceCoverageRepository.findByAncillaryId(meal.getId());
        List<InsuranceConverageResponse> responses = converageResponses
                .stream()
                .map(InsuranceConverageMapper::toResponse)
                .toList();

        AncillaryResponse ancillaryResponse = ancillary != null
                ? AncillaryMapper.toResponse(ancillary, responses)
                : null;


        return MealMapper.toResponse(
                savedMeal,
                ancillaryResponse,
                airlineResponse
        );
    }

    @Override
    public MealResponse updateMeal(Long userId, Long id, MealRequest request) throws AccessDeniedException {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Meal request must not be null"
            );
        }

        AirlineResponse airlineResponse = airlineClient.getAirlineByOwnerId(userId);

        if (airlineResponse == null) {
            throw new ResourceNotFoundException(
                    "Airline not found for owner ID: " + userId
            );
        }

        Long userIds = airlineResponse.getId();

        Meal meal = mealRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Meal not found with ID: " + id
                        )
                );

        // Verify ownership before updating
        if (!Objects.equals(meal.getAirlineId(), userIds)) {
            throw new AccessDeniedException(
                    "You are not authorized to update this meal"
            );
        }

        // Preserve the existing ancillary unless a new ID is provided.
        Ancillary ancillary = meal.getAncillary();

        if (request.getAncillaryId() != null) {
            ancillary = ancillaryRepository
                    .findById(request.getAncillaryId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Ancillary not found with ID: "
                                            + request.getAncillaryId()
                            )
                    );
            // Ensure ancillary belongs to the same airline
            if (!Objects.equals(
                    ancillary.getAirlineId(), userIds)) {
                throw new AccessDeniedException(
                        "This ancillary does not belong to your airline"
                );
            }
        }

        MealMapper.updateEntity(meal, request, ancillary);
        // Never change ownership based on request data
        meal.setAirlineId(userIds);
        Meal saved = mealRepository.save(meal);
        AncillaryResponse ancillaryResponse = ancillary != null
                ? AncillaryMapper.toResponse(ancillary, null)
                : null;


        return MealMapper.toResponse(
                saved,
                ancillaryResponse,
                airlineResponse);
    }

    @Override
    public MealResponse getMealById(Long id) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Meal not found with ID: " + id
                        )
                );

        // TODO
        List<InsuranceCoverage> converageResponses =
                insuranceCoverageRepository.findByAncillaryId(meal.getId());
        List<InsuranceConverageResponse> responses = converageResponses
                .stream()
                .map(InsuranceConverageMapper::toResponse)
                .toList();
        AncillaryResponse ancillaryResponse = meal.getAncillary() != null
                ? AncillaryMapper.toResponse(meal.getAncillary(), responses)
                : null;

        // Fetch through your airline service/client if airline details are required.
        AirlineResponse airlineResponse = null;

        return MealMapper.toResponse(
                meal,
                ancillaryResponse,
                airlineResponse
        );
    }

    @Override
    public List<MealResponse> getAllMeals() {
        AirlineResponse airlineResponse = null;
        return mealRepository.findAll()
                .stream()
                .map(this::toMealResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MealResponse> getMealsByAirlineId(Long userId) {
        AirlineResponse response =  airlineClient.getAirlineByOwnerId(userId);
        return mealRepository.findByAirlineId(response.getId())
                .stream()
                .map(this::toMealResponse)
                .toList();
    }

    @Override
    public List<MealResponse> getMealsByAncillaryId(Long ancillaryId) {
        return mealRepository.findByAncillary_Id(ancillaryId)
                .stream()
                .map(this::toMealResponse)
                .toList();
    }

    @Override
    public List<MealResponse> getAvailableMeals(Long airlineId) {
        return mealRepository
                .findByAirlineIdAndAvailableTrueAndActiveTrue(airlineId)
                .stream()
                .map(this::toMealResponse)
                .toList();
    }

    @Override
    public void activateMeal(Long id) {
        Meal meal = getMealEntityById(id);
        meal.setActive(true);
        mealRepository.save(meal);
    }

    @Override
    public void deactivateMeal(Long id) {
        Meal meal = getMealEntityById(id);
        meal.setActive(false);
        mealRepository.save(meal);
    }

    @Override
    public void deleteMeal(Long id) {
        Meal meal = getMealEntityById(id);
        mealRepository.delete(meal);
    }

    private Meal getMealEntityById(Long id) {
        return mealRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Meal not found with ID: " + id
                        )
                );
    }

    private MealResponse toMealResponse(Meal meal){
        AncillaryResponse ancillaryResponse = meal.getAncillary() != null
                ? AncillaryMapper.toResponse(meal.getAncillary(), null)
                : null;
        AirlineResponse airlineResponse = null;

        return MealMapper.toResponse(
                meal,
                ancillaryResponse,
                airlineResponse
        );
    }
}
