package com.rolez.backend.cards;

import com.rolez.backend.exception.ResourceNotFoundException;
import com.rolez.backend.s3.S3Buckets;
import com.rolez.backend.s3.S3Service;
import com.rolez.backend.users.User;
import com.rolez.backend.users.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.utils.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CardsService
{
    private final UserDao userDao;
    private final CardDao cardDao;
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;
    @Autowired
    public CardsService(@Qualifier("jdbc")UserDao userDao, @Qualifier("cardJdbc")CardDao cardDao, S3Service s3Service, S3Buckets s3Buckets) {
        this.userDao = userDao;
        this.cardDao = cardDao;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
    }

    public List<Card> getCards() {
        return cardDao.selectAll();
    }

    public void addCard(CardRegistrationRequest request, MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();
        String imageId = UUID.randomUUID().toString();

        try{
            s3Service.putObject(
                    s3Buckets.getUser(),
                    "card-images/%s".formatted(imageId),
                    file.getBytes()
            );
        }catch (IOException e){
            throw new RuntimeException("Failed to load image",e);
        }
        Card card = new Card(
                request.name(),
                request.set(),
                request.price(),
                imageId,
                request.condition(),
                user

        );
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

    public byte[] getCardImage(Integer cardId) {
        var card = cardDao.selectCardById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        if(StringUtils.isBlank(card.getImageId())){
            throw new  ResourceNotFoundException("Card  image not found");
        }

        return s3Service.getObject(
                s3Buckets.getUser(),
                "card-images/%s".formatted(card.getImageId())
        );
    }
}
