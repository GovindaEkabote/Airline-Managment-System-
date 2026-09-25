package com.airline.mapper;


import com.airline.model.User;
import com.airline.playload.dto.UserDto;
import com.airline.playload.response.UpdateUserRequest;
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

    public static User updateEntity(User user, UpdateUserRequest updateUserRequest){

        if (updateUserRequest.getFullName() != null) {
            user.setFullName(updateUserRequest.getFullName());
        }

        if (updateUserRequest.getEmail() != null) {
            user.setEmail(updateUserRequest.getEmail());
        }

        if (updateUserRequest.getPhone() != null){
            user.setPhone(updateUserRequest.getPhone());
        }

        if (updateUserRequest.getRole() != null){
            user.setRole(updateUserRequest.getRole());
        }

        return user;
    }
}
