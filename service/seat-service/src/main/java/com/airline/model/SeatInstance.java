package com.airline.model;

import com.airline.enums.SeatAvailabilityStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.SuperBuilder;
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
@EntityListeners(AuditingEntityListener.class)
public class SeatInstance extends BaseEntity{

    @NotNull
    @Column(name = "flight_instance_id", nullable = false)
    private Long flightInstanceId;

    @Column(name = "flight_id", nullable = false)
    private Long flightId;

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

    private boolean isBooked=false;

    private boolean isAvailable=true;

    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal fare;

    @PositiveOrZero
    @Column(name = "premium_surcharge", precision = 10, scale = 2)
    private BigDecimal premiumSurcharge;

    @Version
    private Long version;

    @LastModifiedDate
    private Instant updatedAt;
}
