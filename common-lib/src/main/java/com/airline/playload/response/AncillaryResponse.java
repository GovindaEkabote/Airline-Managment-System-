package com.airline.playload.response;


import com.airline.domain.AncillaryMataData;
import com.airline.enums.AncillaryType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AncillaryResponse {

    private Long id;

    private AncillaryType ancillaryType;

    private String subType;

    private String rfisc;

    private String name;

    private  String description;

    private String categoryDisplayName;

    private String categoryIcon;

    private String iconUrl;

    private AncillaryMataData ancillaryMataData;

    private List<InsuranceConverageResponse> converageResponses;

    private Integer displayOrder;

    private Long airlineId;
}
