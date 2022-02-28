package com.e_bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_bank.registrationEntity.PanDetails;

public interface PanRegister extends JpaRepository<PanDetails, Long> {

}
