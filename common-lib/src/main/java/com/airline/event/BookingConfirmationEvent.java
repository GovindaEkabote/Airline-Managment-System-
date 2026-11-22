package com.airline.event;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BookingConfirmationEvent {

    private Long bookingId;
    private String bookingReference;
    private LocalDateTime confirmedAt;
    private LocalDateTime bookingDate;
    private String cabinClass;
    private boolean flexibleTicket;

    private Long userId;
    private String userName;
    private String contactEmail;
    private String contactPhone;

    private List<PassengerNotification> passengers;

    private Long flightInstanceId;
    private String flightNumber;
    private String airlineName;
    private String airlineLogo;
    private String aircraftModel;

    private String departureAirportCode;
    private String departureAirportName;
    private String departureCity;
    private String departureCountry;
    private LocalDateTime departureDateTime;

    private String arrivalAirportCode;
    private String arrivalAirportName;
    private String arrivalCity;
    private String arrivalCountry;
    private LocalDateTime arrivalDateTime;
    private String flightDuration;

    private BigDecimal totalAmount;
    private String currency;
    private String transactionId;
    private String providerPaymentId;
    private String paymentGateway;
    private LocalDateTime paidAt;

    private String fareName;
    private BigDecimal baseFare;
    private BigDecimal taxesAndFees;
    private BigDecimal seatFees;
    private BigDecimal ancillaryFees;
    private BigDecimal mealFees;

    private Integer checkinBaggagePieces;
    private Double checkinBaggageWeightPerPieces;
    private Integer cabinBaggagePieces;
    private Double cabinBaggageWeightPerPieces;

    private Boolean freeDateChange;
    private Boolean partialRefund;
    private Boolean fullRefund;
    private Boolean priorityBoarding;
    private Boolean loungeAccess;
    private Boolean complimentaryMeals;

    private List<Long> seatInstanceIds;



}
