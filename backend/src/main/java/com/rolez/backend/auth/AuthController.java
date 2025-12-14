package com.rolez.backend.auth;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authenticationService;

    public AuthController(AuthService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // 1. Log Start
            System.out.println("DEBUG: Starting Login Logic...");

            AuthResponse authResponse = authenticationService.login(authRequest);

            // 2. Log Middle
            System.out.println("DEBUG: Service finished. Token: " + authResponse.token());

            // 3. Test for Nulls (Common cause of crashes)
            if (authResponse.userDTO() == null) {
                System.out.println("DEBUG: ALERT! UserDTO is null!");
            } else {
                System.out.println("DEBUG: UserDTO is ready: " + authResponse.userDTO().username());
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, authResponse.token())
                    .body(authResponse);

        } catch (Exception e) {
            // 4. THE TRAP: If anything crashes, print the REAL error
            System.out.println("=================================");
            System.out.println("CRITICAL LOGIN ERROR CAUGHT:");
            e.printStackTrace(); // <--- This will show us the invisible error
            System.out.println("=================================");

            return ResponseEntity.internalServerError().body("Login Failed: " + e.getMessage());
        }
//        AuthResponse authResponse = authenticationService.login(authRequest);
//
//
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.AUTHORIZATION, authResponse.token())
//                .body(authResponse);
    }

}
