package com.nhg.account.service.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Json Web Token
 * It's used for generate or validate the token or to retrieve information contained in the subject.
 *
 */
@Service
public class JwtService {

    @Value("${security.jwt.secret_key}")
    private String jwtSecretKey;

    @Value("${security.jwt.expiration_ms}")
    private int jwtExpirationMs;

    private Jws<Claims> decode(String token) {
        return Jwts.parser().setSigningKey(this.jwtSecretKey).parseClaimsJws(token);
    }

    /**
     * Get the username contained in the JWT's subject
     *
     * @param token JWT
     * @return username
     */
    public String getUsernameFromToken(String token) {
        return this.decode(token)
                .getBody()
                .getSubject();
    }

    public Date getExpirationFromToken(String token) {
        return this.decode(token)
                .getBody()
                .getExpiration();
    }

    /**
     * Generate a Json Web Token from the given authentication.
     * It will be secured with SHA512 and a secret key given in the configuration.
     * The principal username will be stored in the subject.
     *
     * @param username account's username
     * @return JWT
     */
    public String generateToken(String username) {

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, this.jwtSecretKey)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + this.jwtExpirationMs))
                .compact();
    }

    /**
     * Check if the given token is valid or not.
     *
     * @param token JWT
     * @return true if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token) {
        try {
            this.decode(token);
            return true;
        } catch (Exception ignored) {}

        return false;
    }
}