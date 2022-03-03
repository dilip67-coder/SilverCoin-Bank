package com.e_bank.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.e_bank.registrationEntity.OpenAccount;
import com.e_bank.registrationEntity.TransferFund;
import com.e_bank.service.RootService;

@Controller
public class DepositController {

	@Autowired
	private RootService service;
	
	@RequestMapping("/depositLogin")
	public String depositLogin() {
		
		return "Deposit/depositLogin";
	}
	
	@GetMapping("/depositLoginVerify")
	public String depositLoginVerify(@RequestParam("username") String username,
			@RequestParam("password") String password, Model model) {
		
		List<OpenAccount> loginData = service.getLoginData();
		for(int i = 0; i < loginData.size(); i++) {
			
			if(loginData.get(i).getUserName().equals(username) && loginData.get(i).getPassword().equals(password)) {
				
				model.addAttribute("username", loginData.get(i).getFirstName());
				model.addAttribute("customerId", loginData.get(i).getId());
				
				
				return "Deposit/deposit";
			}
		}	
		return "errorPages/loginRejected";
	}
	
	@GetMapping("/depositFund")
	public String depositFund(@RequestParam("depositAmount") long depositAmount,
			@RequestParam("customerId") long cId) {
		
		TransferFund tf = new TransferFund(); 
		
		tf.setTransactionDate(LocalDateTime.now());
		tf.setTranscationID(generateTransactionId());
		tf.setDepositAmount(depositAmount);
		tf.setStatus("deposit success");
		tf.setDeposit("yes");
		tf.setCustomerAccountNum(cId);
		
		List<TransferFund> list = new ArrayList<TransferFund>();
		
		list.add(tf);
		
		OpenAccount updateById = service.updateById(cId);
		
		updateById.setAccountBalance(updateById.getAccountBalance() + depositAmount);
		
		updateById.setTrans(list);
		
		updateById.setCibilScore(updateById.getCibilScore() + (depositAmount / 100) *.36);
		
		service.openAccount(updateById);
		
		return "index";
	}
	
	//Generate generateTransactionId
	public String generateTransactionId() {
		
		Random r = new Random();
		int num = r.nextInt(9000000) + 1000000;
		String accNum = "DepositTID"+num;
		
		return accNum;
	}
}
