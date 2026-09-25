package com.airline.playload.response;

import com.airline.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    private String fullName;
    private String email;
    private String phone;
    private Roles role;
    private boolean active;
    private boolean verified;
}
