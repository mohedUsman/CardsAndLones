package com.example.cards.service;

import com.example.cards.constants.CardsConstants;
import com.example.cards.dto.CardsDto;
import com.example.cards.entity.Cards;
import com.example.cards.exception.CardsAlreadyExistsException;
import com.example.cards.exception.NoCardFoundException;
import com.example.cards.exception.ResourceNotFoundException;
import com.example.cards.mapper.CardMappers;
import com.example.cards.repository.CardsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements CardsService {

    private CardsRepository cardsRepository;

    @Override
    public void cratedCard(String mobileNumber) {

        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent()){
            throw new CardsAlreadyExistsException("Card already exists for mobile number: " + mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }



    private Cards createNewCard(String mobileNumber) {
        Cards cards = new Cards();
        cards.setMobileNumber(mobileNumber);
        // Generate a random card number (for example purposes, you might want to use a more secure method)
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        cards.setCardNumber(Long.toString(randomCardNumber));
        cards.setCardType(CardsConstants.CREDIT_CARD);
        cards.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        cards.setAmountUsed(0);
        cards.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        cards.setMobileNumber(mobileNumber);
        cards.setCreatedBy("System");
        cards.setCreatedAt(LocalDateTime.now());
        return cards;
    }

    @Override
    public CardsDto getCardByMobileNumber(String mobileNumber) {

       Cards cards= cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
       return CardMappers.mapToCardsDto(cards, new CardsDto());
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards =cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new NoCardFoundException("No card found with card number: " + cardsDto.getCardNumber())
        );
        CardMappers.mapToCards(cardsDto, cards);
        cardsRepository.save(cards);
        return true;



    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards =cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }
}

