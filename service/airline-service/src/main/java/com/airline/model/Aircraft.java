package com.airline.model;

import com.airline.enums.AircraftStatus;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(
        name = "aircraft",
        indexes = {
                @Index(
                        name = "idx_aircraft_registration",
                        columnList = "registration_number"
                ),
                @Index(
                        name = "idx_aircraft_serial_number",
                        columnList = "serial_number"
                ),
                @Index(
                        name = "idx_aircraft_type",
                        columnList = "aircraft_type_id"
                ),
                @Index(
                        name = "idx_aircraft_airline",
                        columnList = "airline_id"
                ),
                @Index(
                        name = "idx_aircraft_status",
                        columnList = "status"
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /**
     * Aircraft registration / tail number.
     * Example: VT-ANB
     */
    @Column(
            name = "registration_number",
            nullable = false,
            unique = true,
            length = 20
    )
    private String registrationNumber;

    /**
     * Manufacturer Serial Number.
     * Remains associated with the physical aircraft.
     */
    @Column(
            name = "serial_number",
            nullable = false,
            unique = true,
            length = 50
    )
    private String serialNumber;

    /**
     * ICAO24 transponder address.
     * Example: 800123
     */
    @Column(
            name = "icao24",
            unique = true,
            length = 6
    )
    private String icao24;

    /**
     * Reference to AircraftType.
     */
    @Column(name = "aircraft_type_id", nullable = false)
    private Long aircraftTypeId;

    /**
     * Reference to Airline Service's Airline ID.
     */
    @Column(name = "airline_id", nullable = false)
    private Long airlineId;

    /**
     * Year in which aircraft was manufactured.
     */
    @Column(name = "manufacture_year")
    private Integer manufactureYear;

    /**
     * Date when aircraft was first delivered.
     */
    @Column(name = "delivery_date")
    private java.time.LocalDate deliveryDate;

    /**
     * Aircraft operational status.
     */
    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    @Builder.Default
    private AircraftStatus status = AircraftStatus.ACTIVE;

    /**
     * Indicates whether aircraft can currently
     * be assigned to flights.
     */
    @Column(name = "available", nullable = false)
    @Builder.Default
    private Boolean available = true;

    /**
     * Last user who modified the aircraft.
     */
    @Column(name = "updated_by_id")
    private Long updatedById;

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
