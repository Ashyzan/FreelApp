package com.freelapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.freelapp.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	

    @Query("SELECT c FROM Cliente c WHERE c.name LIKE '%'||:input||'%' OR "
    		+ "c.ragioneSociale LIKE '%'||:input||'%' OR "
    		+ "c.email LIKE '%'||:input||'%' OR "
    		+ "c.telefono LIKE '%'||:input||'%' OR "
    		+ "c.indirizzo LIKE '%'||:input||'%' OR "
    		+ "c.city LIKE '%'||:input||'%' OR "
    		+ "c.partitaIva LIKE '%'||:input||'%' ")
    public List<Cliente> search( String input);
}
