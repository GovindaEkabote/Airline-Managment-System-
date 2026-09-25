package com.airline.playload.dto;

import com.airline.enums.Roles;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private Roles role;
    private boolean active;
    private boolean verified;
    private LocalDateTime lastLoginAt;
}
