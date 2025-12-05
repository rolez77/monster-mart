package com.rolez.backend.auth;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authenticationService;

    public AuthController(AuthService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authenticationService.login(authRequest);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, authResponse.token())
                .body(authResponse);
    }

}
