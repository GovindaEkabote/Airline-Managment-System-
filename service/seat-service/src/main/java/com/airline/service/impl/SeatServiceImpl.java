package com.airline.service.impl;

import com.airline.enums.SeatType;
import com.airline.mapper.SeatMapper;
import com.airline.model.Seat;
import com.airline.model.SeatMap;
import com.airline.playload.request.SeatRequest;
import com.airline.playload.response.SeatResponse;
import com.airline.repository.SeatMapRepository;
import com.airline.repository.SeatRepository;
import com.airline.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SeatMapRepository  seatMapRepository;

    @Override
    public void generateSeatsForSeatMap(Long seatMapId) {
        boolean exists = seatRepository.existsBySeatMapId(seatMapId);
        if (exists) {
            throw new IllegalArgumentException("Seats already exist for the given seat map ID: " + seatMapId);
        }

        // Logic to generate seats for the given seatMapId
        SeatMap seatMap = seatMapRepository.findById(seatMapId)
                .orElseThrow(() -> new IllegalArgumentException("Seat map not found for ID: " + seatMapId));

        // Example logic to generate seats based on the seat map configuration
        int leftSeatsPerRow = seatMap.getLeftSeatsPerRow();
        int rightSeatsPerRow = seatMap.getRightSeatsPerRow();
        int rows = seatMap.getTotalRows();
        int seatsPerRow = leftSeatsPerRow + rightSeatsPerRow;

        List<Seat> generatedSeats = new ArrayList<>();
        for (int row = 1; row <= rows; row++) {
            for (int column = 0; column < seatsPerRow; column++) {
                String seatNumber = row + getSeatLetter(column);
                SeatType type = getSeatType(column, leftSeatsPerRow, rightSeatsPerRow);

                Seat seat = Seat.builder()
                        .seatNumber(seatNumber)
                        .seatRow(row)
                        .columnLetter(getSeatLetter(column).charAt(0))
                        .seatType(type)
                        .seatMap(seatMap)
                        .build();
                generatedSeats.add(seat);
            }
        }
        seatRepository.saveAll(generatedSeats);
    }

    private String getSeatLetter(int column) {
        StringBuilder stringBuilder = new StringBuilder();
        while (column >= 0) {
            stringBuilder.insert(0, (char) ('A' + (column % 26)));
            column = (column / 26) - 1;
        }
        return stringBuilder.toString();
    }
    private  SeatType getSeatType(int column, int leftSeatsPerRow, int rightSeatsPerRow) {
        if (column < leftSeatsPerRow) {
            return SeatType.WINDOW;
        } else if (column >= leftSeatsPerRow && column < leftSeatsPerRow + rightSeatsPerRow) {
            return SeatType.AISLE;
        } else {
            return SeatType.MIDDLE;
        }
    }

    @Override
    public List<SeatResponse> getAllSeats() {
        return null;
    }

    @Override
    public SeatResponse updateSeat(Long seatId, SeatRequest request) {
        return null;
    }
}
