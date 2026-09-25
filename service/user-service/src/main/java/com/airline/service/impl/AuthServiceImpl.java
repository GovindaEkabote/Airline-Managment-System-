package com.airline.service.impl;

import com.airline.config.JwtProvider;
import com.airline.enums.Roles;
import com.airline.exception.DuplicateResourceException;
import com.airline.exception.InvalidCredentialsException;
import com.airline.mapper.UserMapper;
import com.airline.model.User;
import com.airline.playload.response.AuthResponse;
import com.airline.playload.response.LoginRequest;
import com.airline.playload.response.SignupRequest;
import com.airline.repository.UserRepository;
import com.airline.service.AuthService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    /*
    * 1. Find user by email
   ↓
2. Does user exist?
   ↓
3. Is user active?
   ↓
4. Verify raw password against BCrypt password
   ↓
5. Create Authentication with user's authorities
   ↓
6. Generate JWT
   ↓
7. Update lastLoginAt
   ↓
8. Return AuthResponse
    * */
    @Override
    public AuthResponse login(LoginRequest request) throws Exception {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new InvalidCredentialsException(
                    "Invalid email or password",
                    "Invalid email or password",
                    "email"
            );
        }
        if (!user.isActive()) {
            throw new Exception("User account is inactive");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException(
                    "Invalid email or password",
                    "",
                    request
            );
        }

       List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + user.getRole().name()
                )
        );
       Authentication authentication = new UsernamePasswordAuthenticationToken(
               user.getEmail(),
               null,
               authorities
       );

       String jwt = jwtProvider.generateToken(
               authentication,
               user.getId()
       );

       user.setLastLoginAt(LocalDateTime.now());
       userRepository.save(user);

       AuthResponse authResponse = new AuthResponse();
       authResponse.setJwt(jwt);
       authResponse.setUser(UserMapper.toResponse(user));
       authResponse.setMessage("Login successful");
        return authResponse;
    }

    @Override
    public AuthResponse signup(SignupRequest request) {

        User existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser != null) {
            throw new DuplicateResourceException(
                    "Email already registered",
                    "",
                    request.getEmail()
            );
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail().trim().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(Roles.USER)
                .isActive(true)
                .verified(false)
                .build();

        User savedUser = userRepository.save(user);

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + savedUser.getRole().name()
                )
        );

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        savedUser.getEmail(),
                        null,
                        authorities
                );

        String jwt = jwtProvider.generateToken(
                authentication,
                savedUser.getId()
        );

        AuthResponse response = new AuthResponse();

        response.setJwt(jwt);
        response.setUser(UserMapper.toResponse(savedUser));
        response.setMessage("User registered successfully");

        return response;
    }

    @Override
    public void logout(String email) {

    }
}
