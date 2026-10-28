package com.airline.service.impl;

import com.airline.config.feignclient.*;
import com.airline.enums.BookingStatus;
import com.airline.enums.PaymentGateway;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.BookingMapper;
import com.airline.mapper.PassengerMapper;
import com.airline.model.Booking;
import com.airline.model.Passenger;
import com.airline.playload.dto.PaymentDTO;
import com.airline.playload.request.BookingRequest;
import com.airline.playload.request.PassengerRequest;
import com.airline.playload.request.PaymentInitiatRequest;
import com.airline.playload.response.*;
import com.airline.repository.BookingRepository;
import com.airline.service.BookingService;
import com.airline.service.PassengerService;
import com.airline.service.TicketService;
import com.airline.service.integration.CalculatesFareTotal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final FlightClient flightClient;
    private final AncillaryClient ancillaryClient;
    private final SeatClient seatClient;
    private final CalculatesFareTotal calculatesFareTotal;
    private final PaymentClient paymentClient;
    private final AirlineClient airlineClient;


    @Override
    @Transactional
    public PaymentInitiatResponse createBooking(BookingRequest request, Long userId) {


        // Step 1: Generate unique booking reference
        String bookingReference = generateBookingReference();

        // Step 2: Map passengers without saving them individually
        Set<Passenger> passengers = new HashSet<>();

        for (PassengerRequest passengerRequest : request.getPassengers()) {
            Passenger passenger = PassengerMapper.toEntity(passengerRequest);
            passenger.setPrimaryUserId(userId);
            passengers.add(passenger);
        }

        // Step 3: Verify flight exists and retrieve airline
        FlightResponse flightResponse =
                flightClient.getFlightById(request.getFlightId());

        // Step 4: Create booking
        Booking booking = BookingMapper.toEntity(
                request,
                userId,
                passengers,
                bookingReference
        );

        booking.setAirlineId(flightResponse.getAirline().getId());

        // Step 5: Set seat instance IDs
        List<Long> seatInstanceIds = request.getPassengers()
                .stream()
                .map(PassengerRequest::getSeatInstanceId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (seatInstanceIds.size() != request.getPassengers().size()) {
            throw new IllegalArgumentException(
                    "Every passenger must have a seat instance ID."
            );
        }

        booking.setSeatInstanceIds(seatInstanceIds);

        // Step 6: Set both sides of the passenger-booking relationship
        booking.setPassengers(passengers);

        for (Passenger passenger : passengers) {
            passenger.setBooking(booking);
        }

        // Step 7: Save booking and passengers through cascade
        Booking savedBooking = bookingRepository.save(booking);

        // Step 8: Calculate fare
        BigDecimal fareTotal = Objects.requireNonNull(
                calculatesFareTotal.calculateFareTotal(request.getFareId()),
                "Fare total is null for fareId: " + request.getFareId()
        );

        // Step 9: Calculate seat price
        BigDecimal seatPrice = Objects.requireNonNull(
                seatClient.calculatePrice(savedBooking.getSeatInstanceIds()),
                "Seat price is null"
        );

        // Step 10: Calculate ancillary price
        Double ancillaryValue =
                ancillaryClient.calculateAncillaryPrice(
                        savedBooking.getAncillaryIds()
                );

        BigDecimal ancillaryPrice = BigDecimal.valueOf(
                Objects.requireNonNull(ancillaryValue, "Ancillary price is null")
        );

        // Step 11: Calculate meal price
        Double mealValue =
                ancillaryClient.calculateMealPrice(
                        savedBooking.getMealsIds()
                );

        BigDecimal mealPrice = BigDecimal.valueOf(
                Objects.requireNonNull(mealValue, "Meal price is null")
        );

        // Step 12: Calculate total
        BigDecimal totalPrice = fareTotal
                .add(seatPrice)
                .add(ancillaryPrice)
                .add(mealPrice);

        // Step 13: Initiate payment
        PaymentInitiatRequest paymentRequest = PaymentInitiatRequest.builder()
                .userId(userId)
                .bookingId(savedBooking.getId())
                .amount(totalPrice)
                .paymentGateway(PaymentGateway.RAZORPAY)
                .description("Payment for booking: " + bookingReference)
                .build();

        return paymentClient.initiatePayment(paymentRequest);
    }


    @Override
    public BookingResponse updateBooking(Long id, BookingRequest request) {
        return null;
    }

    @Override
    public BookingResponse getBookingById(Long id) {

        Booking booking= bookingRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Booking Id Not Found")
                );

        return convertResponse(booking);
    }

    @Override
    public List<BookingResponse> getAllBookingsByAirline(Long userId, String searchQuery, BookingStatus status, Long flightInstanceId) {
        AirlineResponse airlineResponse = airlineClient.getAirlineByOwnerId(userId);
        List<Booking>bookings = bookingRepository
                .findByAirlineWithFilter(
                        airlineResponse.getId(),
                        searchQuery,
                        status,
                        flightInstanceId
                );

        return bookings.stream().map(
                this::convertResponse
        ).toList();

    }


    @Override
    public List<BookingResponse> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream().map(
                        this::convertResponse
                ).toList();
    }

    @Override
    public BookingResponse cancleBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found with id: " + id
                        )
                );

        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Booking is already cancelled"
            );
        }

        if (booking.getBookingStatus() == BookingStatus.EXPIRED || booking.getBookingStatus() == BookingStatus.REFUNDED) {
            throw new IllegalStateException(
                    "booking cannot be cancelled"
            );
        }
        booking.setBookingStatus(BookingStatus.CANCELLED);

        Booking updatedBooking = bookingRepository.save(booking);

        return convertResponse(updatedBooking);
    }

    @Override
    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new
                        ResourceNotFoundException("Id Not Found"));
        bookingRepository.delete(booking);
    }

    private String generateBookingReference(){
        String reference;

        do {
            reference="BK"+ UUID.randomUUID().toString().substring(0,8).toUpperCase();
        }while (bookingRepository.existsByBookingReference(reference));
        return reference;
    }

    private BookingResponse convertResponse(Booking booking){
            // TODO : when enable feign Client use actual responses
        List<FlightCabinAncillaryResponse> flightCabinAncillaryResponses
                = new ArrayList<>();
        List<FlightMealResponse> flightMealResponses
                = new ArrayList<>();
        PaymentDTO paymentDTO=new PaymentDTO();
        FareResponse fareResponse = new FareResponse();
        FlightResponse flightResponse = new FlightResponse();

        List<SeatInstanceResponse>  seatResponses = new ArrayList<>();
        FlightInstanceResponse flightInstanceResponse = new FlightInstanceResponse();
        return BookingMapper.toResponse(
                booking,
                paymentDTO,
                fareResponse,
                flightResponse,
                flightInstanceResponse,
                flightCabinAncillaryResponses,
                flightMealResponses,
                seatResponses
        );
    }
}
