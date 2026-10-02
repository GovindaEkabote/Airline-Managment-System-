package com.airline.embeddable;


import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatBenifites {

    private Boolean extraSeatSpace= false;
    private Boolean preferredSeatChoice = false;
    private Boolean advancedSeatSelection = false;
    private Boolean guaranteedSeatTogether = false;

}
