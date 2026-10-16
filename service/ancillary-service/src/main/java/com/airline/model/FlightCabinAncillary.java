package com.airline.model;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "flight_cabin_ancillary"
)
public class FlightCabinAncillary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long flightId;

    @Column(nullable = false)
    private Long cabinClassId;

    @ManyToOne(fetch = FetchType.EAGER)
    private Ancillary ancillary;

    private Boolean available;

    private Integer maxQuantity;

    private Double price;

    private Boolean includedInFare=false;


}
