package com.rolez.backend.cards;

public record CardRegistrationRequest(
        String name,
        String set,
        double price,
        String imageId,
        String condition
) {
}
