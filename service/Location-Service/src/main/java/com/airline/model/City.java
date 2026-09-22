package com.airline.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Entity
@Table(
        name = "city",
        indexes = {
                @Index(name = "idx_city_name", columnList = "name"),
                @Index(name = "idx_city_country_code", columnList = "country_code")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "City code cannot be blank")
    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "City code must be 3 uppercase letters"
    )
    @Column(
            name = "city_code",
            nullable = false,
            length = 3,
            unique = true
    )
    private String cityCode;

    @NotBlank(message = "Country cannot be blank")
    @Size(max = 100, message = "Country cannot exceed 100 characters")
    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @NotBlank(message = "Country code cannot be blank")
    @Pattern(
            regexp = "^[A-Z]{2}$",
            message = "Country code must be 2 uppercase letters"
    )
    @Column(
            name = "country_code",
            nullable = false,
            length = 2
    )
    private String countryCode;

    @NotBlank(message = "Region code cannot be blank")
    @Size(max = 10, message = "Region code cannot exceed 10 characters")
    @Column(name = "region_code", nullable = false, length = 10)
    private String regionCode;

    @NotBlank(message = "Time zone ID cannot be blank")
    @Size(max = 64, message = "Time zone ID cannot exceed 64 characters")
    @Column(name = "time_zone_id", nullable = false, length = 64)
    private String timeZoneId;

    @Column(name = "active", nullable = false)
    @Builder.Default
    private boolean active = true;

    @PrePersist
    @PreUpdate
    private void normalize() {

        if (cityCode != null) {
            cityCode = cityCode.trim().toUpperCase();
        }

        if (countryCode != null) {
            countryCode = countryCode.trim().toUpperCase();
        }

        if (regionCode != null) {
            regionCode = regionCode.trim().toUpperCase();
        }

        if (timeZoneId != null) {
            timeZoneId = timeZoneId.trim();
        }

        if (name != null) {
            name = name.trim();
        }

        if (country != null) {
            country = country.trim();
        }
    }
}