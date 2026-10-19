package com.airline.service.impl;

import com.airline.enums.BookingStatus;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.BookingMapper;
import com.airline.mapper.PassengerMapper;
import com.airline.model.Booking;
import com.airline.model.Passenger;
import com.airline.playload.dto.PaymentDTO;
import com.airline.playload.request.BookingRequest;
import com.airline.playload.request.PassengerRequest;
import com.airline.playload.response.*;
import com.airline.repository.BookingRepository;
import com.airline.service.BookingService;
import com.airline.service.PassengerService;
import com.airline.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PassengerService passengerService;
    private final TicketService ticketService;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request, Long userId) {

        // Step 1: Generate unique booking reference
        String bookingReference = generateBookingReference();

        // Step 2: Map passengers without saving them
        Set<Passenger> passengers = new HashSet<>();

        for (PassengerRequest passengerRequest : request.getPassengers()) {
            Passenger passenger = PassengerMapper.toEntity(passengerRequest);

            passenger.setPrimaryUserId(userId);
            passengers.add(passenger);
        }

        // Step 3: Create booking
        Booking booking = BookingMapper.toEntity(
                request,
                userId,
                passengers,
                bookingReference
        );

        // TODO: Retrieve airline ID from flight service
        booking.setAirlineId(1L);

        // Step 4: Set seat instance IDs
        List<Long> seatInstanceIds = request.getPassengers()
                .stream()
                .map(PassengerRequest::getSeatInstanceId)
                .collect(Collectors.toList());

        booking.setSeatInstanceIds(seatInstanceIds);

        // Step 5: Set both sides of the relationship
        for (Passenger passenger : passengers) {
            passenger.setBooking(booking);
        }

        booking.setPassengers(passengers);

        // Step 6: Save booking and passengers through cascade
        Booking savedBooking = bookingRepository.save(booking);

        // Step 7: Generate tickets
        ticketService.generateTicketsForBooking(savedBooking);

        return convertResponse(savedBooking);
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
    public List<BookingResponse> getAllBookingsByAirline(Long airlineId, String searchQuery, BookingStatus status, Long flightInstanceId) {
        List<Booking>bookings = bookingRepository
                .findByAirlineWithFilter(
                        airlineId,
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
