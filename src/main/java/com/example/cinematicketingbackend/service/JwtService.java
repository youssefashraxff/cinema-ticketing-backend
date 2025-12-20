package com.example.cinematicketingbackend.service;


import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.cinematicketingbackend.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET = "cinema-ticketing-super-secret-key-123456";
    private static final long EXPIRATION = 1000 * 60 * 60; // 1 hour

    public static String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
}