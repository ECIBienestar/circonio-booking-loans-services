/*package com.booking.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private String secret = "testSecret";
    private String token;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();

        // Simular inyección de Spring
        try {
            var field = JwtUtil.class.getDeclaredField("secret");
            field.setAccessible(true);
            field.set(jwtUtil, secret);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Crear un token válido
        token = JWT.create()
                .withSubject("testuser")
                .withClaim("email", "test@example.com")
                .withClaim("role", "USER")
                .sign(Algorithm.HMAC256(secret));
    }

    @Test
    void shouldValidateValidToken() {
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void shouldInvalidateWrongToken() {
        String invalidToken = token + "invalid";
        assertFalse(jwtUtil.validateToken(invalidToken));
    }

    @Test
    void shouldExtractUsername() {
        assertEquals("testuser", jwtUtil.extractUsername(token));
    }

    @Test
    void shouldExtractClaim() {
        assertEquals("test@example.com", jwtUtil.extractClaim(token, "email"));
        assertEquals("USER", jwtUtil.extractClaim(token, "role"));
    }

    @Test
    void shouldThrowExceptionForInvalidTokenInDecode() {
        String badToken = "this.is.not.valid";
        assertThrows(Exception.class, () -> jwtUtil.decodeToken(badToken));
    }
}
*/