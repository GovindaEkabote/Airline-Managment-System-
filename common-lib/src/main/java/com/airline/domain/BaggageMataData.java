package com.airline.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaggageMataData {

    private Integer weight;
    private String unit;
    private Integer pieces;
    private String category;
    private String dimensions;
    private String notes;
}
