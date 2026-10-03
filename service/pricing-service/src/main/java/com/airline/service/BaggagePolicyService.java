package com.airline.service;

import com.airline.playload.request.BaggagePolicyRequest;
import com.airline.playload.response.BaggagePolicyResponse;

import java.util.List;

public interface BaggagePolicyService {

    BaggagePolicyResponse calculateBaggagePolicy(BaggagePolicyRequest request);
    BaggagePolicyResponse getBaggagePolicyById(Long id);
    BaggagePolicyResponse updateBaggagePolicy(Long id, BaggagePolicyRequest request);
    void deleteBaggagePolicy(Long id);
    List<BaggagePolicyResponse> getBaggagePoliciesByAirlineId(Long airlineId);
}
