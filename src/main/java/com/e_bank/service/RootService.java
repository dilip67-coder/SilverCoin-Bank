package com.e_bank.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_bank.registrationEntity.OpenAccount;
import com.e_bank.repository.RootRepository;

@Service
public class RootService {

	@Autowired
	private RootRepository repository;
	
	public OpenAccount openAccount(OpenAccount oa)
	{
		OpenAccount freshUser = repository.save(oa);
		return freshUser;
	}
	
	public List<OpenAccount> getLoginData() {
		
		List<OpenAccount> loginData = repository.findAll();
		return loginData;
	}

	public List<OpenAccount> getAllData() {
	
		List<OpenAccount> findAll = repository.findAll();
		return findAll;
		
	}
	
	public Optional<OpenAccount> getDataById(long id) {
		
		return repository.findById(id);
	}
	
	public void deleteById(long id) {
		repository.deleteById(id);
	}
	
	public void updateCustomer(OpenAccount customer) {
		
		repository.save(customer);
	}
		public Optional<OpenAccount> getRecentCustomer() {
			
			Optional<OpenAccount> recentUser = repository.getRecentUser();
			
			return recentUser;
		}	
		
		public OpenAccount updateById(long id) {
			
			return repository.getById(id);
		}
		
}
