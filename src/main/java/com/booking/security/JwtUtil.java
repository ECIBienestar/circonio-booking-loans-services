package com.booking.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:application.properties"})
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    public DecodedJWT decodeToken(String token) {
        JWTVerifier verifier = JWT.require(getAlgorithm()).build();
        return verifier.verify(token);
    }

    public String extractUsername(String token) {
        return decodeToken(token).getSubject();
    }

    public String extractClaim(String token, String claimName) {
        return decodeToken(token).getClaim(claimName).asString();
    }

    public boolean validateToken(String token) {
        try {
            decodeToken(token); // Lanza excepción si es inválido
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
