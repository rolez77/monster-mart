package com.rolez.backend.users;

public record UserRegistrationRequest(
        String name,
        String username,
        String email,
        String password
) {

}
