package com.airline.service.impl;

import com.airline.enums.SeatAvailabilityStatus;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.SeatInstanceMapper;
import com.airline.model.SeatInstance;
import com.airline.playload.response.SeatInstanceResponse;
import com.airline.repository.SeatInstanceRepository;
import com.airline.service.SeatInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatInstanceServiceImpl implements SeatInstanceService {

    private final SeatInstanceRepository seatInstanceRepository;


    @Override
    public BigDecimal calculateSeatPrice(List<Long> seatInstanceIds) {

        if (seatInstanceIds == null || seatInstanceIds.isEmpty()) {
            throw new IllegalArgumentException(
                    "Seat instance IDs must not be empty"
            );
        }

        List<SeatInstance> seatInstances =
                seatInstanceRepository.findAllById(seatInstanceIds);

        if (seatInstances.size() != seatInstanceIds.size()) {
            throw new IllegalArgumentException(
                    "One or more seat instances were not found: "
                            + seatInstanceIds
            );
        }

        return seatInstances.stream()
                .map(instance -> instance.getPremiumSurcharge() != null
                        ? instance.getPremiumSurcharge()
                        : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public SeatInstanceResponse updateSeatInstanceResponse(
            Long seatInstanceId,
            SeatAvailabilityStatus status
    ) {

        SeatInstance seatInstance = seatInstanceRepository.findById(seatInstanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat Instance ID not Found"));

        seatInstance.setStatus(status);
        seatInstanceRepository.save(seatInstance);
        return SeatInstanceMapper.toResponse(seatInstance);
    }

}
