package com.rolez.backend.cards;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("cardJpa")
public class CardJPADataAccessService implements CardDao {
    private CardRepository cardRepository;
    public CardJPADataAccessService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public List<Card> selectAll() {
        Page<Card> page = cardRepository.findAll(Pageable.ofSize(1000));
        return page.getContent();
    }

    @Override
    public Optional<Card> selectCardById(Integer id) {
        return cardRepository.findById(id);
    }

    @Override
    public void addCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public boolean existsCardById(Integer id) {
        return cardRepository.existsById(id);
    }

    @Override
    public void deleteCardById(Integer id) {
        cardRepository.deleteById(id);
    }

    @Override
    public void updateCard(Card update) {
            cardRepository.save(update);
    }

    @Override
    public Optional<Card> selectCardByName(String name) {
        return cardRepository.findByName(name);
    }
}
