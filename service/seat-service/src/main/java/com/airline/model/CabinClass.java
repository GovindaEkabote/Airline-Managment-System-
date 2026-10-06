package com.airline.model;


import com.airline.enums.CabinClassType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * A cabin (Economy/Business/...) of one aircraft. Template data, not per-flight.
 *
 * CHANGES:
 *  - extends BaseEntity: audit fields now actually populated (before: nullable=false but never set -> insert failed)
 *  - @Enumerated(STRING): enum stored by name, safe if enum order changes
 *  - unique (aircraft_id, code): an aircraft cannot have two "J" cabins
 *  - index on aircraft_id: fast "cabins of aircraft X" lookups
 *  - @Builder.Default + primitives: builder no longer nulls out defaults
 *  - validation annotations + explicit column names/lengths
 *  - attachSeatMap(): keeps both sides of the 1-1 relation in sync
 */
import java.time.Instant;

@Entity
@Table(
        name = "cabin_class",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_cabin_class_aircraft_code",
                columnNames = {"aircraft_id", "code"}
        ),
        indexes = @Index(
                name = "idx_cabin_class_aircraft",
                columnList = "aircraft_id"
        )
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CabinClass extends BaseEntity{



    @OneToOne(mappedBy = "cabinClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private SeatMap seatMap;

    @NotNull
    @Column(name = "aircraft_id", nullable = false)
    private Long aircraftId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cabin_class_type", nullable = false, length = 30)
    private CabinClassType cabinClassType;

    @NotBlank
    @Size(max = 10)
    @Column(nullable = false,length = 10)
    private String code;

    @Size(max = 255)
    private String description;

    @Builder.Default
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder=0;

    @Builder.Default
    @Column(name = "active", nullable = false)
    private Boolean isActive=true;

    @Builder.Default
    @Column(name = "bookable", nullable = false)
    private Boolean isBookable=true;

    @Positive
    @Column(name = "typical_seat_pitch_in")
    private Integer typicalSeatPitch;
    @Positive
    @Column(name = "typical_seat_width_in")
    private Integer typicalSeatWidth;

    @Size(max = 50)
    @Column(name = "seat_type", length = 50)
    private String seatType;

    public void attachSeatMap(SeatMap map) {
        this.seatMap = map;
        if (map != null) map.setCabinClass(this);
    }
}
