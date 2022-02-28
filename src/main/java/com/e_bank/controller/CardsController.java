package com.e_bank.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.e_bank.registrationEntity.Card;
import com.e_bank.registrationEntity.OpenAccount;
import com.e_bank.service.CardService;
import com.e_bank.service.RootService;

@Controller
public class CardsController {
@Autowired
private RootService service;

@Autowired
private CardService cardService;
	
	@RequestMapping("/card")
	public String cardHome() {
		
		return "Cards/CardsLogin";
	}
	
	@RequestMapping("/cardLoginVerify")
	public String cardHomeLogin(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model) {
		List<OpenAccount> loginData = service.getLoginData();
		for(int i = 0; i < loginData.size(); i++) {
			
			if(loginData.get(i).getUserName().equals(username) && loginData.get(i).getPassword().equals(password)) {
				model.addAttribute("customerId", loginData.get(i).getId());
				
				Card cardById = cardService.getCardByCustomerId(loginData.get(i).getId());
				if(cardById != null) {
					model.addAttribute("cardData", cardById);
					return "Cards/haveCard";
				}
			}
		}
		return "Cards/debitCard";
	}
	
	@RequestMapping("/newCard")
	public String applyNewCard(@ModelAttribute Card card) {
		
		card.setApplyDate(LocalDateTime.now());
		card.setExpiryDate(card.getApplyDate().plusYears(6).plusMonths(6));
		card.setCvv(generateCvv());
		card.setCardSerialNum(generateCardNum());
		
		/* System.out.println(card.toString()); */
		cardService.saveCustomerCard(card);
		
		return "redirect:/card";
	}
	
	public String generateCardNum() {
		long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
		
		String cardNum = "786123" + number;
		
		return cardNum;
	}
	
	public static String generateCvv() {
	    // It will generate 6 digit random Number.
	    // from 0 to 999999
	    Random rnd = new Random();
	    int number = rnd.nextInt(999);

	    // this will convert any number sequence into 6 character.
	    return String.format("%03d", number);
	}
	
}
