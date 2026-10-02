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
public class FlexibilityBenifites {

    @Column(nullable = false)
    @Builder.Default
    private Boolean freeDateChange = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean partialRefund = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean fullRefund = false;
}
