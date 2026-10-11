package com.airline.service.impl;

import com.airline.mapper.AncillaryMapper;
import com.airline.model.Ancillary;
import com.airline.playload.request.AncillaryRequest;
import com.airline.playload.response.AncillaryResponse;
import com.airline.repository.AncillaryRepository;
import com.airline.service.AncillaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AncillaryServiceImpl implements AncillaryService {

    private final AncillaryRepository ancillaryRepository;

    @Override
    public AncillaryResponse createAncillary(Long airlineId, AncillaryRequest request) {

        if (request == null){
            throw new IllegalArgumentException("Ancillary request must not be null");
        }
        Ancillary ancillary = AncillaryMapper.toEntity(request, airlineId);

        Ancillary savedAncillary = ancillaryRepository.save(ancillary);

        return AncillaryMapper.toResponse(savedAncillary,null);
    }

    @Override
    public AncillaryResponse getbyId(Long id) throws Exception {

        Ancillary ancillary = ancillaryRepository.findById(id).orElseThrow(() -> new Exception("Not Found"));

        // toDo: fetch insurance coverages by ancillary
        return AncillaryMapper.toResponse(ancillary, null);
    }

    @Override
    public List<AncillaryResponse> getAirlineId(Long id, AncillaryRequest request) {
        return List.of();
    }

    @Override
    public AncillaryResponse updateAncillary(Long id, AncillaryRequest request) {
        return null;
    }

    @Override
    public void deleteAncillary(Long id) {

    }
}
