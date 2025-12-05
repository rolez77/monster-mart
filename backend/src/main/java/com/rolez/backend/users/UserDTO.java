package com.rolez.backend.users;

import java.util.List;

public record UserDTO (
        Long id,
        String email,
        String name,
        String username,
        String profilePictureUrl,
        List<String>roles
){


}
