package com.airline.playload.request;

import com.airline.domain.AncillaryMataData;
import com.airline.enums.AncillaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AncillaryRequest {

    @NotNull
    private AncillaryType ancillaryType;
    private String subType;

    private String rfisc;

    private String name;

    private  String description;

    private String iconUrl;

    private AncillaryMataData ancillaryMataData;

    private Integer displayOrder;

    private Long airlineId;
}
