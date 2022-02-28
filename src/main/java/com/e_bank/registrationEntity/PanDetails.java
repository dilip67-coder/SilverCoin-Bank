package com.e_bank.registrationEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class PanDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long panId;
	
		private String panNum;
	
		private String fullName;
	
		private String gender;
	
		private String address;
		private String state;
		private String city;
		private String country;
		private String pin;
	
		private String adhaarNum;
	
		private String dob;
		private int age;
	
		private String email;
		private String phno;
	
	private String password;

	private int cibilScore;
}
