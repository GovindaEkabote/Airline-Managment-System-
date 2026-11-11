package com.airline.event.listener;

import com.airline.config.feignclient.FlightClient;
import com.airline.config.feignclient.PriceClient;
import com.airline.config.feignclient.UserClient;
import com.airline.enums.BookingStatus;
import com.airline.event.PaymentCompletedEvent;
import com.airline.exception.ResourceNotFoundException;
import com.airline.model.Booking;
import com.airline.playload.dto.UserDto;
import com.airline.playload.response.FareResponse;
import com.airline.playload.response.FlightInstanceResponse;
import com.airline.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventListener {

    private final BookingRepository bookingRepository;
    private final FlightClient flightClient;
    private final PriceClient priceClient;
    private final UserClient userClient;

    @KafkaListener(topics = "payment-completed", groupId = "booking-service-group")
    public void handlePaymentCompleted(PaymentCompletedEvent event){

        Booking booking =bookingRepository.findById(event.getBookingId())
                .orElseThrow(() -> new RuntimeException(
                        "Booking not found: " + event.getBookingId()
                ));

        booking.setBookingStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

//        FlightInstanceResponse flightInstanceResponse =
//                flightClient.getFlightInstanceById(booking.getFlightInstanceId());
//        FareResponse fareResponse = priceClient.getFareById(booking.getFareId());
//        UserDto userDto = userClient.getUserByIds(booking.getUserId());

        // public event for seat service and notification service

    }

    @KafkaListener(topics = "payment-failed", groupId = "booking-service-group")
    public void handlePaymentFailed(PaymentCompletedEvent event){

        Booking booking =bookingRepository.findById(event.getBookingId())
                .orElseThrow(() -> new RuntimeException(
                        "Booking not found: " + event.getBookingId()
                ));
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}
