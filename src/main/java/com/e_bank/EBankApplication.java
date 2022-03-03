package com.e_bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class EBankApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(EBankApplication.class, args);
	}
	
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(EBankApplication.class);
    }

}
