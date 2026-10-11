package com.airline.model;

import com.airline.enums.SeatType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "seat",
        uniqueConstraints = @UniqueConstraint(name = "uk_seat_map_seat_number",
                columnNames = {"seat_map_id", "seat_number"}),
        indexes = @Index(name = "idx_seat_seat_map", columnList = "seat_map_id")
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Seat extends BaseEntity{

    @NotBlank
    @Size(max = 5)
    @Column(name = "seat_number", nullable = false, length = 5)   // e.g. "12A"
    private String seatNumber;

    @Min(1)
    @Column(name = "seat_row", nullable = false)
    private int seatRow;

    @NotNull
    @Column(name = "column_letter", nullable = false)
    private Character columnLetter;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type", nullable = false, length = 30)
    private SeatType seatType;

    @PositiveOrZero
    @Column(name = "base_price", precision = 10, scale = 2)
    private BigDecimal basePrice;

    @PositiveOrZero
    @Column(name = "premium_surcharge", precision = 10, scale = 2)
    private BigDecimal premiumSurcharge;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isAvailable = true;        // seat physically exists / usable

    @Builder.Default
    @Column(nullable = false)
    private Boolean isBlocked = false;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    private String fullPosition;

    @Builder.Default
    @Column(name = "emergency_exit")
    private Boolean isEmergencyExit = false;

    @Builder.Default
    @Column(name = "extra_legroom")
    private Boolean isExtraLegroom = false;

    @Builder.Default
    @Column(name = "power_outlet")
    private Boolean isPowerOutlet = false;

    @Builder.Default
    @Column(name = "tv_screen")
    private Boolean isTvScreen = false;

    @Builder.Default
    @Column(name = "extra_width")
    private Boolean isExtraWidth = false;

    @Positive
    @Column(name = "seat_pitch_in")
    private Integer seatPitch;

    @Positive
    @Column(name = "seat_width_in")
    private Integer seatWidth;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seat_map_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_seat_seat_map"))
    private SeatMap seatMap;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cabin_class_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_seat_cabin_class"))
    private CabinClass cabinClass;

    @CreatedBy
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Version
    private Long version;

    // Null-safe (old version could NPE / used Double).
    public BigDecimal getTotalPrice() {
        BigDecimal base = basePrice != null ? basePrice : BigDecimal.ZERO;
        BigDecimal extra = premiumSurcharge != null ? premiumSurcharge : BigDecimal.ZERO;
        return base.add(extra);
    }


    public String getFullSeatNumber() {
        return seatRow + String.valueOf(columnLetter);
    }

    public boolean isBookable() {
        return Boolean.TRUE.equals(isAvailable)
                && !Boolean.TRUE.equals(isBlocked)
                && Boolean.TRUE.equals(isActive);
    }

    public boolean isWindowSeat() {
        return seatType != null && seatType.name().contains("WINDOW");
    }

    public boolean isAisleSeat() {
        return seatType != null && seatType.name().contains("AISLE");
    }

    public boolean isMiddleSeat() {
        return seatType != null && seatType.name().contains("MIDDLE");
    }

    public boolean isPremiumSeat() {
        return Boolean.TRUE.equals(isExtraLegroom)
                || Boolean.TRUE.equals(isEmergencyExit)
                || Boolean.TRUE.equals(isExtraWidth);
    }

    public boolean isEmergencyExitSeat() {
        return Boolean.TRUE.equals(isEmergencyExit);
    }
}
