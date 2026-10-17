package com.airline.service;

import com.airline.playload.request.BookingRequest;
import com.airline.playload.response.BookingResponse;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request, Long userId);
    BookingResponse updateBooking(Long id, BookingRequest request);
    BookingResponse getBookingById(Long id);
    List<BookingResponse> getAllBookingsByAirline(
            Long airlineId,
            String searchQuery,
            Long flightInstanceId,
            String sortDirection
    );
    List<BookingResponse> getBookingsByUser(Long userId);
    BookingResponse cancleBooking(Long id);
    void deleteBooking(Long id);
}
