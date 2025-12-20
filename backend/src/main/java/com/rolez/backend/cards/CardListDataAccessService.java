package com.rolez.backend.cards;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CardListDataAccessService implements CardDao{

    private static final List<Card> cards = List.of();
    @Override
    public List<Card> selectAll() {
        return cards;
    }

    @Override
    public Optional<Card> selectCardById(Integer id) {
        return cards.stream().filter(card -> card.getId().equals(id)).findFirst();
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public boolean existsCardById(Integer id) {
        return cards.stream().anyMatch(card -> card.getId().equals(id));
    }

    @Override
    public void deleteCardById(Integer id) {
        cards.stream().filter(card -> card.getId().equals(id)).findFirst().ifPresent(cards::remove);
    }

    @Override
    public void updateCard(Card update) {
        cards.add(update);
    }

    @Override
    public Optional<Card> selectCardByName(String name) {
        return cards.stream().filter(card -> card.getName().equals(name)).findFirst();
    }
}
