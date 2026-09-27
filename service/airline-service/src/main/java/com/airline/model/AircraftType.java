package com.airline.model;

import com.airline.enums.AircraftTypeStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(
        name = "aircraft_type",
        indexes = {
                @Index(
                        name = "idx_aircraft_type_icao",
                        columnList = "icao_type_code"
                ),
                @Index(
                        name = "idx_aircraft_type_iata",
                        columnList = "iata_type_code"
                ),
                @Index(
                        name = "idx_aircraft_type_manufacturer",
                        columnList = "manufacturer"
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AircraftType    {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(
            name = "manufacturer",
            nullable = false,
            length = 100
    )
    private String manufacturer;

    @Column(
            name = "model",
            nullable = false,
            length = 100
    )
    private String model;

    /**
     * ICAO aircraft type designator.
     * Example: A320
     */
    @Column(
            name = "icao_type_code",
            nullable = false,
            unique = true,
            length = 4
    )
    private String icaoTypeCode;

    /**
     * IATA aircraft type code.
     * Example: 320
     */
    @Column(
            name = "iata_type_code",
            unique = true,
            length = 10
    )
    private String iataTypeCode;

    /**
     * Number of engines.
     */
    @Column(name = "engine_count")
    private Integer engineCount;

    /**
     * Engine type.
     */
    @Column(
            name = "engine_type",
            length = 30
    )
    private String engineType;

    /**
     * Maximum passenger capacity.
     */
    @Column(name = "passenger_capacity")
    private Integer passengerCapacity;

    /**
     * Maximum cargo capacity in kilograms.
     */
    @Column(name = "cargo_capacity_kg")
    private Integer cargoCapacityKg;

    /**
     * Maximum range in kilometers.
     */
    @Column(name = "max_range_km")
    private Integer maxRangeKm;

    /**
     * Cruise speed in km/h.
     */
    @Column(name = "cruise_speed_kmh")
    private Integer cruiseSpeedKmh;

    /**
     * Maximum takeoff weight in kilograms.
     */
    @Column(name = "max_takeoff_weight_kg")
    private Integer maxTakeoffWeightKg;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 20
    )
    @Builder.Default
    private AircraftTypeStatus status = AircraftTypeStatus.ACTIVE;

    @CreationTimestamp
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private Instant createdAt;

    @UpdateTimestamp
    @Column(
            name = "updated_at",
            nullable = false
    )
    private Instant updatedAt;
}


//