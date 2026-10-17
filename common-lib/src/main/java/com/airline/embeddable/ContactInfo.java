package com.airline.embeddable;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class ContactInfo {


    private String email;
    private String phone;

}
