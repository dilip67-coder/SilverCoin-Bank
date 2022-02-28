package com.e_bank.registrationEntity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class TransferFund {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	private String transcationID;
	private LocalDateTime transactionDate;
	
	private String upiId;
	
	private String cardNum;
	private String cardHolderName;
	
	private String accNum;
	private String ifscCode;
	private String accountHolderName;
	
	private long amount;
	
	private String charges;
	private long totalAmount;
	
	private String status;
	
	private long customerAccountNum;
}
