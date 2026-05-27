package com.risosuit.CajeroService.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class JWTService {

    private static final String SECRET =
            "mi_clave_super_secreta_muy_larga_123456";

    private static final long EXPIRATION =
            1000 * 60 * 60; // 1 hora

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(
                SECRET.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(String numTarjeta) {
        return Jwts.builder()
                .setSubject(numTarjeta)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION)
                )
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getTarjetaIfValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            
            if (claims != null && !claims.getExpiration().before(new Date())) {
                return claims.getSubject(); 
            }
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Token inválido o expirado: " + e.getMessage());
        }
        return null; 
    }

    public String extractNumTarjeta(String token) {
        Claims claims = extractAllClaims(token);
        return claims != null ? claims.getSubject() : null;
    }

    public boolean isTokenValid(String token) {
        return getTarjetaIfValid(token) != null;
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            return null; 
        }
    }
}