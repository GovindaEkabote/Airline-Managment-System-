package com.airline.embeddable;


import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class GeoCode {

    private double latitude;
    private double longitude;
}
