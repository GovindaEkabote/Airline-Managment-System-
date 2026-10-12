package com.airline.service;

import com.airline.playload.request.InsuranceCoverageRequest;
import com.airline.playload.response.InsuranceConverageResponse;

import java.util.List;

public interface InsuranceCoverageService {

    InsuranceConverageResponse createCoverage(
            InsuranceCoverageRequest request
    );

    InsuranceConverageResponse updateCoverage(
            Long id,
            InsuranceCoverageRequest request
    );

    void deleteCoverage(Long id);

    InsuranceConverageResponse getCoverageById(Long id);

    List<InsuranceConverageResponse> getCoveragesByAncillaryId(
            Long ancillaryId
    );

    List<InsuranceConverageResponse> getActiveCoverage(Long ancillaryId);

    List<InsuranceConverageResponse> getAllCoverages();

    void deactivateCoverage(Long id) throws Exception;

    void activateCoverage(Long id) throws Exception;
}
