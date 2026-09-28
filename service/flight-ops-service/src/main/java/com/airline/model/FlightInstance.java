package com.airline.model;

import com.airline.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flight_instance")
@EntityListeners(AuditingEntityListener.class)
public class FlightInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "airline_id", nullable = false)
    private Long airlineId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Column(name = "departure_airport_id", nullable = false)
    private Long departureAirportId;

    @Column(name = "arrival_airport_id", nullable = false)
    private Long arrivalAirportId;

    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    @Column(name = "departure_date_time", nullable = false)
    private LocalDateTime departureDateTime;

    @Column(name = "arrival_date_time", nullable = false)
    private LocalDateTime arrivalDateTime;

    @Column(name = "total_seats", nullable = false)
    private Integer totalSeats;

    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FlightStatus status;

    @Column(name = "min_advance_booking_days")
    private Integer minAdvanceBookingDays;

    @Column(name = "max_advance_booking_days")
    private Integer maxAdvanceBookingDays;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Transient
    private String getFormatedDuration() {
        if (departureDateTime == null || arrivalDateTime == null){
            return null;
        }

        Duration duration =
                Duration.between(
                        departureDateTime, arrivalDateTime
                );
        long hours=duration.toHours();
        long minutes = duration.toMinutesPart();

        StringBuilder stringBuilder = new StringBuilder();

        if (hours > 0){
            stringBuilder.append(hours).append("h");
        }

        if (minutes > 0){
            stringBuilder.append(minutes).append("m");
        }

        return stringBuilder.toString();
    }
}
