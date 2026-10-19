package com.airline.playload.response;

import com.airline.embeddable.ContactInfo;
import com.airline.enums.BookingStatus;
import com.airline.enums.CabinClassType;
import com.airline.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long id;
    private String bookingReference;
    private Long userId;
    private String userName;
    private String userEmail;

    private Long flightId;
    private Long flightNumber;
    private String flightName;

    private String dapartureAirport;
    private String arrivalAirport;
    private String flightDuration;

    private CabinClassType cabinClassType;
    private BookingStatus bookingStatus;

    private Instant bookingDate;
    private Instant lastModified;

    // Passengers and seats
    private List<PassengerResponse> passengers;
    private List<SeatInstanceResponse> seatInstances;
    private Integer totalPassengers;

    // Payment details
    private PaymentLinkResponse payment;
    private PaymentStatus paymentStatus;
    private String paymentLink;

    // Ancillaries and meals
    private List<FlightCabinAncillaryResponse> flightCabinAncillaryResponses;
    private List<FlightMealResponse> flightMealResponses;

    // Ticket details
    private List<TicketResponse> tickets;

    // Fare and pricing
    private FareResponse fareResponse;
    private BigDecimal totalAmount;
    private String currency;

    // Derived flight information
    private Boolean upcoming;
    private Boolean past;

    // Booking contact details
    private ContactInfo contactInfo;


    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private Boolean isUpcoming;
    private Boolean isPast;

}
