package com.e_bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.e_bank.registrationEntity.EquityList;
import com.e_bank.repository.EquityRepository;

@Service
public class EquityServiceTest {

	@Autowired
	private EquityRepository equity;
	
	public Page<EquityList> getAllEquityData(PageRequest pageRequest) {
	Page<EquityList> findAll = equity.findAll(pageRequest);
		return findAll;
	}
	
	public List<EquityList> getAllEquity() {
		
		return equity.findAll();
	}
}
