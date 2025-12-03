package com.rolez.backend.users;

public record UserDTO (
        Long id,
        String email,
        String name,
        String username,
        String profilePictureUrl
){


}
