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
	    		+ "p.descrizione LIKE '%'||:input||'%' ")
//	    		+ "p.DataInizio LIKE '%'||:intInput||'%' OR "
//	    		+ "p.DataFine LIKE '%'||:intInput||'%' OR ")
	 
	    public Page<Progetto> search( String input, Pageable pageable);
	    
	    public List<Progetto> findAll();
	    
	    // transitional usato per modifica e cancellazione
	    @Transactional
	    void deleteByClienteId(Integer id);
	    
	  
	    
}
