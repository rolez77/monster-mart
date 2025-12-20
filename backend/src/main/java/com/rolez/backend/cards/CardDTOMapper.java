package com.rolez.backend.cards;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CardDTOMapper implements Function<Card,CardDTO> {
    @Override
    public CardDTO apply(Card card) {
        return new CardDTO(
                card.getId(),
                card.getName(),
                card.getCondition(),
                card.getSet(),
                card.getPrice(),
                card.getUser()
        );
    }
}
