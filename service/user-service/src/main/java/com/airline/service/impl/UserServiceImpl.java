package com.airline.service.impl;

import com.airline.exception.DuplicateResourceException;
import com.airline.exception.InvalidCredentialsException;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.UserMapper;
import com.airline.model.User;
import com.airline.playload.response.UpdateUserRequest;
import com.airline.playload.response.UserResponse;
import com.airline.repository.UserRepository;
import com.airline.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Data
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException(
                    "email",
                    "email",
                    email
            );
        }
        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "email",
                        "email",
                        id
                ));
        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse updateUser(
            Long id,
            UpdateUserRequest request) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        "id",
                        "id",
                        id
                )
        );
        if (request.getEmail() != null
                && !request.getEmail().equalsIgnoreCase(user.getEmail())
                && userRepository.existsByEmail(
                request.getEmail().trim().toLowerCase()
        )) {

            throw new DuplicateResourceException(
                    "Email already registered"
            );
        }

        UserMapper.updateEntity(user, request);

        User updatedUser = userRepository.save(user);

        return UserMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "id",
                                "id",
                                id
                        )
                );
        userRepository.delete(user);
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserMapper::toResponse);
    }

    @Override
    public Page<UserResponse> searchUsers(String query, Pageable pageable) {

        return userRepository
                .searchUsers(query, pageable)
                .map(UserMapper::toResponse);
    }
}
