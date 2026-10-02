package com.airline.embeddable;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PremiumServiceBenifites {

    @Column(nullable = false)
    @Builder.Default
    private Boolean loungeAccess = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean airportTransfer = false;
}
