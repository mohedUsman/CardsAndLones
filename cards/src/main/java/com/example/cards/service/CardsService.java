package com.example.cards.service;

import com.example.cards.dto.CardsDto;

public interface CardsService {

     void cratedCard(String mobileNumber);

     CardsDto getCardByMobileNumber(String mobileNumber);

     boolean updateCard(CardsDto cardsDto);

     boolean deleteCard(String mobileNumber);
}
