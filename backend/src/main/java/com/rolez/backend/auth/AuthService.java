package com.rolez.backend.auth;


import com.rolez.backend.jwt.JWTService;
import com.rolez.backend.users.User;
import com.rolez.backend.users.UserDTO;
import com.rolez.backend.users.UserDTOMapper;
import com.rolez.backend.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDTOMapper userDTOMapper;
    private final JWTService jwtService;

    public AuthService(AuthenticationManager authenticationManager, UserDTOMapper userDTOMapper, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDTOMapper = userDTOMapper;
        this.jwtService = jwtService;
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        User principal = (User) authentication.getPrincipal();
        UserDTO userDTO = userDTOMapper.apply(principal);
        String token = jwtService.issueToken(userDTO.username(), userDTO.roles());
        return new AuthResponse(token, userDTO);
    }
}

