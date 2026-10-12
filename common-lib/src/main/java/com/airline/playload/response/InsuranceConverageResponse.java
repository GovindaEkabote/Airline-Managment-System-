package com.airline.playload.response;

import com.airline.enums.CoverageType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceConverageResponse {

    private Long id;
    private  Long ancillaryId;
    private String ancillaryName;
    private CoverageType coverageType;
    private String name;
    private String description;
    private Double coverageAmount;
    private String emergencyContact;
    private boolean isFlat;
    private String claimCondition;
    private Integer displayOrder;
    private boolean active;
}
