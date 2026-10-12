package com.airline.model;

import com.airline.enums.CoverageType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "insurance_coverage",
        indexes = {
                @Index(
                        name = "idx_insurance_coverage_ancillary",
                        columnList = "ancillary_id"
                ),
                @Index(
                        name = "idx_insurance_coverage_type",
                        columnList = "coverage_type"
                )
        }
)
public class InsuranceCoverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ancillary_id", nullable = false)
    private  Ancillary ancillary;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "coverage_type", nullable = false, length = 50)
    private CoverageType coverageType;

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    private String name;

    @Size(max = 200)
    @Column(length = 1000)
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "coverage_amount", nullable = false)
    private Double coverageAmount;

    @Builder.Default
    @Column(name = "is_flat", nullable = false)
    private Boolean isFlat = true;

    @Column(name = "claim_condition", columnDefinition = "TEXT")
    private String claimCondition;

    @Column(name = "display_order")
    private Integer displayOrder;

    private String emergencyContact;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;
}
