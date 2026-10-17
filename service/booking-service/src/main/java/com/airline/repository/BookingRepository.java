package com.airline.repository;

import com.airline.enums.BookingStatus;
import com.airline.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    long coundByFlightInstanceId(Long flightInstanceId);


    @Query("""
    SELECT DISTINCT b
    FROM Booking b
    LEFT JOIN FETCH b.passengers p
    WHERE b.airlineId = :airlineId
      AND (:status IS NULL OR b.bookingStatus = :status)
      AND (:flightInstanceId IS NULL
           OR b.flightInstanceId = :flightInstanceId)
      AND (
          :search IS NULL
          OR TRIM(:search) = ''
          OR LOWER(b.bookingReference)
              LIKE LOWER(CONCAT('%', :search, '%'))
          OR STR(b.id)
              LIKE CONCAT('%', :search, '%')
          OR LOWER(p.firstName)
              LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(p.lastName)
              LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(p.email)
              LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(b.contactInfo.email)
              LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(b.contactInfo.phone)
              LIKE LOWER(CONCAT('%', :search, '%'))
      )
    ORDER BY b.bookingDate DESC
    """)
    List<Booking> findByAirlineWithFilter(
            @Param("airlineId") Long airlineId,
            @Param("search") String search,
            @Param("status") BookingStatus status,
            @Param("flightInstanceId") Long flightInstanceId
    );


    boolean existByBookingReference(String bookingReference);

}
