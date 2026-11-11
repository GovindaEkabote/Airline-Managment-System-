package com.airline.event;

import com.airline.enums.SeatAvailabilityStatus;
import com.airline.enums.SeatType;
import com.airline.model.CabinClass;
import com.airline.model.FlightInstanceCabin;
import com.airline.model.Seat;
import com.airline.model.SeatInstance;
import com.airline.repository.CabinClassRepository;
import com.airline.repository.FlightInstanceCabinRepository;
import com.airline.repository.SeatInstanceRepository;
import com.airline.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightInstanceEventConsumer {

    private final CabinClassRepository cabinClassRepository;
    private final SeatRepository seatRepository;
    private final FlightInstanceCabinRepository flightInstanceCabinRepository;
    private final SeatInstanceRepository seatInstanceRepository;

    @KafkaListener(topics = "flight-instance-created", groupId = "seat-service-group")
    @Transactional
    public void handleFlightInstanceCreated(FlightInstanceCreatedEvent event){

        System.out.println(
                "Flight instance created"+
                        event.getFlightId()+"-"+
                        event.getFlightInstanceId()+"-"+
                        event.getAircraftId()
        );

        List<CabinClass> cabinClasses =
                cabinClassRepository.findByAircraftIdAndIsActiveTrueOrderByDisplayOrderAsc(event.getAircraftId());

        System.out.println(" --------------> "+cabinClasses.size());

        int totalSeatInstances  = 0;
        for (CabinClass cabinClass: cabinClasses){
            List<Seat> seats = cabinClass.getSeatMap() != null
                    ? seatRepository.findBySeatMapId(cabinClass.getSeatMap().getId())
                    : List.of();

            FlightInstanceCabin flightInstanceCabin = FlightInstanceCabin.builder()
                    .flightInstanceId(event.getFlightInstanceId())
                    .cabinClass(cabinClass)
                    .totalSeats(seats.size())
                    .bookedSeats(0)
                    .build();
            FlightInstanceCabin cabin =
                    flightInstanceCabinRepository.save(flightInstanceCabin);

            List<SeatInstance> seatInstances = seats.stream().map(
                    seat -> SeatInstance.builder()
                            .flightId(event.getFlightId())
                            .flightInstanceId(event.getFlightInstanceId())
                            .flightInstanceCabin(cabin)
                            .seat(seat)
                            .status(SeatAvailabilityStatus.AVAILABLE)
                            .isBooked(false)
                            .isAvailable(true)
                            .premiumSurcharge(getPremiumSuperCharge(seat.getSeatType(),
                                    BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0)))
                            .build()
            ).toList();

            seatInstanceRepository.saveAll(seatInstances);
            totalSeatInstances +=seatInstances.size();
         }
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
