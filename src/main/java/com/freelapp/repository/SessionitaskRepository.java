package com.freelapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.freelapp.model.SessioniTask;


public interface SessionitaskRepository extends JpaRepository<SessioniTask, Integer> {
	
	List<SessioniTask> findByContatoreId(Integer contatoreId);
	void deleteByContatoreId(Integer contatoreId);

}
