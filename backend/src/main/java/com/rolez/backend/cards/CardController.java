package com.rolez.backend.cards;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cards")
public class CardController {


    private final CardsService cardsService;

    @Autowired
    public CardController(CardsService cardsService) {
        this.cardsService = cardsService;
    }

    @GetMapping
    public List<Card> getCards(@RequestParam(required = false) String cardName) {
        if(cardName != null) {
            return cardsService.getCardByName(cardName);
        }else{
            return cardsService.getCards();
        }
    }

    @PostMapping
    public Card addCard(@RequestBody Card card) {
        return cardsService.addCard(card);
    }

    @DeleteMapping("/{id}")
    public void deleteCardById(@RequestParam Long id) {
        if(cardsService.existsById(id)){
            cardsService.deleteCard(id);
        }else{
            System.console().printf("Card with id: %s does not exist", id);
        }

    }

}
