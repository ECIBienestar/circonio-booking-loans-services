package com.booking.security;

import io.jsonwebtoken.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private String secretKey = "EPRiC0Bt0/2KcBRRWqVKhEWzModEtI6Q4K05RWuLgVQV4Xw92Ulk9kHPmQVjiRW5c9XtLNm4lgNoridiLgvZpg=="; 

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", secretKey); 
    }

    private String generateTestToken(String subject, Map<String, Object> claims, long expirationMillis) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(SignatureAlgorithm.HS256, secretKey) 
                .compact();
    }

    @Test
    void testExtractClaimsReturnsCorrectData() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_USER");
        claims.put("email", "jeimy@email.com");

        String token = generateTestToken("jeimy123", claims, 100000);
        Claims extracted = jwtUtil.extractClaims(token);

        assertEquals("jeimy123", extracted.getSubject());
        assertEquals("ROLE_USER", extracted.get("role"));
        assertEquals("jeimy@email.com", extracted.get("email"));
    }

    @Test
    void testExtractUsername() {
        String token = generateTestToken("jeimy321", new HashMap<>(), 100000);
        assertEquals("jeimy321", jwtUtil.extractUsername(token));
    }

    @Test
    void testExtractRole() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_ADMIN");

        String token = generateTestToken("admin123", claims, 100000);
        assertEquals("ROLE_ADMIN", jwtUtil.extractRole(token));
    }

    @Test
    void testValidateToken_Valid() {
        String token = generateTestToken("validUser", new HashMap<>(), 100000);
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void testValidateToken_Expired() {
        String token = generateTestToken("expiredUser", new HashMap<>(), -10000);
        assertFalse(jwtUtil.validateToken(token));
    }

    @Test
    void testValidateToken_InvalidSignature() {
        // Crear un token firmado con una clave incorrecta
        String fakeSecret = "claveFalsaDemasiadoDebilYNoSeguraPeroSuficienteParaElTest1234";
        String token = Jwts.builder()
                .setSubject("hacker")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 100000))
                .signWith(SignatureAlgorithm.HS256, fakeSecret)
                .compact();

        assertFalse(jwtUtil.validateToken(token));
    }
}

