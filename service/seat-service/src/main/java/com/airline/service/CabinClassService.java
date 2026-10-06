package com.airline.service;

import com.airline.enums.CabinClassType;
import com.airline.playload.request.CabinClassRequest;
import com.airline.playload.response.CabinClassResponse;

import java.util.List;

public interface CabinClassService {

    CabinClassResponse create(CabinClassRequest request);

    CabinClassResponse getById(Long id);

    List<CabinClassResponse> getAircraftById(Long aircraftId);

    CabinClassResponse update(Long id, CabinClassRequest request);

    CabinClassResponse setActive(Long id, boolean active);


    void delete(Long id);
}
