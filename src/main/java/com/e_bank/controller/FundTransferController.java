package com.e_bank.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e_bank.registrationEntity.OpenAccount;
import com.e_bank.registrationEntity.TransferFund;
import com.e_bank.service.RootService;
import com.e_bank.service.TransferService;

@Controller
public class FundTransferController {

	@Autowired
	private RootService service;
	@Autowired
	private TransferService tService;
	
	@RequestMapping("/fundTransfer")
	public String fundTransferHome(Model model) {
		
		return "fundTransfer/fundTransLogin";
	}
	
	@GetMapping("/fundLogin")
	public String loginVerify(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model) {
		
		List<OpenAccount> loginData = service.getLoginData();
		for(int i = 0; i < loginData.size(); i++) {
			
			if(loginData.get(i).getUserName().equals(username) && loginData.get(i).getPassword().equals(password)) {
				model.addAttribute("accountNum", loginData.get(i).getId());
				model.addAttribute("password", loginData.get(i).getPassword());
				System.out.println("Account Number" + loginData.get(i).getAccountNum());
				
				return "fundTransfer/fundTrans";
			}
		}
		return "errorPages/loginRejected";
	}
	
	@RequestMapping("/transferfund")
	public String transferFund( @ModelAttribute TransferFund transferFund,
			@RequestParam("password") String password) {
		
		
		List<TransferFund> listTf = new ArrayList<TransferFund>();		
		System.out.println("transferFund" + transferFund.toString());
		
		
		transferFund.setTransactionDate(LocalDateTime.now());
		transferFund.setTranscationID(generateTransactionId());
		OpenAccount updateAccountById = service.updateById(transferFund.getCustomerAccountNum());
		
		//check for password is correct or not
		if(password.equals(updateAccountById.getPassword())) {
			
			if(updateAccountById.getAccountBalance() > transferFund.getTotalAmount()) {
				
				transferFund.setStatus("success payment");
				listTf.add(transferFund);
				updateAccountById.setTrans(listTf);
				//deduct amount on transaction (DEbit)
				updateAccountById.setAccountBalance(updateAccountById.getAccountBalance() -transferFund.getTotalAmount());	
				//increase the cibil Score on every successful transaction .36 points
				updateAccountById.setCibilScore(updateAccountById.getCibilScore() + (transferFund.getAmount()/100) * .36);
				service.openAccount(updateAccountById);
			}else {
				transferFund.setStatus("payment rejected due to insufficient funds");
				listTf.add(transferFund);
				updateAccountById.setTrans(listTf);
				//deduct amount on transaction (DEbit) if amount is greater than totalDeduct amount it will not deduct from account.
				//updateAccountById.setAccountBalance(updateAccountById.getAccountBalance()-transferFund.getTotalAmount());
				
				//decrease cibil score if transaction is failed
				updateAccountById.setCibilScore(updateAccountById.getCibilScore() - (transferFund.getAmount()/100) * .72);
				service.openAccount(updateAccountById);
			}
		}else {
			return "fundTransfer/fundTransLogin";
		}
		
		return "index";
	}
	
	@RequestMapping("/eStatement")
	public String eStatement() {
		
		return "e-StatementLogin";
	}
	
	@RequestMapping("/statementLogin")
	public String statementLoginVerify(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model) {
		
		
		List<OpenAccount> loginData = service.getLoginData();
		for(int i = 0; i < loginData.size(); i++) {
			
			if(loginData.get(i).getUserName().equals(username) && loginData.get(i).getPassword().equals(password)) {
				
				List<TransferFund> allTransactionsById = tService.getAllTransactionsById(loginData.get(i).getId());
				
				System.out.println("all Transactions: " + loginData.get(i).getId());
				System.out.println("all Transactions: " + allTransactionsById);
				
				Optional<OpenAccount> data = service.getDataById(loginData.get(i).getId());
				long accountBalance = data.get().getAccountBalance();
				double cibilScore =  data.get().getCibilScore();
				
				model.addAttribute("accountBalance", accountBalance);
				model.addAttribute("cibilScore", cibilScore);
				
				model.addAttribute("transactions", allTransactionsById);
				
				return "eStatement";
			}
		}
		return "errorPages/loginRejected";
	}
	
	
	//Generate generateTransactionId
	public String generateTransactionId() {
		
		Random r = new Random();
		int num = r.nextInt(9000000) + 1000000;
		String accNum = "TID"+num;
		
		return accNum;
	}
}
