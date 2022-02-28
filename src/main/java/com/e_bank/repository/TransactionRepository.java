package com.e_bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.e_bank.registrationEntity.TransferFund;

@Repository
public interface TransactionRepository extends JpaRepository<TransferFund, Long>{

	@Query(value="SELECT * FROM Transfer_Fund where customer_account_num = ?",nativeQuery = true)
	List<TransferFund> findAllById(long id);

}
