package com.airline.service.impl;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.CabinClassMapper;
import com.airline.model.CabinClass;
import com.airline.playload.request.CabinClassRequest;
import com.airline.playload.response.CabinClassResponse;
import com.airline.repository.CabinClassRepository;
import com.airline.service.CabinClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CabinClassServiceImpl implements CabinClassService {

    private final CabinClassRepository cabinClassRepository;

    @Override
    public CabinClassResponse create(CabinClassRequest request) {
        if (cabinClassRepository.existsByAircraftIdAndCode(request.getAircraftId(),request.getCode())){
            throw new RuntimeException(
                    "Cabin class with aircraftId "
                            + request.getAircraftId()
                            + " and code "
                            + request.getCode()
                            + " already exists"
            );
        }
        CabinClass cabinClass = CabinClassMapper.toEntity(request);

        CabinClass savedCabinClass = cabinClassRepository.save(cabinClass);
        return CabinClassMapper.toResponse(savedCabinClass);
    }

    @Override
    public CabinClassResponse getById(Long id) {

        CabinClass cabinClass = cabinClassRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not Found")
        );
        return CabinClassMapper.toResponse(cabinClass);
    }

    @Override
    public List<CabinClassResponse> getAircraftById(Long aircraftId) {
        return cabinClassRepository.findByAircraftIdOrderByDisplayOrderAsc(aircraftId)
                .stream()
                .map(CabinClassMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CabinClassResponse update(Long id, CabinClassRequest request) {
        CabinClass cabinClass = cabinClassRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                        "Airline not found with owner id: " + id
                ));
        if (cabinClassRepository.existsByAircraftIdAndCodeAndIdNot(
                request.getAircraftId(),
                request.getCode(),
                id
        )) {
            throw new RuntimeException(
                    "Cabin class with aircraftId "
                            + request.getAircraftId()
                            + " and code "
                            + request.getCode()
                            + " already exists"
            );
        }
        CabinClassMapper.updateEntity(cabinClass, request);
        CabinClass saveUpdatedCabinClass = cabinClassRepository.save(cabinClass);
        return CabinClassMapper.toResponse(saveUpdatedCabinClass);
    }

    @Override
    public CabinClassResponse setActive(Long id, boolean active) {
        CabinClass cabinClass = cabinClassRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cabin class not found with id: " + id
                        ));
        cabinClass.setIsActive(active);
        CabinClass updatedCabinClass =
                cabinClassRepository.save(cabinClass);
        return CabinClassMapper.toResponse(updatedCabinClass);
    }

    @Override
    public void delete(Long id) {
        CabinClass cabinClass = cabinClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cabin class not found with id: " + id
                ));

        cabinClassRepository.delete(cabinClass);
    }
}
