package com.airline.service;

import com.airline.playload.request.AncillaryRequest;
import com.airline.playload.response.AncillaryResponse;

import java.util.List;

public interface AncillaryService {

    AncillaryResponse createAncillary(Long airlineId, AncillaryRequest request);
    AncillaryResponse getbyId(Long id) throws Exception;
    List<AncillaryResponse> getAirlineId(
            Long id,
            AncillaryRequest request
    );
    AncillaryResponse updateAncillary(Long id, AncillaryRequest request);
    void deleteAncillary(Long id);

}
