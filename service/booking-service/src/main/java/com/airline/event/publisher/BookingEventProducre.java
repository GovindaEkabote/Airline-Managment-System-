package com.airline.event.publisher;

import com.airline.event.BookingConfirmationEvent;
import com.airline.event.PassengerNotification;
import com.airline.event.PaymentCompletedEvent;
import com.airline.model.Booking;
import com.airline.model.Ticket;
import com.airline.playload.dto.UserDto;
import com.airline.playload.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookingEventProducre {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendBookingConfirmedEvent(
            Booking booking,
            PaymentCompletedEvent payment,
            FlightInstanceResponse flight,
            FareResponse fare,
            UserDto user){

        Map<Long, String> ticketByPassenger=booking
                .getTickets()
                .stream()
                .filter(t -> t.getPassenger() != null )
                .collect(Collectors.toMap(
                        t-> t.getPassenger().getId(), Ticket::getTicketNumber,
                        (a,b) -> a
                ));

        List<PassengerNotification> passengerNotifications =
                booking.getPassengers()
                        .stream()
                        .map(p -> PassengerNotification.builder()
                                .firstName(p.getFirstName())
                                .lastName(p.getLastName())
                                .ticketNumber(ticketByPassenger.getOrDefault(p.getId(), "N/A"))
                                .passportNumber(null)
                                .nationality(p.getNationality())
                                .gender(p.getGender() != null ? p.getGender().name(): "")
                                .adult(p.isAdult())
                                .build()
                        ).collect(Collectors.toList());

        String contactEmail = booking.getContactInfo() != null
                ? booking.getContactInfo().getEmail() : null;

        String contactPhone = booking.getContactInfo() != null
                ? booking.getContactInfo().getPhone() : null;

        String flightNumber = flight != null ? flight.getFlight().getFlightNumber() : "N/A";
        String airlineNumber = flight != null ? flight.getAirline().getName() : "N/A";
        String airlineLogo = flight != null ? flight.getAirline().getLogoUrl() : null;
        String duration = flight != null ? flight.getFormattedDuration() : null;
        LocalDateTime depTime = flight != null ? flight.getDepartureDateTime() : null;
        LocalDateTime arrTime = flight != null ? flight.getArrivalDateTime() : null;

        AirportResponse dep = flight != null ? flight.getDepartureAirport() : null;
        CityResponse depCity = dep != null ? dep.getCity() : null;
        String depCode = dep != null ? dep.getIataCode(): "N/A";
        String depName = dep != null ? dep.getName() : "N/A";
        String depCityName = depCity != null ? depCity.getName() : "N/A";
        String depCountry = depCity != null ? depCity.getCountry() : "N/A";

        AirportResponse arr = flight != null ? flight.getArrivalAirport() : null;
        CityResponse arrCity = arr != null ? arr.getCity() : null;
        String arrCode = arr != null ? arr.getIataCode(): "N/A";
        String arrName = arr != null ? arr.getName() : "N/A";
        String arrCityName = arrCity != null ? arrCity.getName() : "N/A";
        String arrCountry = arrCity != null ? arrCity.getCountry() : "N/A";

        String fareName = fare != null ? fare.getName() : null;
        BigDecimal baseFare = fare != null ? fare.getBaseFare() : null;
        BigDecimal taxes = fare != null ? fare.getTaxesAndFees() : null;
        BaggagePolicyResponse baggagePolicyResponse = fare != null ? fare.getBaggagePolicy() : null;
        Integer ciPieces = baggagePolicyResponse != null ? baggagePolicyResponse.getCheckInBaggagePieces() : null;
        Double ciWeightPer = baggagePolicyResponse != null ? baggagePolicyResponse.getCabinBaggageWeightPerPiece() : null;
        Integer cbPieces = baggagePolicyResponse != null ? baggagePolicyResponse.getCabinBaggagePieces() : null;
        Double cbWeightPer = baggagePolicyResponse != null ? baggagePolicyResponse.getCabinBaggageWeightPerPiece() : null;

        BookingConfirmationEvent event = BookingConfirmationEvent.builder()
                .bookingId(booking.getId())
                .bookingReference(booking.getBookingReference())
                .confirmedAt(LocalDateTime.now())
                .bookingDate(LocalDateTime.ofInstant(booking.getBookingDate(), ZoneId.systemDefault()))
                .cabinClass(booking.getCabinClassType() != null ? booking.getCabinClassType().name() : "ECONOMY")

                .flexibleTicket(booking.getFlexibleTicket())
                // Contact
                .userId(booking.getUserId())
                .userName(user != null ? user.getFullName() : "Valued Customer")
                .contactEmail(contactEmail)
                .contactPhone(contactPhone)
                // Passengers
                .passengers(passengerNotifications)
                // Flight
                .flightInstanceId(booking.getFlightInstanceId())
                .flightNumber(flightNumber)
                .airlineName(arrName)
                .airlineLogo(airlineLogo)
                .departureAirportCode(depCode)
                .departureAirportName(depName)
                .departureCity(depCityName)
                .departureCountry(depCountry)

                .departureDateTime(depTime)
                .arrivalAirportCode(arrCode)
                .arrivalAirportName(arrName)
                .arrivalCity(arrCityName)
                .arrivalCountry(arrCountry)
                .arrivalDateTime(arrTime)
                .flightDuration(duration)
                // Payment
                .totalAmount(payment.getAmount())
                .currency("INR")
                .transactionId(payment.getTransactionId())
                .providerPaymentId(payment.getProviderPaymentId())
                .paymentGateway("RAZORPAY")
                .paidAt(payment.getPaidAt())
                // Fare breakdown
                .fareName(fareName)
                .baseFare(baseFare)
                .taxesAndFees(taxes)
                // Baggage
                .checkinBaggagePieces(ciPieces)
                .checkinBaggageWeightPerPieces(cbWeightPer)
                .cabinBaggagePieces(cbPieces)
                .cabinBaggageWeightPerPieces(ciWeightPer)
                // Policies
                .freeDateChange(fare != null ? fare.getFreeDateChange()       : null)
                .partialRefund(fare != null  ? fare.getPartialRefund()        : null)
                .fullRefund(fare != null     ? fare.getFullRefund()           : null)
                .priorityBoarding(fare != null ? fare.getPriorityBoarding()   : null)
                .loungeAccess(fare != null   ? fare.getLoungeAccess()         : null)
                .complimentaryMeals(fare != null ? fare.getComplimentaryMeals() : null)
                // Legacy — seat-service still reads this to mark seats BOOKED
                .seatInstanceIds(booking.getSeatInstanceIds())
                .build();

        kafkaTemplate.send("booking.confirmed",event);
    }

}
