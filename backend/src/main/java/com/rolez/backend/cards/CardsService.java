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
    private final CardDao cardDao;
    @Autowired
    public CardsService(@Qualifier("jdbc")UserDao userDao, @Qualifier("cardJdbc")CardDao cardDao) {
        this.userDao = userDao;
        this.cardDao = cardDao;
    }

    public List<Card> getCards() {
        return cardDao.selectAll();
    }

    public void addCard(CardRegistrationRequest request, String email) {
        System.out.println("2. SERVICE HIT: Finding user " + email);
        Optional<User> user = Optional.ofNullable(userDao.selectUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
        System.out.println("3. USER FOUND: ID " );
        Card card = new Card(
                request.name(),
                request.set(),
                request.price(),
                request.imageId(),
                request.condition(),
                user

        );
        System.out.println("4. CALLING DAO...");
        cardDao.addCard(card);

    }

    public List<Card> getCardByName(String cardName) {
        return cardDao.selectAll().stream()
                .filter(cards -> cards.getName().toLowerCase().contains(cardName.toLowerCase()))
                .collect(Collectors.toList());
    }

    public boolean existsById(Integer cardId) {
        return cardDao.existsCardById(cardId);
    }

    public void deleteCard(Integer cardId) {
        cardDao.deleteCardById(cardId);
    }


    public void uploadCardImage(Integer cardId, MultipartFile file) {
    }
}
