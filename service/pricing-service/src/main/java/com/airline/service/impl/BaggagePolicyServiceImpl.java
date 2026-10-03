package com.airline.service.impl;

import com.airline.mapper.BaggagePolicyMapper;
import com.airline.model.BaggagePolicy;
import com.airline.model.Fare;
import com.airline.playload.request.BaggagePolicyRequest;
import com.airline.playload.response.BaggagePolicyResponse;
import com.airline.repository.BaggagePolicyRepository;
import com.airline.repository.FareRepository;
import com.airline.service.BaggagePolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BaggagePolicyServiceImpl implements BaggagePolicyService {

    private final BaggagePolicyRepository baggagePolicyRepository;
    private final FareRepository fareRepository;

    @Override
    public BaggagePolicyResponse calculateBaggagePolicy(BaggagePolicyRequest request) {
        // Implementation for calculating baggage policy

        Fare fare = fareRepository.findById(request.getFareId())
                .orElseThrow(() -> new RuntimeException("Fare not found with id: " + request.getFareId()));

        if (baggagePolicyRepository.existsByFareId(fare.getId())) {
            throw new RuntimeException("Baggage policy already exists for fare id: " + fare.getId());
        }
        BaggagePolicy baggagePolicy = BaggagePolicyMapper.toEntity(request, fare);
        BaggagePolicy savedBaggagePolicy = baggagePolicyRepository.save(baggagePolicy);
        return BaggagePolicyMapper.toResponse(savedBaggagePolicy);
    }

    @Override
    public BaggagePolicyResponse getBaggagePolicyById(Long id) {
        BaggagePolicy baggagePolicy = baggagePolicyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Baggage policy not found with id: " + id));
        return BaggagePolicyMapper.toResponse(baggagePolicy);
    }

    @Override
    public BaggagePolicyResponse updateBaggagePolicy(Long id, BaggagePolicyRequest request) {
        BaggagePolicy existingBaggagePolicy = baggagePolicyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Baggage policy not found with id: " + id));
        BaggagePolicy updatedBaggagePolicy = BaggagePolicyMapper.toEntity(request, existingBaggagePolicy.getFare());
        updatedBaggagePolicy.setId(id);
        BaggagePolicy savedBaggagePolicy = baggagePolicyRepository.save(updatedBaggagePolicy);
        return BaggagePolicyMapper.toResponse(savedBaggagePolicy);
    }

    @Override
    public void deleteBaggagePolicy(Long id) {
        baggagePolicyRepository.deleteById(id);
    }

    @Override
    public List<BaggagePolicyResponse> getBaggagePoliciesByAirlineId(Long airlineId) {
        return baggagePolicyRepository.findAllByAirlineId(airlineId).stream()
                .map(BaggagePolicyMapper::toResponse)
                .collect(Collectors.toList());
    }
}
