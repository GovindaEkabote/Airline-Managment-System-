package com.airline.service.impl;

import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.FareMapper;
import com.airline.model.Fare;
import com.airline.playload.request.FareRequest;
import com.airline.playload.response.FareResponse;
import com.airline.repository.FareRepository;
import com.airline.service.FareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FareServiceImpl implements FareService {

    private final FareRepository fareRepository;


    @Override
    public FareResponse createFare(FareRequest fareRequest) {
        if (fareRepository.existsByFlightIdAndCabinClassId(
                fareRequest.getFlightId(), fareRequest.getCabinClassId(), fareRequest.getName())) {
            throw new RuntimeException("Fare already exists");
        }
        Fare fare = FareMapper.toEntity(fareRequest);
        return FareMapper.toResponse(fareRepository.save(fare));
    }

    @Override
    public FareResponse updateFare(Long id, FareRequest fareRequest) {
        Fare fare = fareRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fare not found"));

        if (fareRepository.existsByFlightIdAndCabinClassIdAndNameAndIdNot(
                fareRequest.getFlightId(),
                fareRequest.getCabinClassId(),
                fareRequest.getName(),
                fare.getId()
        ))

        FareMapper.updateEntity(fare, fareRequest);
        Fare updateFare = fareRepository.save(fare);
        return FareMapper.toResponse(updateFare);
    }

    @Override
    public FareResponse getFareById(Long id) {
        Fare fare = fareRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Not Found", "id", id)
                );
        return FareMapper.toResponse(fare);
    }

    @Override
    public List<FareResponse> getFaresByFlightCabinClassId(Long flightId, Long cabinClassId) {
        List<Fare> fares = fareRepository.findByFlightIdAndCabinClassId(flightId, cabinClassId);
        return fares.stream().map(FareMapper::toResponse).toList();
    }

    @Override
    public void deleteFare(Long id) {
        Fare fare =fareRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fare not found"));
        fareRepository.delete(fare);
    }

    @Override
    public List<FareResponse> getAllFares() {
        return fareRepository.findAll().stream().map(FareMapper::toResponse).toList();
    }

    @Override
    public Map<Long, FareResponse> getLowestFarePerFlight(
            List<Long> flightIds,
            Long cabinClassId) {

        if (flightIds == null || flightIds.isEmpty()) {
            return Map.of();
        }

        List<Fare> fares =
                fareRepository.findByFlightIdInAndCabinClassId(
                        flightIds,
                        cabinClassId
                );

        return fares.stream()
                .collect(Collectors.toMap(
                        Fare::getFlightId,
                        fare -> fare,
                        (existing, candidate) ->
                                candidate.getTotalPrice()
                                        .compareTo(existing.getTotalPrice()) < 0
                                        ? candidate
                                        : existing
                ))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> FareMapper.toResponse(entry.getValue())
                ));
    }

    @Override
    public Map<Long, FareResponse> getFaresByIds(List<Long> ids) {
        List<Fare> fares = fareRepository.findAllById(ids);
        return fares.stream().collect(Collectors.toMap(Fare::getId, FareMapper::toResponse));
    }
}
