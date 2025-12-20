package com.rolez.backend.cards;

import com.rolez.backend.exception.ResourceNotFoundException;
import com.rolez.backend.users.User;
import com.rolez.backend.users.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardsService
{
    private final UserDao userDao;
    private final CardRepository cardRepository;
    @Autowired
    public CardsService(@Qualifier("jdbc")UserDao userDao, CardRepository cardRepository) {
        this.userDao = userDao;
        this.cardRepository = cardRepository;
    }

    public List<Card> getCards() {
        return cardRepository.findAll();
    }

    public void addCard(CardRegistrationRequest request, String email) {

        Optional<User> user = Optional.ofNullable(userDao.selectUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));

        Card card = new Card(
                request.name(),
                request.set(),
                request.price(),
                request.imageId(),
                request.condition(),
                user

        );
        cardRepository.save(card);

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
