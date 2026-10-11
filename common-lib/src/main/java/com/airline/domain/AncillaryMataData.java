package com.airline.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AncillaryMataData {

    private BaggageMataData baggageMataData;
    private String protectionSummanry;
    private String specialServiceDetails;
    private String upgradeDetails;
}
