package com.rolez.backend.auth;


import com.rolez.backend.users.UserDTO;


public record AuthResponse (
        String token,
        UserDTO userDTO){
}

