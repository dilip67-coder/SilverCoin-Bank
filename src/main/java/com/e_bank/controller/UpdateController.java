package com.e_bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.e_bank.registrationEntity.OpenAccount;
import com.e_bank.service.RootService;


@Controller
public class UpdateController {
	@Autowired
	private RootService service;
	
	@GetMapping("/update")
	public String updateHome() {
		
		return "Updates/updateLogin";
	}
	
	
	@RequestMapping("/updForm")
	public String updateData(@RequestParam("email") String email,@RequestParam("phone") String phone,
			@RequestParam("address") String address,@RequestParam("id") String id){
		long sid=Long.parseLong(id);
		OpenAccount oa=service.updateById(sid);
		System.out.println(oa.getEmail());
		if(email.equals("")){
		oa.setEmail(oa.getEmail());
		}else{
		oa.setEmail(email);
		}
		if(phone.equals("")){
		oa.setPhno(oa.getPhno());
		}else{
		oa.setPhno(phone);
		}
		if(address.equals("")){
		oa.setAddress(oa.getAddress());
		}else{
		oa.setAddress(address);
		}
		
		
		System.out.println(oa.getEmail());
		service.openAccount(oa);
		return "index";
	}
	
	
	@RequestMapping("/UpdateLoginVerfy")
	public String updateVerify(@RequestParam("username") String username,
			@RequestParam("password") String password,
			Model model) {
		
		List<OpenAccount> loginData = service.getLoginData();
		
		for(int i=0; i<loginData.size(); i++) {
			if(loginData.get(i).getUserName().equals(username) && loginData.get(i).getPassword().equals(password)) {
				
				 java.util.Optional<OpenAccount> dataById = service.getDataById(loginData.get(i).getId());
				
				model.addAttribute("user", dataById.get());
				
				return "Updates/update";
			}
		}
		
		return "errorPages/loginRejected";
	}
}
