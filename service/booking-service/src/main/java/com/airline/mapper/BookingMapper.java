package com.airline.mapper;

import com.airline.enums.BookingStatus;
import com.airline.model.Booking;
import com.airline.model.Passenger;
import com.airline.playload.dto.PaymentDTO;
import com.airline.playload.request.BookingRequest;
import com.airline.playload.response.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingMapper {

    public static Booking toEntity(
            BookingRequest request,
            Long userId,
            Set<Passenger> passengers,
            String bookingReference
    ){
        return Booking.builder()
                .bookingReference(bookingReference)
                .userId(userId)
                .flightInstanceId(request.getFlightInstanceId())
                .flightId(request.getFlightId())
                .fareId(request.getFareId())
                .contactInfo(request.getContactInfo())
                .passengers(passengers)
                .cabinClassType(request.getCabinClassType())
                .ancillaryIds(request.getAncillaryIds())
                .mealsIds(request.getMealIds())
                .bookingStatus(BookingStatus.PENDING)
                .build();
    }

    public static BookingResponse toResponse(
            Booking booking,
            PaymentDTO paymentDTO,
            FareResponse fareResponse,
            FlightResponse flightResponse,
            FlightInstanceResponse flightInstanceResponse,
            List<FlightCabinAncillaryResponse> ancillaries,
            List<FlightMealResponse> meals,
            List<SeatInstanceResponse> seats
    ) {

        List<PassengerResponse> passengerResponses =
                booking.getPassengers() != null
                        ? booking.getPassengers().stream()
                        .map(PassengerMapper::toResponse)
                        .collect(Collectors.toList())
                        : null;

        List<TicketResponse> ticketResponses =
                booking.getTickets() != null
                        ? booking.getTickets().stream()
                        .map(TicketMapper::toResponse)
                        .collect(Collectors.toList())
                        : null;

        return BookingResponse.builder()
                .id(booking.getId())
                .bookingReference(booking.getBookingReference())
                .userId(booking.getUserId())

                // Flight details
                .flightId(booking.getFlightInstanceId())
                .flightNumber(
                        flightResponse != null
                                && flightResponse.getFlightNumber() != null
                                ? Long.valueOf(flightResponse.getFlightNumber())
                                : null
                )
                .flightName(
                        flightResponse != null
                                && flightResponse.getArrivalAirport() != null
                                && flightResponse.getDepartureAirport() != null
                                && flightResponse.getArrivalAirport().getCity() != null
                                && flightResponse.getDepartureAirport().getCity() != null
                                ? flightResponse.getDepartureAirport().getCity().getName()
                                + " - "
                                + flightResponse.getArrivalAirport().getCity().getName()
                                : null
                )
                .departureTime(
                        flightInstanceResponse != null
                                ? flightInstanceResponse.getDepartureDateTime()
                                : null
                )
                .arrivalTime(
                        flightInstanceResponse != null
                                ? flightInstanceResponse.getArrivalDateTime()
                                : null
                )
                .flightDuration(
                        flightInstanceResponse != null
                                ? flightInstanceResponse.getFormattedDuration()
                                : null
                )

                // Airport details
                .dapartureAirport(
                        flightResponse != null
                                && flightResponse.getDepartureAirport() != null
                                ? flightResponse.getDepartureAirport().getDetailedName()
                                : null
                )
                .arrivalAirport(
                        flightResponse != null
                                && flightResponse.getArrivalAirport() != null
                                ? flightResponse.getArrivalAirport().getName()
                                : null
                )

                // Booking details
                .bookingStatus(booking.getBookingStatus())
                .bookingDate(booking.getBookingDate())
                .lastModified(booking.getLastModified())
                .passengers(passengerResponses)
                .tickets(ticketResponses)
                .totalPassengers(
                        booking.getPassengers() != null
                                ? booking.getPassengers().size()
                                : 0
                )

                // Ancillaries, meals and seats
                .flightCabinAncillaryResponses(ancillaries)
                .flightMealResponses(meals)
                .seatInstances(seats)

                // Payment and fare details
                .paymentStatus(
                        paymentDTO != null
                                ? paymentDTO.getStatus()
                                : null
                )
                .fareResponse(fareResponse)
                .totalAmount(
                        fareResponse != null
                                ? fareResponse.getTotalPrice()
                                : null
                )
                .currency(
                        fareResponse != null
                                ? fareResponse.getCurrency()
                                : null
                )

                // Contact information
                .contactInfo(booking.getContactInfo())
                .build();
    }

}