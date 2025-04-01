package com.freelapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.freelapp.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>, PagingAndSortingRepository<Cliente, Integer>{
	

    @Query("SELECT c FROM Cliente c "
    		+ "LEFT JOIN Progetto p ON c.id=p.cliente.id "
    		+ "LEFT JOIN Task t ON p.id=t.progetto.id "
    		+ "WHERE c.name LIKE '%'||:input||'%' OR "
    		+ "c.labelCliente LIKE '%'||:input||'%' OR "
    		+ "c.ragioneSociale LIKE '%'||:input||'%' OR "
    		+ "c.email LIKE '%'||:input||'%' OR "
    		+ "c.telefono LIKE '%'||:input||'%' OR "
    		+ "c.indirizzo LIKE '%'||:input||'%' OR "
    		+ "c.city LIKE '%'||:input||'%' OR "
    		+ "c.partitaIva LIKE '%'||:input||'%' OR "
    		+ "c.name LIKE '%'||:input||'%' OR "
    		+ "c.surname LIKE '%'||:input||'%' OR "
    		+ "c.codiceFiscale LIKE '%'||:input||'%' OR "
    		+ "c.sito LIKE '%'||:input||'%' OR "
    		+ "c.nameContatto LIKE '%'||:input||'%' OR "
    		+ "c.surnameContatto LIKE '%'||:input||'%' OR "
    		+ "p.name LIKE '%'||:input||'%' OR "
    		+ "p.descrizione LIKE '%'||:input||'%' OR "
    		+ "t.name LIKE '%'||:input||'%' OR "
    		+ "t.descrizione LIKE '%'||:input||'%'")
    public Page<Cliente> search( String input, Pageable pageable);
    
    public List<Cliente> findAll();
    
    
   
}
