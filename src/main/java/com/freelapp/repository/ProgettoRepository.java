package com.freelapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.freelapp.model.Progetto;

import jakarta.transaction.Transactional;

public interface ProgettoRepository extends JpaRepository<Progetto, Integer>, PagingAndSortingRepository<Progetto, Integer>{

	 @Query("SELECT p FROM Progetto p "
	 		+ "LEFT JOIN Task t ON p.id=t.progetto.id "
	 		+ "WHERE p.name LIKE '%'||:input||'%' OR "
	    	+ "p.descrizione LIKE '%'||:input||'%' OR "
	    	+ "p.cliente.labelCliente LIKE '%'||:input||'%' OR "
	    	+ "p.cliente.ragioneSociale LIKE '%'||:input||'%' OR "
	    	+ "p.cliente.email LIKE '%'||:input||'%' OR "
	    	+ "p.cliente.telefono LIKE '%'||:input||'%' OR "
	    	+ "p.cliente.indirizzo LIKE '%'||:input||'%' OR "
	    	+ "p.cliente.city LIKE '%'||:input||'%' OR "
	    	+ "p.cliente.partitaIva LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.name LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.surname LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.codiceFiscale LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.sito LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.nameContatto LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.surnameContatto LIKE '%'||:input||'%' OR "
	    	+ "t.name LIKE '%'||:input||'%' OR "
	    	+ "t.descrizione LIKE '%'||:input||'%'")
	 
	    public Page<Progetto> search( String input, Pageable pageable);
	 
	 
	// @Query(
		//	  value = "SELECT * FROM Progetti p WHERE p.archivia = 1", 
			// value = "SELECT * FROM Progetti p WHERE p.archivia = 1 AND p.name LIKE '%'||:input||'%' ", 
		//	  nativeQuery = true)
	
//	 @Query(value = "SELECT *"
//			  + " from progetti p "
//			  + " where p.archivia=1 "
//			  + " and p.denominazione_progetto LIKE '%'||:input||'%' OR  "
//			  + "p.descrizione LIKE '%'||:input||'%' OR " 
//			  + "p.cliente.nickname LIKE '%'||:input||'%'",
//			  nativeQuery = true)
	 
	 @Query("SELECT p FROM Progetto p WHERE p.archivia=:isArchived AND "
	 		+ "(p.descrizione LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.labelCliente LIKE '%'||:input||'%' OR "
	 		+ "p.name LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.ragioneSociale LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.email LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.telefono LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.indirizzo LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.city LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.partitaIva LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.name LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.surname LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.codiceFiscale LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.sito LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.nameContatto LIKE '%'||:input||'%' OR "
	 		+ "p.cliente.surnameContatto LIKE '%'||:input||'%')")
	
	 	public Page<Progetto> searchArchiviati(Boolean isArchived, String input, Pageable pageable);
	    
	    public List<Progetto> findAll();
	    
	    // transitional usato per modifica e cancellazione
	    @Transactional
	    void deleteByClienteId(Integer id);


		public Page<Progetto> findByArchivia(boolean i, Pageable pageable2);
		
		
		@Query("SELECT p FROM Progetto p WHERE p.dataFine IS NULL")
		public List<Progetto> findByActiveProject();
		
		
		@Query("SELECT p FROM Progetto p WHERE p.dataFine IS NOT NULL")
		public List<Progetto> findByNotActiveProject();
		
		
		@Query("SELECT p FROM Progetto p WHERE p.dataFine IS NULL")
		public Page<Progetto> findByActiveProjectPageable(Pageable pageable);
		
		
		@Query("SELECT p FROM Progetto p WHERE p.dataFine IS NOT NULL")
		public Page<Progetto> findByNotActiveProjectPageable(Pageable pageable);
		
//		 @Query("SELECT p FROM Progetto p WHERE p.cliente.id=:input")
//		 public Page<Progetto> findByClienteId(int input,  Pageable pageable);
		
		
		public List<Progetto>findByArchivia(boolean value);
	    
}
