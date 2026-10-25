package com.airline.service;

import com.airline.enums.BookingStatus;
import com.airline.playload.request.BookingRequest;
import com.airline.playload.response.BookingResponse;
import com.airline.playload.response.PaymentInitiatResponse;

import java.util.List;

public interface BookingService {

    PaymentInitiatResponse createBooking(BookingRequest request, Long userId);
    BookingResponse updateBooking(Long id, BookingRequest request);
    BookingResponse getBookingById(Long id);
    List<BookingResponse> getAllBookingsByAirline(
            Long airlineId,
            String searchQuery,
            BookingStatus status,
            Long flightInstanceId
    );
    List<BookingResponse> getBookingsByUser(Long userId);
    BookingResponse cancleBooking(Long id);
    void deleteBooking(Long id);
}
