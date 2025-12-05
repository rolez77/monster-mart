package com.rolez.backend.users;

import com.rolez.backend.jwt.JWTService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private UserService userService;
    private final JWTService jwtService;

    public UserController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<UserDTO> getUsers() {return userService.getAllUsers();}

    @GetMapping("{userId}")
    public UserDTO getUser(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        userService.addUser(request);
        String jwtToken = jwtService.issueToken(request.email(), "ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION,  jwtToken)
                .build();
    }
}
