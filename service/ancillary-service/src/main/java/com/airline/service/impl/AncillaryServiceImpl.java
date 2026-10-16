package com.airline.service.impl;

import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.AncillaryMapper;
import com.airline.mapper.InsuranceConverageMapper;
import com.airline.model.Ancillary;
import com.airline.model.InsuranceCoverage;
import com.airline.playload.request.AncillaryRequest;
import com.airline.playload.response.AncillaryResponse;
import com.airline.playload.response.InsuranceConverageResponse;
import com.airline.repository.AncillaryRepository;
import com.airline.repository.InsuranceCoverageRepository;
import com.airline.service.AncillaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AncillaryServiceImpl implements AncillaryService {

    private final AncillaryRepository ancillaryRepository;
    private final InsuranceCoverageRepository insuranceCoverageRepository;

    @Override
    public AncillaryResponse createAncillary(Long airlineId, AncillaryRequest request) {

        if (request == null){
            throw new IllegalArgumentException("Ancillary request must not be null");
        }
        Ancillary ancillary = AncillaryMapper.toEntity(request, airlineId);

        Ancillary savedAncillary = ancillaryRepository.save(ancillary);

        List<InsuranceCoverage> converageResponses =
                insuranceCoverageRepository.findByAncillaryId(ancillary.getId());
        List<InsuranceConverageResponse> responses = converageResponses
                .stream()
                .map(InsuranceConverageMapper::toResponse)
                .toList();

        return AncillaryMapper.toResponse(savedAncillary,responses);
    }

    @Override
    public AncillaryResponse getbyId(Long id) throws Exception {

        Ancillary ancillary = ancillaryRepository.findById(id).orElseThrow(() -> new Exception("Not Found"));

        List<InsuranceCoverage> converageResponses =
                insuranceCoverageRepository.findByAncillaryId(ancillary.getId());
        List<InsuranceConverageResponse> responses = converageResponses
                .stream()
                .map(InsuranceConverageMapper::toResponse)
                .toList();
        return AncillaryMapper.toResponse(ancillary, responses);
    }

    @Override
    public List<AncillaryResponse> getAirlineId(Long id) {
        return ancillaryRepository.findByAirlineId(id)
                .stream()
                .map(
                        ancillary -> {
                            List<InsuranceCoverage> converageResponses =
                                    insuranceCoverageRepository.findByAncillaryId(ancillary.getId());
                            List<InsuranceConverageResponse> responses = converageResponses
                                    .stream()
                                    .map(InsuranceConverageMapper::toResponse)
                                    .toList();
                            return AncillaryMapper.toResponse(ancillary,responses);
                        }
                )
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public AncillaryResponse updateAncillary(
            Long id,
            AncillaryRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Ancillary request must not be null");
        }

        Ancillary ancillary = ancillaryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ancillary not found with id: " + id));

        // Update the existing entity
        AncillaryMapper.updateEntity(ancillary, request);

        // Save the updated entity
        Ancillary updatedAncillary =
                ancillaryRepository.save(ancillary);

        List<InsuranceCoverage> converageResponses =
                insuranceCoverageRepository.findByAncillaryId(ancillary.getId());
        List<InsuranceConverageResponse> responses = converageResponses
                .stream()
                .map(InsuranceConverageMapper::toResponse)
                .toList();
        // Convert entity to response DTO
        return AncillaryMapper.toResponse(updatedAncillary, responses);
    }


    @Override
    public void deleteAncillary(Long id) {
        Ancillary ancillary
                = ancillaryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found"));

        ancillaryRepository.delete(ancillary);
    }
}
