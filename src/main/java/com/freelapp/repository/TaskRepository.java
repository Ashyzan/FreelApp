package com.freelapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.freelapp.model.Task;

import jakarta.transaction.Transactional;

public interface TaskRepository extends JpaRepository<Task, Integer>, PagingAndSortingRepository<Task, Integer> {

	@Query("SELECT t FROM Task t WHERE t.name LIKE '%'||:input||'%' OR "
    		+ "t.descrizione LIKE '%'||:input||'%' OR "
			+ "t.progetto.name LIKE '%'||:input||'%' OR "
    		+ "t.progetto.descrizione LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.labelCliente LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.ragioneSociale LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.email LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.telefono LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.indirizzo LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.city LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.partitaIva LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.name LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.surname LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.codiceFiscale LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.sito LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.nameContatto LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.surnameContatto LIKE '%'||:input||'%'")
    public Page<Task> search( String input, Pageable pageable);
	
	public List<Task> findByProgettoId(Integer id);
    
    public List<Task> findAll();
    
    @Query("SELECT t FROM Task t WHERE t.name LIKE '%'||:input||'%' OR "
    		+ "t.descrizione LIKE '%'||:input||'%' OR "
    		+ "t.progetto.name LIKE '%'||:input||'%' OR "
    		+ "t.progetto.descrizione LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.labelCliente LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.ragioneSociale LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.email LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.telefono LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.indirizzo LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.city LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.partitaIva LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.name LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.surname LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.codiceFiscale LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.sito LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.nameContatto LIKE '%'||:input||'%' OR "
    		+ "t.progetto.cliente.surnameContatto LIKE '%'||:input||'%'")
    public List<Task> searchOreLavorate(String input);
    
    @Query("SELECT t FROM Task t WHERE t.stato !='chiuso' ORDER BY t.dataModifica DESC")
    public Page<Task> findAllNotClosed(Pageable pageable);
	
     @Query("SELECT t FROM Task t WHERE t.stato !='chiuso' ORDER BY t.dataModifica DESC")
    public List<Task> findAllNotClosed();
    @Transactional
    void deleteByProgettoId(Integer id);
    
    public Optional<Task> findById(Integer id);
    
  
    
}
