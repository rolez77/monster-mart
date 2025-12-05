package com.rolez.backend.auth;

public record AuthRequest(
        String username,
        String password
) {
}
