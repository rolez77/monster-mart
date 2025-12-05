package com.rolez.backend.users;

public record UserRegistrationRequest(
        String name,
        String email,
        String password
) {

}
