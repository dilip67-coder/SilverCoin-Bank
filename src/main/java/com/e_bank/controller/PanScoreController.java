package com.e_bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.e_bank.registrationEntity.PanDetails;
import com.e_bank.service.PanService;

@Controller
public class PanScoreController {

	@Autowired
	private PanService service;
	
	@RequestMapping("/cibilScore")
	public String cibilScoreHome() {
		
		return "panScore/cibilScore";
	}
	
	@RequestMapping("/registerPanHome")
	public String registerPage() {
		
		return "panScore/registerPan";
	}
	
	@GetMapping("/registerPan")
	public String regsterPanHome(@ModelAttribute PanDetails pan) {
		
		pan.setCibilScore(500);
		
		service.savePanData(pan);
		
		return "redirect:/cibilScore";
	}
	
	@RequestMapping("/panVerify")
	public String panLoginVerify( @RequestParam("panNumber") String panNum,
			@RequestParam("password") String password,
			Model model) {
		
		List<PanDetails> allData = service.getAllData();
		boolean flag = false;
		for(PanDetails data : allData) {
			  if(data.getPanNum().equals(panNum) && data.getPassword().equals(password)) {
				  model.addAttribute("cibilScore", data.getCibilScore());
				  flag = true;
			  return "panScore/cibilScore"; 
			  }
		}
		
		return "redirect:/registerPanHome";
	}
	
}
