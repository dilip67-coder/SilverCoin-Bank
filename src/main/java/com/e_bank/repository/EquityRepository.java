package com.e_bank.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.e_bank.registrationEntity.EquityList;

public interface EquityRepository extends JpaRepository<EquityList, Long> {
	/*
	 * @Query("from EquityList as c" ) public Page<EquityList>
	 * findAllByPage(PageRequest pageRequest);
	 */}
