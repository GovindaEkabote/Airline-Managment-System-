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
public class BordingBenifites {

    @Column(nullable = false, name = "priority_boarding")
    @Builder.Default
    private Boolean priorityBoarding = false;
    @Column(nullable = false, name = "priority_checkin")
    @Builder.Default
    private Boolean priorityCheckin = false;
    @Column(nullable = false, name = "priority_security")
    @Builder.Default
    private Boolean prioritySecurity = false;
}
