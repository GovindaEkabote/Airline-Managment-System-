package com.airline.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "flight_meal"
)
@EntityListeners(AuditingEntityListener.class)
public class FlightMeal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long flightId;

    @ManyToOne
    private Meal meal;

    private Boolean available=true;

    private Double price;

    private Integer displayOrder=0;
}
