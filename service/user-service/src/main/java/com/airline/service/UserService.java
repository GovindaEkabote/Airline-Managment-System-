package com.airline.service;

import com.airline.model.User;
import com.airline.playload.dto.UserDto;
import com.airline.playload.response.UpdateUserRequest;
import com.airline.playload.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {


    UserResponse getUserByEmail(String email) throws Exception;

    UserResponse getUserById(Long id);

    UserDto getUserByIds(Long userId);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);



    Page<UserResponse> getAllUsers(Pageable pageable);

    Page<UserResponse> searchUsers(String query, Pageable pageable);

}
