package com.e_bank.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.e_bank.registrationEntity.OpenAccount;
import com.e_bank.service.RootService;

@RestController
public class ApiController {
	
	@Autowired
	private RootService service;
	
	@GetMapping("/getAllData")
	public  List<OpenAccount>  getData() {
		
		List<OpenAccount> allData = service.getAllData();
		return allData;
	}
	
	@PostMapping("/saveData")
	@ResponseBody
	public OpenAccount save(@RequestBody OpenAccount oa) {
		
		service.openAccount(oa); 
		
		return oa;
	}
	
	@PutMapping("/updateCustomer")
	public  OpenAccount update(@RequestBody OpenAccount oa) {
		
		service.updateCustomer(oa);
		
		return oa;
		
	}
	
	@GetMapping("/getUser/{id}")
	public Optional<OpenAccount> getUserByid(@PathVariable long id) {
		
		Optional<OpenAccount> dataById = service.getDataById(id);
		
		return dataById;
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Long id){
		service.deleteById(id);
		
		String body = "Customer Delete with id "+ id;
		
		return ResponseEntity.ok(body);
	}
	
}
