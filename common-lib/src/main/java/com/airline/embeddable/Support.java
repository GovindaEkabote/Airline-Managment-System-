package com.airline.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Support {
    private String email;
    private String phone;
    private String hours;
}
