package com.freelapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freelapp.model.Contatore;

public interface ContatoreRepository extends JpaRepository<Contatore, Integer> {
	
//	@Transactional
//	@Modifying
//	@Query("UPDATE Contatore c SET c.start = :start where c.id = :id")
//	public void updateStart(LocalDateTime start);
//	
//	@Transactional
//	@Modifying
//	@Query("UPDATE Contatore c SET c.stop = :stop where c.id = :id")
//	public void updateStop(LocalDateTime stop);

//	@Transactional
//	@Modifying
//	@Query("UPDATE Contatore c SET c.pause = :pause where c.id = :id")
//	public void updatePause(LocalDateTime pause, int id);
//	
//	@Transactional
//	@Modifying
//	@Query("UPDATE Contatore c SET c.stop = :stop where c.id = :id")
//	public void updateStop(LocalDateTime stop, int id);

//	public void updateStart(Contatore contatore);

}

