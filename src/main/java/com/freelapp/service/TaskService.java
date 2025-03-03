package com.freelapp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
		Pageable pageable = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending().and(Sort.by("name")));
		return taskRepository.findAll(pageable);
		
		
			  
		
	}
	
	public Page<Task> findSearchedPage(int pageNumber, String input){
		Pageable pageable = PageRequest.of(pageNumber -1, 12);
		return taskRepository.search(input, pageable);
		
	}
	
	// Metodo che salva la data fine task in corrispondenza di STOP contatore
	// fa la conversioni da localdatetime dello stop a localdate della data di chiusura del task
	public void setStopTaskDate(LocalDateTime STOP, int taskId) {
		java.util.Date NEWSTOP = java.util.Date.from(STOP.atZone(ZoneId.systemDefault()).toInstant());
		LocalDate NEWSTOP2 = NEWSTOP.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		taskRepository.getReferenceById(taskId).setDataChiusuraDefinitiva(NEWSTOP2);
	}
	
}
