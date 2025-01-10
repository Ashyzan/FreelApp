package com.freelapp.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.freelapp.model.Contatore;

import jakarta.transaction.Transactional;

public interface ContatoreRepository extends JpaRepository<Contatore, Integer> {
	
	@Transactional
	@Modifying
	@Query("UPDATE Contatore c SET c.start = :start where c.id = :id")
	public void updateStart(LocalDateTime start, int id);

	@Transactional
	@Modifying
	@Query("UPDATE Contatore c SET c.pause = :pause where c.id = :id")
	public void updatePause(LocalDateTime pause, int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE Contatore c SET c.stop = :stop where c.id = :id")
	public void updateStop(LocalDateTime stop, int id);

}

