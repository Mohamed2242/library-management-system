package com.mo.librarymanagement.controller;

import com.mo.librarymanagement.adapter.controller.AuthController;
import com.mo.librarymanagement.application.dto.AuthenticationRequest;
import com.mo.librarymanagement.application.dto.AuthenticationResponse;
import com.mo.librarymanagement.application.dto.RegistrationRequest;
import com.mo.librarymanagement.application.service.MyUserDetailsService;
import com.mo.librarymanagement.infrastructure.util.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private MyUserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testCreateAuthenticationToken_Success() {
        AuthenticationRequest request = new AuthenticationRequest("user", "password");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("user"); // Ensure mocked user has "user" as username
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);

        // Check the argument type for jwtUtil.generateToken and update the mock accordingly:
        when(jwtUtil.generateToken(any(String.class))).thenReturn("mock-jwt");

        ResponseEntity<AuthenticationResponse> response = authController.createAuthenticationToken(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("mock-jwt", response.getBody().getJwt());
    }


    @Test
    public void testCreateAuthenticationToken_Unauthorized() {
        AuthenticationRequest request = new AuthenticationRequest("user", "wrong-password");

        doThrow(new RuntimeException("Bad credentials")).when(authenticationManager)
            .authenticate(new UsernamePasswordAuthenticationToken("user", "wrong-password"));

        ResponseEntity<AuthenticationResponse> response = authController.createAuthenticationToken(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testRegister_Success() {
        RegistrationRequest request = new RegistrationRequest("user", "password");
        
        doNothing().when(userDetailsService).saveUser("user", "password");

        ResponseEntity<Map<String, String>> response = authController.register(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody().get("message"));
    }

    @Test
    public void testRegister_BadRequest() {
        RegistrationRequest request = new RegistrationRequest("user", "password");

        doThrow(new RuntimeException("Registration failed")).when(userDetailsService).saveUser("user", "password");

        ResponseEntity<Map<String, String>> response = authController.register(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}