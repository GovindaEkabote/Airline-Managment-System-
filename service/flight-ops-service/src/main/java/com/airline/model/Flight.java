package com.airline.model;

import com.airline.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Duration;
import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "flight",
        indexes = {
                @Index(name = "idx_flight_airline", columnList = "airline_id"),
                @Index(name = "idx_flight_aircraft", columnList = "aircraft_id"),
                @Index(name = "idx_flight_departure_airport", columnList = "departure_airport_id"),
                @Index(name = "idx_flight_arrival_airport", columnList = "arrival_airport_id"),
                @Index(name = "idx_flight_departure_time", columnList = "scheduled_departure"),
                @Index(name = "idx_flight_arrival_time", columnList = "scheduled_arrival"),
                @Index(name = "idx_flight_status", columnList = "status"),
                @Index(
                        name = "idx_flight_search",
                        columnList = "departure_airport_id, arrival_airport_id, scheduled_departure"
                )
        }
)
@EntityListeners(AuditingEntityListener.class)
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Commercial flight number.
     * Example: AI101, EK501, 6E203
     */
    @Column(
            name = "flight_number",
            nullable = false,
            length = 10
    )
    private String flightNumber;

    /**
     * Airline operating this flight.
     */
    @Column(name = "airline_id", nullable = false)
    private Long airlineId;

    /**
     * Aircraft assigned to this flight.
     */
    @Column(name = "aircraft_id", nullable = false)
    private Long aircraftId;

    /**
     * Origin airport.
     */
    @Column(name = "departure_airport_id", nullable = false)
    private Long departureAirportId;

    /**
     * Destination airport.
     */
    @Column(name = "arrival_airport_id", nullable = false)
    private Long arrivalAirportId;

    /**
     * Scheduled departure time.
     * Stored as UTC.
     */
    @Column(
            name = "scheduled_departure",
            nullable = false
    )
    private Instant scheduledDeparture;

    /**
     * Scheduled arrival time.
     * Stored as UTC.
     */
    @Column(
            name = "scheduled_arrival",
            nullable = false
    )
    private Instant scheduledArrival;

    /**
     * Actual departure time.
     */
    @Column(name = "actual_departure")
    private Instant actualDeparture;

    /**
     * Actual arrival time.
     */
    @Column(name = "actual_arrival")
    private Instant actualArrival;

    /**
     * Current flight status.
     */
    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    @Builder.Default
    private FlightStatus status;

    /**
     * Departure terminal.
     * Example: T2
     */
    @Column(name = "departure_terminal", length = 20)
    private String departureTerminal;

    /**
     * Departure gate.
     * Example: A12
     */
    @Column(name = "departure_gate", length = 20)
    private String departureGate;

    /**
     * Arrival terminal.
     */
    @Column(name = "arrival_terminal", length = 20)
    private String arrivalTerminal;

    /**
     * Arrival gate.
     */
    @Column(name = "arrival_gate", length = 20)
    private String arrivalGate;

    /**
     * Flight duration in minutes.
     */
    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    /**
     * Distance between airports in kilometers.
     */
    @Column(name = "distance_km")
    private Integer distanceKm;

    /**
     * Delay in minutes.
     */
    @Column(name = "delay_minutes", nullable = false)
    @Builder.Default
    private Integer delayMinutes = 0;

    /**
     * Reason for cancellation/delay.
     */
    @Column(name = "disruption_reason", length = 500)
    private String disruptionReason;

    /**
     * Whether passengers can currently book this flight.
     */
    @Column(name = "bookable", nullable = false)
    @Builder.Default
    private boolean bookable = true;

    /**
     * Creation timestamp.
     */
    @CreatedDate
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private Instant createdAt;

    /**
     * Last modification timestamp.
     */
    @LastModifiedDate
    @Column(
            name = "updated_at",
            nullable = false
    )
    private Instant updatedAt;

    @PrePersist
    @PreUpdate
    private void calculateDuration() {

        if (scheduledDeparture != null
                && scheduledArrival != null) {

            this.durationMinutes = (int) Duration.between(
                    scheduledDeparture,
                    scheduledArrival
            ).toMinutes();
        }
    }
}