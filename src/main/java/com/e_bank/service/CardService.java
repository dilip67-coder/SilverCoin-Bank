package com.e_bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_bank.registrationEntity.Card;
import com.e_bank.repository.CardRepository;

@Service
public class CardService {

	@Autowired
	private CardRepository cardRepository;
	
	public void saveCustomerCard(Card card) {
		
		cardRepository.save(card);
	}
	
	public Card getCardById(long id) {
		
		return cardRepository.getById(id);
	}
	
	public Card getCardByCustomerId(long id) {
		
		return cardRepository.findByCustomerId(id);
	}
}
