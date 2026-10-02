package com.airline.model;

import com.airline.embeddable.*;
import com.airline.enums.CabinClassType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "fare"
)
@EntityListeners(AuditingEntityListener.class)
public class Fare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String rbdCode;

    @Column(nullable = false)
    private Long flightId;

    @Column(nullable = false)
    private Long cabinClassId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CabinClassType cabinClass = CabinClassType.ECONOMY;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false)
    private BigDecimal baseFare;

    @Column(nullable = false)
    private BigDecimal taxesAndFees;

    private BigDecimal airlineFees;

    @Column(nullable = false)
    private BigDecimal currentPrice;

    private BigDecimal totalPrice;

    private String fareLabel;

    // todo: when create baggage policy
//    private BaggagePolicy baggagePolicy;

    // todo: when create fare rules
//    private FareRule fareRule;

    @Embedded
    @Builder.Default
    private SeatBenifites seatBenifites =  new  SeatBenifites();

    @Embedded
    @Builder.Default
    private BordingBenifites bordingBenifites =  new  BordingBenifites();

    @Embedded
    @Builder.Default
    private InFlightsBenifites inFlightsBenifites =  new  InFlightsBenifites();

    @Embedded
    @Builder.Default
    private FlexibilityBenifites flexibilityBenifites =  new  FlexibilityBenifites();

    @Embedded
    @Builder.Default
    private PremiumServiceBenifites premiumServiceBenifites =  new  PremiumServiceBenifites();

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;



}
