    package com.airline.model;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.Min;
    import jakarta.validation.constraints.NotNull;
    import lombok.*;
    import lombok.experimental.SuperBuilder;

    import java.util.ArrayList;
    import java.util.List;

    @Entity
    @Table(
            name = "flight_instance_cabin",
            uniqueConstraints = @UniqueConstraint(name = "uk_fic_flight_cabin",
                    columnNames = {"flight_instance_id", "cabin_class_id"}),
            indexes = @Index(name = "idx_fic_flight_instance", columnList = "flight_instance_id")
    )
    @Getter
    @Setter
    @SuperBuilder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class FlightInstanceCabin extends BaseEntity{

        @NotNull
        @Column(name = "flight_instance_id", nullable = false)
        private Long flightInstanceId;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "cabin_class_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_fic_cabin_class"))
        private CabinClass cabinClass;

        @Min(0)
        @Column(name = "total_seats", nullable = false)
        private int totalSeats;


        @Min(0)
        @Builder.Default
        @Column(name = "booked_seats", nullable = false)
        private int bookedSeats = 0;

        @Builder.Default
        @OneToMany(mappedBy = "flightInstanceCabin", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        private List<SeatInstance> seatInstances = new ArrayList<>();

        public int getAvailableSeats() {
            return totalSeats - bookedSeats;
        }
    }
