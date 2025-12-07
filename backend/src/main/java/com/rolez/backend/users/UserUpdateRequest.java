package com.rolez.backend.users;

public record UserUpdateRequest(
        String name,
        String username,
        String email

) {
}
