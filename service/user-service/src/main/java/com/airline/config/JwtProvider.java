package com.airline.config;


import com.airline.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@Component
public class JwtProvider {

    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            JwtConstant.SECRET_KEY.getBytes()
    );


    public String generateToken(Authentication authentication, Long userId) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = populateAuthorities(authorities);
        String jwt = Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .claim("email", authentication.getName())
                .claim("authorities", roles)
                .claim("userId", userId)
                .signWith(secretKey)
                .compact();
        return jwt;
    }


    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
            Set<String> authoritiesSet = new HashSet<>();
            for (GrantedAuthority authority : authorities) {
                authoritiesSet.add(authority.getAuthority());
            }
            return String.join(",", authoritiesSet);
    }

}
