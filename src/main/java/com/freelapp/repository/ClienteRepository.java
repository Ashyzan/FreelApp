package com.freelapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.freelapp.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
//    List<Cliente> findByPartitaIva(String partitaIva);
    
    
//    query custom per la ricerca per partita IVA
    @Query("SELECT c FROM Cliente c WHERE c.partitaIva LIKE '%'||:input||'%' ")
	
    public List<Cliente> search( String input);
}
