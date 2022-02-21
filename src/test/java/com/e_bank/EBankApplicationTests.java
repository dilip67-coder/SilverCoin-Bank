package com.e_bank;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.e_bank.registrationEntity.OpenAccount;
import com.e_bank.repository.RootRepository;
import com.e_bank.service.ServiceTest;

@SpringBootTest
class EBankApplicationTests {
         Logger logger = LoggerFactory.getLogger(EBankApplication.class);
	@Autowired
	private ServiceTest serviceTest;
	
	@Autowired
	RootRepository  rootRepository;
	
	@Test
	  public void  getRecentAccount()
	  {
		logger.info("getRecentAccount() called");
		OpenAccount openAccount = rootRepository.getRecentUser().get();
		  System.out.println(openAccount);
		  logger.info("getRecentAccount() Ended");
	  }
	
	//login test
	@Test
	public void loginTest() {
		
		List<OpenAccount> loginData = serviceTest.getLoginData();
		
		String username = "userName";
		String password = "password";
		boolean status = false;
		
		for(int i=0; i<loginData.size(); i++) {
			if(loginData.get(i).getUserName().equals(username) && loginData.get(i).getPassword().equals(password)) {
				status = true;
		}
	}
		assertThat(status).isTrue();
}
}

