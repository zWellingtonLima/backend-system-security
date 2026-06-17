package com.group1.gestao_seguranca.security;

import com.group1.gestao_seguranca.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret_key}")
    private String JWT_SECRET_KEY;

    @Value("${jwt.access-token-expiration-ms}")
    private long JWT_ACCESS_TOKEN_EXPIRATION_MS;

    @Value("${jwt.refresh-token-expiration-ms}")
    private long JWT_REFRESH_TOKEN_EXPIRATION_MS;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET_KEY));
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("nome", user.getNome())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_EXPIRATION_MS))
                .signWith(getKey())
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_EXPIRATION_MS))
                .signWith(getKey())
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("JWT Expired Exception: " + ex); // TODO: Remover log interno
            return false;
            // Token expirado
        } catch (JwtException ex) {
            System.out.println("JWT Exception: " + ex); // TODO: Remover log interno
            return false;
            // assinatura mal formatada ou inválida
        }
    }

    public Integer extractUserId(String token) {
        return Integer.parseInt(extractClaims(token).getSubject());
    }
}
