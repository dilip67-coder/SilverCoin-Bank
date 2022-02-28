package com.e_bank.registrationEntity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long cardId;
	
	private String fullName;
	private String cardType;
	private long customerId;
	
	private LocalDateTime applyDate;
	private LocalDateTime expiryDate;
	
	private String cvv;
	
	private String cardSerialNum;
	

}
