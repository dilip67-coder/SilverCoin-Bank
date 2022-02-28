package com.e_bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e_bank.registrationEntity.ForgotPassword;
import com.e_bank.registrationEntity.OpenAccount;
import com.e_bank.service.RootService;

@Controller
public class ForgotPasswordController {

	@Autowired
	private RootService service;
	
	@RequestMapping("/forgotPassword")
	public String forgotPassHome() {
		
		return "ForgotPassword/forgetPassword";
	}
	
	@RequestMapping("/resetPassword")
	public String resetPassword(@ModelAttribute ForgotPassword fp) {
		boolean flag=false;
		System.out.println(fp.toString());
		
		List<OpenAccount> allData = service.getAllData();
		
		/*
		 * if(allData.stream().anyMatch(a -> a.getUserName().equals(fp.getUsername()) &&
		 * a.getEmail().equals(fp.getEmail()))){ }
		 */
		
		for(int i=0;i<allData.size();i++) {
			if (allData.get(i).getUserName().equals(fp.getUsername()) && allData.get(i).getEmail().equals(fp.getEmail())) {
				
				flag=true;
				OpenAccount updateById = service.updateById(allData.get(i).getId());
				updateById.setPassword(fp.getPassword());
				service.openAccount(updateById);
				
			}
		}
		
		System.out.println(flag);
		
		return "redirect:/login";
	}
}
