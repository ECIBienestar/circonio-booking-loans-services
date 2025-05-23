package com.booking.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtRequestFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private Claims claims;

    private JwtRequestFilter jwtRequestFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtRequestFilter = new JwtRequestFilter(jwtUtil);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testValidTokenSetsAuthentication() throws ServletException, IOException {
        String token = "valid.token.here";
        String header = "Bearer " + token;

        when(request.getHeader("Authorization")).thenReturn(header);
        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.extractClaims(token)).thenReturn(claims);

        when(claims.get("id", String.class)).thenReturn("123");
        when(claims.get("role", String.class)).thenReturn("ROLE_USER");
        when(claims.get("fullName", String.class)).thenReturn("Jeimy Rodríguez");
        when(claims.get("email", String.class)).thenReturn("jeimy@email.com");

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("123", ((UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getId());

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testNoTokenSkipsAuthentication() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testInvalidTokenSkipsAuthentication() throws ServletException, IOException {
        String token = "invalid.token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.validateToken(token)).thenReturn(false);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }
}

