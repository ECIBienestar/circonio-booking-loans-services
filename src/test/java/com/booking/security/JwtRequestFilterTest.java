package com.booking.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtRequestFilterTest {

    private JwtUtil jwtUtil;
    private JwtRequestFilter jwtRequestFilter;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        jwtUtil = mock(JwtUtil.class);
        jwtRequestFilter = new JwtRequestFilter(jwtUtil);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldAuthenticateWhenTokenIsValid() throws ServletException, IOException {
        String token = "valid.token.here";
        String header = "Bearer " + token;

        when(request.getHeader("Authorization")).thenReturn(header);
        when(jwtUtil.validateToken(token)).thenReturn(true);

        // Mock DecodedJWT and claims
        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        when(jwtUtil.decodeToken(token)).thenReturn(decodedJWT);

        Map<String, String> claims = Map.of(
                "id", "123",
                "fullName", "John Doe",
                "email", "john@example.com",
                "role", "USER"
        );

        claims.forEach((key, value) -> {
            Claim claim = mock(Claim.class);
            when(claim.asString()).thenReturn(value);
            when(decodedJWT.getClaim(key)).thenReturn(claim);
        });

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        assertNotNull(auth);
        assertEquals("john@example.com", ((UserDetailsImpl) auth.getPrincipal()).getEmail());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticateWhenHeaderIsMissing() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticateWhenTokenIsInvalid() throws ServletException, IOException {
        String token = "bad.token";
        String header = "Bearer " + token;

        when(request.getHeader("Authorization")).thenReturn(header);
        when(jwtUtil.validateToken(token)).thenReturn(false);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }
}
