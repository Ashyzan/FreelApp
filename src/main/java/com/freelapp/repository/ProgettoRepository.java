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

	 @Query("SELECT p FROM Progetto p WHERE p.name LIKE '%'||:input||'%' OR "
	    		+ "p.descrizione LIKE '%'||:input||'%' OR "
	    		+ "p.cliente.labelCliente LIKE '%'||:input||'%'")
	 
	    public Page<Progetto> search( String input, Pageable pageable);
	 
	 
	// @Query(
		//	  value = "SELECT * FROM Progetti p WHERE p.archivia = 1", 
			// value = "SELECT * FROM Progetti p WHERE p.archivia = 1 AND p.name LIKE '%'||:input||'%' ", 
		//	  nativeQuery = true)
	
	 @Query(value = "SELECT *"
			  + " from progetti p "
			  + " where p.archivia=true "
			  + " and p.denominazione_progetto LIKE '%'||:input||'%' OR  "
			  + "p.descrizione LIKE '%'||:input||'%' OR " 
			  + "p.cliente_rif LIKE '%'||:input||'%'",
			  nativeQuery = true)
	
	 	public Page<Progetto> searchArchiviati( String input, Pageable pageable);
	    
	    public List<Progetto> findAll();
	    
	    // transitional usato per modifica e cancellazione
	    @Transactional
	    void deleteByClienteId(Integer id);


		public Page<Progetto> findByArchivia(boolean i, Pageable pageable2);
	    
}
