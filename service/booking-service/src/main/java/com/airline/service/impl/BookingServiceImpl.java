package com.airline.service.impl;

import com.airline.playload.request.BookingRequest;
import com.airline.playload.response.BookingResponse;
import com.airline.repository.BookingRepository;
import com.airline.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public BookingResponse createBooking(BookingRequest request, Long userId) {
        return null;
    }

    @Override
    public BookingResponse updateBooking(Long id, BookingRequest request) {
        return null;
    }

    @Override
    public BookingResponse getBookingById(Long id) {
        return null;
    }

    @Override
    public List<BookingResponse> getAllBookingsByAirline(Long airlineId, String searchQuery, Long flightInstanceId, String sortDirection) {
        return List.of();
    }

    @Override
    public List<BookingResponse> getBookingsByUser(Long userId) {
        return List.of();
    }

    @Override
    public BookingResponse cancleBooking(Long id) {
        return null;
    }

    @Override
    public void deleteBooking(Long id) {

    }

    private String generateBookingRefeence(){
        String reference;

        do {
            reference="BK"+ UUID.randomUUID().toString().substring(0,8).toUpperCase();
        }while (bookingRepository.existByBookingReference(reference));
        return reference;
    }
}
