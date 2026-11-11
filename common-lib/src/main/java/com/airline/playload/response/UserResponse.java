package com.airline.playload.response;

import com.airline.enums.Roles;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private Roles role;
    private boolean active;
    private boolean verified;
    private LocalDateTime lastLoginAt;

}

