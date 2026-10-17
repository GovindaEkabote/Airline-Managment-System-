
package com.airline.model;

import com.airline.embeddable.ContactInfo;
import com.airline.enums.BookingStatus;
import com.airline.enums.CabinClassType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "bookings",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_booking_reference",
                        columnNames = "booking_reference"
                )
        },
        indexes = {
                @Index(name = "idx_booking_user", columnList = "user_id"),
                @Index(name = "idx_booking_flight_instance",
                        columnList = "flight_instance_id"),
                @Index(name = "idx_booking_status", columnList = "booking_status"),
                @Index(name = "idx_booking_payment", columnList = "payment_id")
        }
)
@EntityListeners(AuditingEntityListener.class)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_reference", nullable = false,
            updatable = false, length = 20)
    private String bookingReference;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "flight_id", nullable = false)
    private Long flightId;

    @Column(name = "flight_instance_id", nullable = false)
    private Long flightInstanceId;

    @Column(name = "airline_id", nullable = false)
    private Long airlineId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "cabin_class_type", nullable = false, length = 30)
    private CabinClassType cabinClassType = CabinClassType.ECONOMY;

    @Column(name = "fare_id", nullable = false)
    private Long fareId;

    @Builder.Default
    @Column(name = "flexible_ticket", nullable = false)
    private Boolean flexibleTicket = false;

    @Column(name = "ticket_time_limit")
    private LocalDateTime ticketTimeLimit;

    @Builder.Default
    @OneToMany(
            mappedBy = "booking",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Passenger> passengers = new HashSet<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(
            name = "booking_seat_instances",
            joinColumns = @JoinColumn(name = "booking_id"),
            uniqueConstraints = @UniqueConstraint(
                    name = "uk_booking_seat_instance",
                    columnNames = {"booking_id", "seat_instance_id"}
            )
    )
    @Column(name = "seat_instance_id", nullable = false)
    private Set<Long> seatInstanceIds = new HashSet<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(
            name = "booking_ancillaries",
            joinColumns = @JoinColumn(name = "booking_id")
    )
    @Column(name = "ancillary_id", nullable = false)
    private Set<Long> ancillaryIds = new HashSet<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(
            name = "booking_meals",
            joinColumns = @JoinColumn(name = "booking_id")
    )
    @Column(name = "meal_id", nullable = false)
    private Set<Long> mealsIds = new HashSet<>();

    @Builder.Default
    @OneToMany(
            mappedBy = "booking",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Ticket> tickets = new HashSet<>();

    @Column(name = "payment_id")
    private Long paymentId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", nullable = false, length = 30)
    private BookingStatus bookingStatus = BookingStatus.PENDING;

    @CreatedDate
    @Column(name = "booking_date", nullable = false, updatable = false)
    private Instant bookingDate;

    @UpdateTimestamp
    @Column(name = "last_modified")
    private Instant lastModified;

    @Builder.Default
    @Column(name = "ticket_issued", nullable = false)
    private Boolean ticketIssued = false;

    @Embedded
    private ContactInfo contactInfo;

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
        passenger.setBooking(this);
    }

    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger);
        passenger.setBooking(null);
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setBooking(this);
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
        ticket.setBooking(null);
    }
}
