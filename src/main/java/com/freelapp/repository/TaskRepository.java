package com.freelapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.freelapp.model.Task;

import jakarta.transaction.Transactional;

public interface TaskRepository extends JpaRepository<Task, Integer>, PagingAndSortingRepository<Task, Integer> {

	@Query("SELECT t FROM Task t WHERE t.name LIKE '%'||:input||'%' OR "
    		+ "t.descrizione LIKE '%'||:input||'%' ")
    public Page<Task> search( String input, Pageable pageable);
    
    public List<Task> findAll();
	
    @Transactional
    void deleteByProgettoId(Integer id);
    
}
