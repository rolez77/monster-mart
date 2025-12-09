package com.rolez.backend.cards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardsService
{
    private final CardRepository cardRepository;
    @Autowired
    public CardsService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getCards() {
        return cardRepository.findAll();
    }

    public Card addCard(Card card) {
        return cardRepository.save(card);
    }

    public List<Card> getCardByName(String cardName) {
        return cardRepository.findAll().stream()
                .filter(cards -> cards.getName().toLowerCase().contains(cardName.toLowerCase()))
                .collect(Collectors.toList());
    }

    public boolean existsById(Integer cardId) {
        return cardRepository.existsById(cardId);
    }

    public void deleteCard(Integer cardId) {
        cardRepository.deleteById(cardId);
    }


    public void uploadCardImage(Integer cardId, MultipartFile file) {
    }
}
