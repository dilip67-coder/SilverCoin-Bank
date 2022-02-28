package com.e_bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.e_bank.registrationEntity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

	@Query(value = "SELECT * FROM CARD WHERE customer_id = ?",nativeQuery = true)
	public Card findByCustomerId(long id);
}
