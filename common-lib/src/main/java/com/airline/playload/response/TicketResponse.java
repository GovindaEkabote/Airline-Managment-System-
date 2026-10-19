package com.airline.playload.response;

import com.airline.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {

    private Long id;
    private String ticketNumber;
    private TicketStatus status ;
    private Instant issuedAt;

    private Long bookingId;
    private String bookingReference;

    private Long passengerId;
    private String passengerFirstName;
    private String passengerLastName;
    private String passengerEmail;

    private Long paymentId;
    private BigDecimal paymentAmount;
}
