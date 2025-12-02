package com.rolez.backend.auth;


import org.springframework.security.authentication.AuthenticationManager;


public class AuthService {

    private final AuthenticationManager authenticationManager;
    public AuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}

