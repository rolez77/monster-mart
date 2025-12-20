package com.rolez.backend.cards;

import java.util.List;
import java.util.Optional;

public interface CardDao {
    List<Card> selectAll();
    Optional<Card> selectCardById(Integer id);
    void addCard(Card card);
    boolean existsCardById(Integer id);
    void deleteCardById(Integer id);
    void updateCard(Card update);
    Optional<Card> selectCardByName(String name);
}
