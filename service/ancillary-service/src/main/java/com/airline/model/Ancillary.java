package com.airline.model;

import com.airline.domain.AncillaryMataData;
import com.airline.enums.AncillaryType;
import com.airline.service.AncillaryMataDataConverter;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "ancillary",
        indexes = {
                @Index(name = "idx_ancillary_airline_id", columnList = "airline_id"),
                @Index(name = "idx_ancillary_type", columnList = "ancillary_type")
        }
)
public class Ancillary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ancillary_type", nullable = false)
    private AncillaryType ancillaryType;

    private String subType;

    private String rfisc;

    @Column(nullable = false)
    private String name;

    private  String description;

    private String iconUrl;

    @Convert(converter = AncillaryMataDataConverter.class)
    @Column(name = "metadata", columnDefinition = "json")
    private AncillaryMataData ancillaryMataData;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "airline_id", nullable = false)
    private Long airlineId;


}
