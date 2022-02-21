package com.e_bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.e_bank.registrationEntity.OpenAccount;

public interface RootRepository extends JpaRepository<OpenAccount, Long> {
	
	@Query(value = "Select * From bankmanagement.open_account o Order By o.id DESC limit 1",nativeQuery=true)
	public Optional<OpenAccount> getRecentUser();
	
}
