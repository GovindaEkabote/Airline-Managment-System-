package com.airline.service.impl;

import com.airline.exception.DuplicateResourceException;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.FareRolesMapper;
import com.airline.model.Fare;
import com.airline.model.FareRoles;
import com.airline.playload.request.FareRolesRequest;
import com.airline.playload.response.FareRuleResponse;
import com.airline.repository.FareRepository;
import com.airline.repository.FareRolesRepository;
import com.airline.service.FareRolesService;
import com.airline.service.FareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FareRolesServiceImpl implements FareRolesService {

    private final FareRolesRepository fareRolesRepository;
    private final FareRepository fareRepository;

    @Override
    public FareRuleResponse createFareRoles(FareRolesRequest request) {
        Fare fare = fareRepository.findById(request.getFareId())
                .orElseThrow(() -> new ResourceNotFoundException("Fare not found with id: ", "fareId", request.getFareId()));

        if (fareRolesRepository.existsByFareId(fare.getId())) {
            throw new DuplicateResourceException("Fare roles already exist for fare id: ", "fareId", fare.getId());
        }
        FareRoles fareRoles = FareRolesMapper.toEntity(request, fare);
        fareRoles = fareRolesRepository.save(fareRoles);

        return FareRolesMapper.toResponse(fareRoles);
    }

    @Override
    public FareRuleResponse getFareRolesById(Long id) {
        FareRoles fareRoles = fareRolesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fare roles not found with id: ", "id", id));
        return FareRolesMapper.toResponse(fareRoles);
    }

    @Override
    public FareRuleResponse updateFareRoles(Long id, FareRolesRequest request) {
        FareRoles fareRoles = fareRolesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fare roles not found with id: ", "id", id));
        // Update the fareRoles entity with values from the request
        FareRolesMapper.updateEntity(fareRoles, request);
        fareRoles = fareRolesRepository.save(fareRoles);
        return FareRolesMapper.toResponse(fareRoles);
    }

    @Override
    public void deleteFareRoles(Long id) {
        FareRoles fareRoles = fareRolesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fare roles not found with id: ", "id", id));
        fareRolesRepository.delete(fareRoles);
    }

    @Override
    public FareRuleResponse getFareRolesByFareId(Long fareId) {
        FareRoles fareRoles = fareRolesRepository.findByFareId(fareId);
        if (fareRoles != null) {
            return FareRolesMapper.toResponse(fareRoles);
        }
        return null;
    }

    @Override
    public List<FareRuleResponse> getFareRolesByAirlineId(Long airlineId) {
        return fareRolesRepository.findByAirlineId(airlineId).stream()
                .map(FareRolesMapper::toResponse)
                .toList();
    }
}
