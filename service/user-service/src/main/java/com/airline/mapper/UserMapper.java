package com.airline.mapper;


import com.airline.model.User;
import com.airline.playload.dto.UserDto;
import com.airline.playload.response.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponse toResponse(User user) {

        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .active(user.isActive())
                .verified(user.isVerified())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }

    public static List<UserResponse> toDTOList(List<User> user){
        return user
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }
}
