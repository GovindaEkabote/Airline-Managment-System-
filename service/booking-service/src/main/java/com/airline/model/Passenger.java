
package com.airline.model;

import com.airline.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "passengers",
        indexes = {
                @Index(name = "idx_passenger_booking", columnList = "booking_id"),
                @Index(name = "idx_passenger_primary_user",
                        columnList = "primary_user_id")
        }
)
@EntityListeners(AuditingEntityListener.class)
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(length = 254)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Gender gender;



    @Column(length = 100)
    private String nationality;

    @Column(name = "frequent_flyer_number", length = 100)
    private String frequentFlyerNumber;

    @Column(name = "primary_user_id")
    private Long primaryUserId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Version
    private Long version;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getAge() {
        if (dateOfBirth == null) {
            throw new IllegalStateException(
                    "Date of birth is required to calculate age"
            );
        }

        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public boolean isAdult() {
        return dateOfBirth != null && getAge() >= 18;
    }
}
