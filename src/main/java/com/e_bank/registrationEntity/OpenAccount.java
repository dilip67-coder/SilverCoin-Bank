package com.e_bank.registrationEntity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class OpenAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String accountType;
	private String accountNum;
	private String ifscCode;
	
	private String firstName;
	private String middleName;
	private String lastName;
	
	private String userName;
	private String password;
	
	private String adhaarNum;
	private String panNum;
	private String gender;
	
	private String dob;
	private int age;
	
	private String nationality;
	private String email;
	private String phno;
	
	private String address;
	private String state;
	private String city;
	private long pin;
	private String maritialStatus;
	private String occupation;
	private String country;
	private LocalDateTime openDate;
	
	

	@Lob
	@Column( columnDefinition = "MEDIUMBLOB")
	private String userPhoto;
	
	@Lob
	@Column( columnDefinition = "MEDIUMBLOB")
	private String userPanPhoto;
	
	@Lob
	@Column( columnDefinition = "MEDIUMBLOB")
	private String userQr;
	
	
}
