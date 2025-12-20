package com.rolez.backend.cards;

import com.rolez.backend.users.User;

public record CardDTO(
        Integer id,
        String name,
        String condition,
        String set,
        double price,
        User user
) {
}
