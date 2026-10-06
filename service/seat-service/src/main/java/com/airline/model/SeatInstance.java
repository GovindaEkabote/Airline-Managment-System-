package com.airline.model;

import com.airline.enums.SeatAvailabilityStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(
        name = "seat_instance",
        uniqueConstraints = @UniqueConstraint(name = "uk_seat_instance_flight_seat",
                columnNames = {"flight_instance_id", "seat_id"}),
        indexes = {
                @Index(name = "idx_seat_instance_flight_status", columnList = "flight_instance_id, status"),
                @Index(name = "idx_seat_instance_hold_expiry", columnList = "status, hold_expires_at")
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeatInstance extends BaseEntity{

    @NotNull
    @Column(name = "flight_instance_id", nullable = false)
    private Long flightInstanceId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flight_instance_cabin_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_seat_instance_fic"))
    private FlightInstanceCabin flightInstanceCabin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seat_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_seat_instance_seat"))
    private Seat seat;

    @NotNull
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SeatAvailabilityStatus status = SeatAvailabilityStatus.AVAILABLE;

    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal fare;

    @PositiveOrZero
    @Column(name = "premium_surcharge", precision = 10, scale = 2)
    private BigDecimal premiumSurcharge;

    // Set while HELD; a scheduled job releases expired holds.
    @Column(name = "hold_expires_at")
    private Instant holdExpiresAt;

    // Booking that owns this seat once HELD/BOOKED.
    @Column(name = "booking_id")
    private Long bookingId;

    public BigDecimal getTotalFare() {
        BigDecimal f = fare != null ? fare : BigDecimal.ZERO;
        BigDecimal s = premiumSurcharge != null ? premiumSurcharge : BigDecimal.ZERO;
        return f.add(s);
    }

    public boolean isAvailable() {
        return status == SeatAvailabilityStatus.AVAILABLE;
    }

    public boolean isBooked() {
        return status == SeatAvailabilityStatus.BOOKED;
    }

    /** Temporarily reserve while the passenger pays. */
    public void hold(Long bookingId, Instant expiresAt) {
        if (!isAvailable()) {
            throw new IllegalStateException("Seat not available: " + status);
        }
        this.status = SeatAvailabilityStatus.HELD;
        this.bookingId = bookingId;
        this.holdExpiresAt = expiresAt;
    }

    /** Payment done -> permanent booking. */
    public void confirm() {
        if (status != SeatAvailabilityStatus.HELD) {
            throw new IllegalStateException("Only a HELD seat can be confirmed: " + status);
        }
        this.status = SeatAvailabilityStatus.BOOKED;
        this.holdExpiresAt = null;
    }

    /** Hold expired / booking cancelled -> back to the pool. */
    public void release() {
        if (status == SeatAvailabilityStatus.BLOCKED) {
            throw new IllegalStateException("Blocked seat cannot be released");
        }
        this.status = SeatAvailabilityStatus.AVAILABLE;
        this.bookingId = null;
        this.holdExpiresAt = null;
    }

}
