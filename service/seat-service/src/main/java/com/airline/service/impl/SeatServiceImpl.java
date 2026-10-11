package com.airline.service.impl;

import com.airline.enums.SeatType;
import com.airline.exception.ResourceNotFoundException;
import com.airline.model.Seat;
import com.airline.model.SeatMap;
import com.airline.playload.request.SeatRequest;
import com.airline.playload.response.SeatResponse;
import com.airline.repository.SeatMapRepository;
import com.airline.repository.SeatRepository;
import com.airline.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SeatMapRepository  seatMapRepository;

    @Override
    @Transactional
    public void generateSeatsForSeatMap(Long seatMapId) {

        SeatMap seatMap = seatMapRepository.findById(seatMapId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Seat map not found for ID: " + seatMapId
                        )
                );

        if (seatRepository.existsBySeatMapId(seatMapId)) {
            throw new IllegalArgumentException(
                    "Seats already exist for the given seat map ID: " + seatMapId
            );
        }

        int leftSeatsPerRow = seatMap.getLeftSeatsPerRow();
        int rightSeatsPerRow = seatMap.getRightSeatsPerRow();
        int totalRows = seatMap.getTotalRows();

        if (leftSeatsPerRow <= 0 || rightSeatsPerRow <= 0 || totalRows <= 0) {
            throw new IllegalArgumentException(
                    "Invalid seat map configuration for ID: " + seatMapId
            );
        }

        int seatsPerRow = leftSeatsPerRow + rightSeatsPerRow;

        List<Seat> generatedSeats = new ArrayList<>(
                totalRows * seatsPerRow
        );

        for (int row = 1; row <= totalRows; row++) {

            for (int column = 0; column < seatsPerRow; column++) {

                char seatLetter = getSeatLetter(column).charAt(0);

                SeatType seatType = getSeatType(
                        column,
                        leftSeatsPerRow,
                        rightSeatsPerRow
                );

                Seat seat = Seat.builder()
                        .seatNumber(row + String.valueOf(seatLetter))
                        .seatRow(row)
                        .columnLetter(seatLetter)
                        .seatType(seatType)
                        .seatMap(seatMap)
                        .cabinClass(seatMap.getCabinClass())
                        .basePrice(BigDecimal.ZERO)
                        .premiumSurcharge(BigDecimal.ZERO)
                        .isAvailable(true)
                        .isBlocked(false)
                        .isActive(true)
                        .isEmergencyExit(false)
                        .isExtraLegroom(false)
                        .isPowerOutlet(false)
                        .isTvScreen(false)
                        .createdDate(LocalDateTime.now())
                        .isExtraWidth(false)
                        .build();

                generatedSeats.add(seat);
            }
        }

        seatRepository.saveAll(generatedSeats);
    }

    /*
    * Generates a letter for the seat based on its column index.
    * */
    private String getSeatLetter(int column) {

        StringBuilder result = new StringBuilder();

        while (column >= 0) {
            result.insert(
                    0,
                    (char) ('A' + (column % 26))
            );

            column = (column / 26) - 1;
        }

        return result.toString();
    }

    private SeatType getSeatType(
            int column,
            int leftSeatsPerRow,
            int rightSeatsPerRow) {

        int totalSeats = leftSeatsPerRow + rightSeatsPerRow;

        if (column == 0 || column == totalSeats - 1) {
            return SeatType.WINDOW;
        }

        if (column == leftSeatsPerRow - 1 || column == leftSeatsPerRow) {
            return SeatType.AISLE;
        }

        return SeatType.MIDDLE;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatResponse> getAllSeats() {

        return seatRepository.findAll()
                .stream()
                .map(this::mapToSeatResponse)
                .toList();
    }

    @Override
    public SeatResponse updateSeat(Long seatId, SeatRequest request) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Seat not found for ID: " + seatId
                        )
                );
        seat.setSeatType(SeatType.valueOf(request.getSeatType()));
        seat.setBasePrice(request.getBasePrice());
        seat.setPremiumSurcharge(request.getPremiumSurcharge());

        seat.setIsAvailable(request.isAvailable());
        seat.setIsBlocked(request.isBlocked());
        seat.setIsActive(request.isActive());

        seat.setIsEmergencyExit(request.isEmergencyExit());
        seat.setIsExtraLegroom(request.isExtraLegroom());
        seat.setIsPowerOutlet(request.isPowerOutlet());
        seat.setIsTvScreen(request.isTvScreen());
        seat.setIsExtraWidth(request.isExtraWidth());

        seat.setSeatPitch(request.getSeatPitch());
        seat.setSeatWidth(request.getSeatWidth());

        Seat updatedSeat = seatRepository.save(seat);

        return mapToSeatResponse(updatedSeat);
    }


    private SeatResponse mapToSeatResponse(Seat seat) {

        return SeatResponse.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .seatRow(seat.getSeatRow())
                .columnLetter(seat.getColumnLetter())
                .seatType(seat.getSeatType().name())

                .basePrice(seat.getBasePrice())
                .premiumSurcharge(seat.getPremiumSurcharge())
                .totalPrice(seat.getTotalPrice())

                .available(seat.getIsAvailable())
                .blocked(seat.getIsBlocked())
                .active(Boolean.TRUE.equals(seat.getIsActive()))
                .bookable(seat.isBookable())

                .emergencyExit(seat.getIsEmergencyExit())
                .extraLegroom(seat.getIsExtraLegroom())
                .powerOutlet(seat.getIsPowerOutlet())
                .tvScreen(seat.getIsTvScreen())
                .extraWidth(seat.getIsExtraWidth())

                .seatPitch(seat.getSeatPitch())
                .seatWidth(seat.getSeatWidth())

                .seatMapId(seat.getSeatMap().getId())
                .cabinClassId(seat.getCabinClass().getId())
                .cabinClassName(seat.getCabinClass().getCode())
                .createdDate(seat.getCreatedDate())
                .lastModifiedDate(seat.getLastModifiedDate())
                .version(seat.getVersion())

                .build();
    }
}
