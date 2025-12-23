package com.rolez.backend.cards;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/cards")
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
    public void addCard(@RequestBody CardRegistrationRequest card, Authentication authentication) {
        System.out.println("1. API HIT: Received request for " + card.name());
        String email = authentication.getName();
        cardsService.addCard(card, email);
    }

    @DeleteMapping("/{id}")
    public void deleteCardById(@RequestParam Integer id) {
        if(cardsService.existsById(id)){
            cardsService.deleteCard(id);
        }else{
            System.console().printf("Card with id: %s does not exist", id);
        }

    }

    public void uploadImage(
            @PathVariable("cardId") Integer cardId,
            @RequestPart("file") MultipartFile file
    ){
        cardsService.uploadCardImage(cardId, file);
    }

}
