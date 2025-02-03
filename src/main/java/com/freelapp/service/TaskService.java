package com.freelapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.freelapp.model.Task;
import com.freelapp.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public List<Task> findAll(){
		return taskRepository.findAll(Sort.by(Sort.Direction.DESC, "dataInizio"));
	}
	
	public Page<Task> findPage(int pageNumber){
		Pageable pageable = PageRequest.of(pageNumber -1, 12);
		return taskRepository.findAll(pageable);
		
	}
	
	public Page<Task> findSearchedPage(int pageNumber, String input){
		Pageable pageable = PageRequest.of(pageNumber -1, 12);
		return taskRepository.search(input, pageable);
		
	}

	
}
