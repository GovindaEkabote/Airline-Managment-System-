package com.airline.service;

import com.airline.model.User;
import com.airline.playload.dto.UserDto;
import com.airline.playload.response.AuthResponse;
import com.airline.playload.response.LoginRequest;
import com.airline.playload.response.SignupRequest;

public interface AuthService {

    AuthResponse login(LoginRequest request) throws Exception;

    AuthResponse signup(SignupRequest request);

    void logout(String email);
}
