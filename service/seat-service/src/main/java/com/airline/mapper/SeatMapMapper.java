package com.airline.mapper;

import com.airline.model.CabinClass;
import com.airline.model.Seat;
import com.airline.model.SeatMap;
import com.airline.playload.request.SeatMapRequest;
import com.airline.playload.response.SeatMapResponse;

import java.util.List;

public class SeatMapMapper {

    private SeatMapMapper() {
        // Utility class
    }

    public static SeatMap toEntity(
            SeatMapRequest request,
            CabinClass cabinClass
    ) {
        if (request == null) {
            return null;
        }

        return SeatMap.builder()
                .name(request.getName().trim())
                .totalRows(request.getTotalRows())
                .rightSeatsPerRow(request.getRightSeatsPerRow())
                .leftSeatsPerRow(request.getLeftSeatsPerRow())
                .cabinClass(cabinClass)
                .build();
    }

    public static SeatMapResponse toResponse(SeatMap map) {

        if (map == null) {
            return null;
        }

        List<Seat> seats = map.getSeats();
        CabinClass cabin = map.getCabinClass();

        int totalSeats = seats == null ? 0 : seats.size();

        int availableSeats = count(
                seats,
                Seat::isBookable
        );

        int windowSeats = count(
                seats,
                Seat::isWindowSeat
        );

        int aisleSeats = count(
                seats,
                Seat::isAisleSeat
        );

        int middleSeats = count(
                seats,
                Seat::isMiddleSeat
        );

        int premiumSeats = count(
                seats,
                seat -> seat.isBookable() && seat.isPremiumSeat()
        );

        int emergencyExitSeats = count(
                seats,
                Seat::isEmergencyExitSeat
        );



        return SeatMapResponse.builder()
                .id(map.getId())
                .name(map.getName())
                .totalRows(map.getTotalRows())
                .leftSeatsPerRow(map.getLeftSeatsPerRow())
                .rightSeatsPerRow(map.getRightSeatsPerRow())
                .airlineId(map.getAirlineId())
                .cabinClassId(cabin != null ? cabin.getId() : null)
                .cabinClassName(cabin != null ? cabin.getName() : null)
                .cabinClassCode(cabin != null ? cabin.getCode() : null)
                .createdAt(map.getCreatedAt())
                .updatedAt(map.getUpdatedAt())

                // Add these when your SeatMapResponse has these fields
                 .totalSeats(totalSeats)
                 .availableSeats(availableSeats)
                 .windowsSeats(windowSeats)
                 .aisleSeats(aisleSeats)
                 .middleSeats(middleSeats)
                 .premiumSeats(premiumSeats)
                 .emergencyExitSeats(emergencyExitSeats)

                .build();
    }

    public static SeatMapResponse toSimpleResponse(SeatMap seatMap) {

        if (seatMap == null) {
            return null;
        }

        return SeatMapResponse.builder()
                .totalRows(seatMap.getTotalRows())
                .leftSeatsPerRow(seatMap.getLeftSeatsPerRow())
                .rightSeatsPerRow(seatMap.getRightSeatsPerRow())
                .build();
    }

    public static void updateEntity(
            SeatMapRequest request,
            SeatMap seatMap
    ) {

        if (request == null || seatMap == null) {
            return;
        }

        seatMap.setName(request.getName().trim());
        seatMap.setTotalRows(request.getTotalRows());
        seatMap.setLeftSeatsPerRow(request.getLeftSeatsPerRow());
        seatMap.setRightSeatsPerRow(request.getRightSeatsPerRow());
    }

    private static int count(
            List<Seat> seats,
            java.util.function.Predicate<Seat> condition
    ) {
        if (seats == null) {
            return 0;
        }

        return (int) seats.stream()
                .filter(condition)
                .count();
    }
}