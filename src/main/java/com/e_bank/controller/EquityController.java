package com.e_bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.e_bank.registrationEntity.EquityList;
import com.e_bank.service.EquityService;

@Controller
public class EquityController {

	@Autowired
	private EquityService service;
	
	
	@GetMapping("/equity/{page}")
	public String equityHome(@PathVariable("page") Integer page,Model model) {
		
		PageRequest pageRequest =PageRequest.of(page,10);
		
		Page<EquityList> allEquityData = service.getAllEquityData(pageRequest);
		
		System.out.println(allEquityData);
		model.addAttribute("equity", allEquityData);
		model.addAttribute("totalPages", allEquityData.getTotalPages());
		model.addAttribute("currentPage", page);
		return "equity";
	}
}
