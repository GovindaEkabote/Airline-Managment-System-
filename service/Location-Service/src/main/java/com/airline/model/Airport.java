package com.airline.model;


import com.airline.embeddable.Address;
import com.airline.embeddable.GeoCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "airport",
        indexes = {
                @Index(name = "idx_airport_name", columnList = "name"),
                @Index(name = "idx_airport_iata_code", columnList = "iata_code"),
                @Index(name = "idx_airport_icao_code", columnList = "icao_code"),
                @Index(name = "idx_airport_city_id", columnList = "city_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(
            name = "iata_code",
            unique = true,
            nullable = false,
            length = 3
    )
    private String iataCode;

    @Column(
            name = "icao_code",
            unique = true,
            nullable = false,
            length = 4
    )
    private String icaoCode;

    @Column(
            name = "name",
            nullable = false,
            length = 100
    )
    private String name;

    @Embedded
    private Address address;

    @Embedded
    private GeoCode geoCode;

    @Column(name = "time_zone_id", length = 50)
    private String timeZoneId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    @JsonIgnore
    private City city;
}
