package com.airline.service.impl;

import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.InsuranceConverageMapper;
import com.airline.model.Ancillary;
import com.airline.model.InsuranceCoverage;
import com.airline.playload.request.InsuranceCoverageRequest;
import com.airline.playload.response.InsuranceConverageResponse;
import com.airline.repository.AncillaryRepository;
import com.airline.repository.InsuranceCoverageRepository;
import com.airline.service.InsuranceCoverageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsuranceCoverageServiceImpl implements InsuranceCoverageService {

    private final InsuranceCoverageRepository insuranceCoverageRepository;
    private final AncillaryRepository ancillaryRepository;

    @Override
    public InsuranceConverageResponse createCoverage(InsuranceCoverageRequest request) {
        if (request == null){
            throw new IllegalArgumentException("Insurance Coverage request must not be null");
        }
        Ancillary ancillary = ancillaryRepository.findById(request.getAncillaryId())
                .orElseThrow(() -> new RuntimeException("Not Found"));
        InsuranceCoverage coverage = InsuranceConverageMapper.toEntity(request, ancillary);
        InsuranceCoverage save = insuranceCoverageRepository.save(coverage);
        return InsuranceConverageMapper.toResponse(save);
    }

    @Override
    public InsuranceConverageResponse updateCoverage(Long id, InsuranceCoverageRequest request) {
        InsuranceCoverage coverage = insuranceCoverageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));

        Ancillary ancillary = null;
        if (request.getAncillaryId() != null){
            ancillary = ancillaryRepository.findById(request.getAncillaryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        }

        InsuranceConverageMapper.updateEntity(coverage, request, ancillary);
        InsuranceCoverage saved = insuranceCoverageRepository.save(coverage);
        return InsuranceConverageMapper.toResponse(saved);
    }

    @Override
    public void deleteCoverage(Long id) {
        InsuranceCoverage coverage = insuranceCoverageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));

        insuranceCoverageRepository.delete(coverage);
    }

    @Override
    public InsuranceConverageResponse getCoverageById(Long id) {
        InsuranceCoverage coverage = insuranceCoverageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        return InsuranceConverageMapper.toResponse(coverage);
    }

    @Override
    public List<InsuranceConverageResponse> getCoveragesByAncillaryId(Long ancillaryId) {

        return insuranceCoverageRepository.findByAncillaryId(ancillaryId)
                .stream()
                .map(InsuranceConverageMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<InsuranceConverageResponse> getActiveCoverage(Long ancillaryId) {
        return insuranceCoverageRepository.findByAncillaryIdAndActiveTrue(ancillaryId)
                .stream()
                .map(InsuranceConverageMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<InsuranceConverageResponse> getAllCoverages() {
        return insuranceCoverageRepository.findAll()
                .stream()
                .map(
                        InsuranceConverageMapper::toResponse
                ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deactivateCoverage(Long id) throws Exception {

        InsuranceCoverage coverage = insuranceCoverageRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Insurance coverage not found with id: " + id
                        )
                );

        if (!coverage.getActive()) {
            throw new Exception(
                    "Insurance coverage is already deactivated"
            );
        }

        coverage.setActive(false);
    }

    @Override
    public void activateCoverage(Long id) throws Exception {
        InsuranceCoverage coverage = insuranceCoverageRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Insurance coverage not found with id: " + id
                        )
                );

        if (!coverage.getActive()) {
            throw new Exception(
                    "Insurance coverage is already deactivated"
            );
        }

        coverage.setActive(true);
    }
}
