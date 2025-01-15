package com.freelapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freelapp.model.Contatore;

public interface ContatoreRepository extends JpaRepository<Contatore, Integer> {
	

}

