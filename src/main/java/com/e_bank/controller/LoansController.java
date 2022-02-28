package com.e_bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoansController {

	@RequestMapping("/homeLoan")
	public String homeLoan() {
		
		return "loans/homeLoan";
	}
	
	@RequestMapping("/personalLoan")
	public String personalLoan() {
		
		return "loans/personalLoan";
	}
	
	@RequestMapping("/educationLoan")
	public String educationLoan() {
		
		return "loans/educationLoan";
	}
}
