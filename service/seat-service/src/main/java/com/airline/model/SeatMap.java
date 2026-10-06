package com.airline.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "seat_map",
        indexes = @Index(name = "idx_seat_map_airline", columnList = "airline_id")
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeatMap extends BaseEntity{

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;

    @Min(1)
    @Column(name = "total_rows", nullable = false)
    private int totalRows;

    @Min(0)
    @Column(name = "right_seats_per_row", nullable = false)
    private int rightSeatsPerRow;

    @Min(0)
    @Column(name = "left_seats_per_row", nullable = false)
    private int leftSeatsPerRow;

    @NotNull
    @Column(name = "airline_id", nullable = false)
    private Long airlineId;

    @Builder.Default
    @OneToMany(mappedBy = "seatMap", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cabin_class_id", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "fk_seat_map_cabin_class"))
    private CabinClass cabinClass;

}
