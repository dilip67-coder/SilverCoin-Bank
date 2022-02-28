package com.e_bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_bank.registrationEntity.PanDetails;
import com.e_bank.repository.PanRegister;

@Service
public class PanService {

	@Autowired
	private PanRegister panRepo;
	
	public void savePanData(PanDetails pan) {
		
		panRepo.save(pan);
	}
	
	public List<PanDetails> getAllData() {
		
		return panRepo.findAll();
	}
}
