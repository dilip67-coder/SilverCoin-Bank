package com.e_bank.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_bank.registrationEntity.OpenAccount;
import com.e_bank.repository.RootRepository;

@Service
public class ServiceTest {

	@Autowired
	private RootRepository repository;
	
	public boolean openAccount(OpenAccount oa)
	{
	repository.save(oa);
		return true;
	}
	
	public List<OpenAccount> getLoginData() {
		
		List<OpenAccount> loginData = repository.findAll();
		return loginData;
	}
}
