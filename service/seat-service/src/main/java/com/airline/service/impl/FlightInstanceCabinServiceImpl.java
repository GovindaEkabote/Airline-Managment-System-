package com.airline.service.impl;

import com.airline.enums.SeatAvailabilityStatus;
import com.airline.enums.SeatType;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.FlightInstanceCabinMapper;
import com.airline.model.CabinClass;
import com.airline.model.FlightInstanceCabin;
import com.airline.model.SeatInstance;
import com.airline.model.SeatMap;
import com.airline.playload.request.FlightInstanceCabinRequest;
import com.airline.playload.response.FlightInstanceCabinResponse;
import com.airline.repository.CabinClassRepository;
import com.airline.repository.FlightInstanceCabinRepository;
import com.airline.repository.SeatInstanceRepository;
import com.airline.repository.SeatMapRepository;
import com.airline.service.FlightInstanceCabinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightInstanceCabinServiceImpl implements FlightInstanceCabinService {

    private final FlightInstanceCabinRepository flightInstanceCabinRepository;
    private final CabinClassRepository cabinClassRepository;
    private final SeatMapRepository seatMapRepository;
    private final SeatInstanceRepository seatInstanceRepository;

    @Override
    public FlightInstanceCabinResponse createFlightInstanceCabin(FlightInstanceCabinRequest request) {
        CabinClass cabinClass = cabinClassRepository.findById(
                request.getCabinClassId()
        ).orElseThrow(
                () -> new ResourceNotFoundException("Cabin Not Found"+ request.getCabinClassId())
        );
        SeatMap seatMap = seatMapRepository.findByCabinClassId(cabinClass.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Seat Map Id Not Found"));

        if (seatMap.getSeats() == null || seatMap.getSeats().isEmpty()){
            throw new ResourceNotFoundException("No Seat Found in Seat Map");
        }

        int totalSeate = seatMap.getSeats().size();

        FlightInstanceCabin cabin = FlightInstanceCabin.builder()
                .flightInstanceId(request.getFlightInstanceId())
                .cabinClass(cabinClass)
                .totalSeats(totalSeate)
                .bookedSeats(0)
                .build();

        FlightInstanceCabin saved = flightInstanceCabinRepository.save(cabin);

        List<SeatInstance> seatInstances = seatMap.getSeats().stream()
                .map(
                        seat -> {
                            BigDecimal premiumSupperCharge= getPremiumSuperCharge(
                                    seat.getSeatType(),
                                    BigDecimal.valueOf(1000),
                                    BigDecimal.valueOf(500)
                            );
                            SeatInstance seatInstance =
                                    SeatInstance.builder()
                                            .flightId(request.getFlightId())
                                            .status(SeatAvailabilityStatus.AVAILABLE)
                                            .flightInstanceId(request.getFlightInstanceId())
                                            .flightInstanceCabin(saved)
                                            .seat(seat)
                                            .isAvailable(true)
                                            .isBooked(false)
                                            .premiumSurcharge(premiumSupperCharge)
                                            .build();
                            return seatInstance;
                        }
                ).toList();

        seatInstanceRepository.saveAll(seatInstances);
        saved.setSeatInstances(seatInstances);
        return FlightInstanceCabinMapper.toResponse(saved);
    }

    @Override
    public FlightInstanceCabinResponse getFlightInstanceCabinById(Long id) {
        return FlightInstanceCabinMapper.toResponse(
                flightInstanceCabinRepository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException("Not Found")
                )
        );
    }

    @Override
    public Page<FlightInstanceCabinResponse> getFlightInstance(Long id, Pageable pageable) {
        return flightInstanceCabinRepository.findByFlightInstanceId(id, pageable)
                .map(FlightInstanceCabinMapper::toResponse);
    }

    @Override
    public FlightInstanceCabinResponse getByFlightInstanceIdAndCabinClassId(Long flightInstanceId, Long cabinClassId) {
        FlightInstanceCabin cabin =
                flightInstanceCabinRepository.findByFlightInstanceIdAndCabinClassId(
                        flightInstanceId,
                        cabinClassId
                );
        return FlightInstanceCabinMapper.toResponse(cabin);
    }

    @Override
    public FlightInstanceCabinResponse updateFlightInstanceCabin(Long id, FlightInstanceCabinRequest request) {

        FlightInstanceCabin cabin = flightInstanceCabinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));

        if (request.getCabinClassId() != null){
            CabinClass cabinClass=cabinClassRepository.findById(request.getCabinClassId())
                    .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
            cabin.setCabinClass(cabinClass);
        }
        FlightInstanceCabin updated = flightInstanceCabinRepository.save(cabin);
        return FlightInstanceCabinMapper.toResponse(updated);
    }

    @Override
    public void deleteFlightInstanceCabin(Long id) {
        FlightInstanceCabin cabin =
                flightInstanceCabinRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Not Found"));

        flightInstanceCabinRepository.delete(cabin);
    }

    private BigDecimal getPremiumSuperCharge(
            SeatType seatType,
            BigDecimal windowSuperCharge,
            BigDecimal aisleSuperCharge
    ){
        if (seatType == null){
            return BigDecimal.ZERO;
        }

        return switch (seatType){
            case AISLE -> aisleSuperCharge;
            case WINDOW -> windowSuperCharge;
            default -> BigDecimal.ZERO;
        };
    }
}
