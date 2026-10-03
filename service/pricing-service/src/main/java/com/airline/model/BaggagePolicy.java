package com.airline.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "baggage_policy")
public class BaggagePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fare_id", nullable = false)
    private Fare fare;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;
    private Double cabinBaggageMaxWeightLimit;
    private Integer cabinBaggagePieces;
    private Double cabinBaggageWeightPerPiece;
    private Double cabinBaggageMaxDimensions;

    private Double checkInBaggageMaxWeightLimit;
    private Integer checkInBaggagePieces;
    private Double checkInBaggageWeightPerPiece;
    private Integer freeCheckBaggageAllowance;

    private Boolean priorityBaggage;
    private Boolean extraBaggageAllowed;
    private Long airlineId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}