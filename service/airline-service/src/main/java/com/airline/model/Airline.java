package com.airline.model;

import com.airline.embeddable.Support;
import com.airline.enums.AirlineStatus;
import com.airline.playload.response.CityResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(
        name = "airline",
        indexes = {
                @Index(name = "idx_airline_name", columnList = "name"),
                @Index(name = "idx_airline_status", columnList = "status"),
                @Index(name = "idx_airline_headquarters_city", columnList = "headquarters_city_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(
            name = "iata_code",
            nullable = false,
            unique = true,
            length = 2
    )
    private String iataCode;

    @Column(
            name = "icao_code",
            nullable = false,
            unique = true,
            length = 3
    )
    private String icaoCode;

    @Column(
            name = "name",
            nullable = false,
            length = 100
    )
    private String name;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "alias", length = 100)
    private String alias;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @Column(name = "website", length = 500)
    private String website;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 20
    )
    @Builder.Default
    private AirlineStatus status = AirlineStatus.ACTIVE;

    @Column(name = "alliance", length = 50)
    private String alliance;

    /**
     * Reference to Location Service's City ID.
     * No JPA relationship because City belongs to another microservice.
     */
    @Column(name = "headquarters_city_id")
    private Long headquartersCityId;

    /**
     * ID of the user who last modified this airline.
     */
    @Column(name = "updated_by_id")
    private Long updatedById;

    @Embedded
    private Support support;

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