package com.airline.service.impl;

import com.airline.enums.AncillaryType;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.FlightCabinAncillaryMapper;
import com.airline.mapper.InsuranceConverageMapper;
import com.airline.model.Ancillary;
import com.airline.model.FlightCabinAncillary;
import com.airline.model.InsuranceCoverage;
import com.airline.playload.request.AncillaryRequest;
import com.airline.playload.request.FlightCabinAncillaryRequest;
import com.airline.playload.response.FlightCabinAncillaryResponse;
import com.airline.playload.response.InsuranceConverageResponse;
import com.airline.repository.AncillaryRepository;
import com.airline.repository.FlightCabinAncillaryRepository;
import com.airline.repository.InsuranceCoverageRepository;
import com.airline.service.FlightCabinAncillaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightCabinAncillaryServiceImpl implements FlightCabinAncillaryService {

    private final FlightCabinAncillaryRepository flightCabinAncillaryRepository;
    private final AncillaryRepository ancillaryRepository;
    private final InsuranceCoverageRepository insuranceCoverageRepository;

    @Override
    public FlightCabinAncillaryResponse create(FlightCabinAncillaryRequest request) {
        Ancillary ancillary = ancillaryRepository.findById(request.getAncillaryId())
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));

        FlightCabinAncillary flightCabinAncillary = FlightCabinAncillaryMapper.toEntity(request,ancillary);
        FlightCabinAncillary save = flightCabinAncillaryRepository.save(flightCabinAncillary);

        return convertToResponse(save);
    }

    @Override
    public FlightCabinAncillaryResponse getById(Long id) {
        FlightCabinAncillary flightCabinAncillary = flightCabinAncillaryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        return FlightCabinAncillaryMapper.toResponse(flightCabinAncillary, null);
    }

    @Override
    public List<FlightCabinAncillaryResponse> getByFlightAndCabinClass(Long flightId, Long cabinClassId) {
        return flightCabinAncillaryRepository
                .findByFlightIdAndCabinClassId(flightId,cabinClassId)
                .stream()
                .map(
                    this::convertToResponse
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightCabinAncillaryResponse> getAllByIds(List<Long> ids) {
        return flightCabinAncillaryRepository.findAllById(ids)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    @Override
    public FlightCabinAncillaryResponse getByFlightIdAndCabinClassAndtype(
            Long flightId,
            Long cabinClassId,
            AncillaryType ancillaryType) {

        FlightCabinAncillary flightCabinAncillary =
                flightCabinAncillaryRepository
                        .findByFlightIdAndCabinClassIdAndAncillary_AncillaryType(
                                flightId,
                                cabinClassId,
                                ancillaryType
                        )
                        .stream()
                        .findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Flight cabin ancillary not found for flightId: "
                                        + flightId
                                        + ", cabinClassId: "
                                        + cabinClassId
                                        + ", ancillaryType: "
                                        + ancillaryType
                        ));

        return convertToResponse(flightCabinAncillary);
    }

    @Override
    public List<FlightCabinAncillaryResponse>
    getAllByFlightIdAndCabinClassIdAndType(
            Long flightId,
            Long cabinClassId,
            AncillaryType ancillaryType) {

        return flightCabinAncillaryRepository
                .findByFlightIdAndCabinClassIdAndAncillary_AncillaryType(
                        flightId,
                        cabinClassId,
                        ancillaryType
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    @Override
    public FlightCabinAncillaryResponse update(Long id, FlightCabinAncillaryRequest request) {
        FlightCabinAncillary flightCabinAncillary = flightCabinAncillaryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        flightCabinAncillary.setAvailable(request.getAvailable());
        flightCabinAncillary.setMaxQuantity(request.getMaxQuantity());
        flightCabinAncillary.setPrice(request.getPrice());
        flightCabinAncillary.setIncludedInFare(request.getIncludedInFare());
        FlightCabinAncillary saved = flightCabinAncillaryRepository.save(flightCabinAncillary);
        return convertToResponse(saved);
    }

    @Override
    public void delete(Long id) {
        FlightCabinAncillary flightCabinAncillary = flightCabinAncillaryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        flightCabinAncillaryRepository.delete(flightCabinAncillary);
    }

    @Override
    public Double calculateAncillaryPrice(List<Long> ancillaryIds) {
        List<FlightCabinAncillary> ancillaries
                = flightCabinAncillaryRepository.findAllById(ancillaryIds);
        double totalPrice = 0.0;
        for (FlightCabinAncillary flightCabinAncillary : ancillaries){
            totalPrice = totalPrice + flightCabinAncillary.getPrice();
        }
        return totalPrice;
    }

    private FlightCabinAncillaryResponse convertToResponse(FlightCabinAncillary flightCabinAncillary){
        List<InsuranceCoverage> coverages = insuranceCoverageRepository.findByAncillaryId(flightCabinAncillary.getId());
        List<InsuranceConverageResponse> converageResponses = coverages
                .stream()
                .map(InsuranceConverageMapper::toResponse)
                .collect(Collectors.toList()
                );

        return FlightCabinAncillaryMapper.toResponse(flightCabinAncillary, converageResponses);
    }
}
