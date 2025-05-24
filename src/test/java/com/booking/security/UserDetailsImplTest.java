/*package com.booking.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsImplTest {

    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        userDetails = new UserDetailsImpl("123", "Jeimy", "jeimy@example.com", "USER");
    }

    @Test
    void testGetId() {
        assertEquals("123", userDetails.getId());
    }

    @Test
    void testGetFullName() {
        assertEquals("Jeimy", userDetails.getFullName());
    }

    @Test
    void testGetEmail() {
        assertEquals("jeimy@example.com", userDetails.getEmail());
    }

    @Test
    void testGetRole() {
        assertEquals("USER", userDetails.getRole());
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertEquals(1, authorities.size());
        assertEquals("ROLE_USER", authorities.iterator().next().getAuthority());
    }

    @Test
    void testGetUsernameReturnsEmail() {
        assertEquals("jeimy@example.com", userDetails.getUsername());
    }

    @Test
    void testGetPasswordIsNull() {
        assertNull(userDetails.getPassword());
    }

    @Test
    void testAccountIsNotExpired() {
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void testAccountIsNotLocked() {
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void testCredentialsAreNotExpired() {
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(userDetails.isEnabled());
    }
}

*/