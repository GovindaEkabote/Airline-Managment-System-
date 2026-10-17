
package com.airline.model;

import com.airline.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "tickets",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_ticket_number",
                        columnNames = "ticket_number"
                ),
                @UniqueConstraint(
                        name = "uk_ticket_booking_passenger",
                        columnNames = {"booking_id", "passenger_id"}
                )
        },
        indexes = {
                @Index(name = "idx_ticket_booking", columnList = "booking_id"),
                @Index(name = "idx_ticket_passenger", columnList = "passenger_id"),
                @Index(name = "idx_ticket_status", columnList = "status")
        }
)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_number", nullable = false,
            updatable = false, length = 30)
    private String ticketNumber;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TicketStatus status = TicketStatus.PENDING;

    @Column(name = "issued_at", updatable = false)
    private Instant issuedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = TicketStatus.PENDING;
        }

        if (status == TicketStatus.ISSUED && issuedAt == null) {
            issuedAt = Instant.now();
        }
    }
}
