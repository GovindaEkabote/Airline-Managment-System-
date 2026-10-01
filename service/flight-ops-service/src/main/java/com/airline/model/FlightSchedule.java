package com.airline.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "flight_schedule",
        indexes = {
                @Index(name = "idx_schedule_flight_id", columnList = "flight_id"),
                @Index(name = "idx_schedule_departure_airport", columnList = "departure_airport_id"),
                @Index(name = "idx_schedule_arrival_airport", columnList = "arrival_airport_id"),
                @Index(name = "idx_schedule_start_end_date", columnList = "start_date, end_date"),
                @Index(name = "idx_schedule_active", columnList = "is_active")
        }
)
@EntityListeners(AuditingEntityListener.class)
public class FlightSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "airline_id", nullable = false)
    private Long airlineId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "flight_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_schedule_flight")
    )
    private Flight flight;

    @Column(name = "departure_airport_id", nullable = false)
    private Long departureAirportId;

    @Column(name = "arrival_airport_id", nullable = false)
    private Long arrivalAirportId;

    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    private LocalTime arrivalTime;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "flight_schedule_operating_days",
            joinColumns = @JoinColumn(name = "schedule_id")
    )
    @Column(name = "operating_day", nullable = false)
    private List<DayOfWeek> operatingDays;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}