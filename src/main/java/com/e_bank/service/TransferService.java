package com.e_bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_bank.registrationEntity.OpenAccount;
import com.e_bank.registrationEntity.TransferFund;
import com.e_bank.repository.RootRepository;
import com.e_bank.repository.TransactionRepository;

@Service
public class TransferService {

	@Autowired
	private RootRepository repo;
	
	@Autowired
	private TransactionRepository tRepo;
	
	/*
	 * public OpenAccount updateByAccountNum(String accountNum) {
	 * 
	 * long id = 0; boolean flag=false; List<OpenAccount> findAll = repo.findAll();
	 * for(int i = 0; i < findAll.size(); i++) { if(
	 * findAll.get(i).getAccountNum().equals(accountNum)) { flag=true;
	 * id=findAll.get(i).getId(); } }
	 * 
	 * if(flag) { return repo.getById(id); } return null; }
	 */
	
	public OpenAccount updateAccountById(long id) {
		
		return repo.getById(id);
	}
	
	public List<TransferFund> getAllTransactions() {
		
		return tRepo.findAll();
	}
	
	public List<TransferFund> getAllTransactionsById(long id) {
		
		return tRepo.findAllById(id);
	}
}
