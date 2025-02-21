package com.freelapp.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelapp.model.Task;
import com.freelapp.repository.TaskRepository;
import com.freelapp.restModel.RestTask;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class FreelappRestController {

	@Autowired
	private TaskRepository taskRepository;
	
	@GetMapping("/task/{id}")
	public Optional<RestTask> get(@PathVariable("id") Integer id){
		
		Task task = taskRepository.getReferenceById(id);
		
		String nome = task.getName();
		
		String progetto = task.getProgetto().getName();
		
		String cliente = task.getProgetto().getCliente().getLabelCliente();
		
		String logoCliente = task.getProgetto().getCliente().getLogoPath();
		
		LocalDate chiusuraStimata = task.getDataChiusuraStimata();

		Long finalTime = 0l;
		
		Integer taskInUsoDaInviare = 0;
		
		if(ContatoreController.taskInUso != null) {
			taskInUsoDaInviare = ContatoreController.taskInUso.getId();
		} 
		
		if(task.getContatore() != null && task.getContatore().getFinaltime() != null) {
			
			finalTime = task.getContatore().getFinaltime();
			
		}
	
		Optional<RestTask> restTask = Optional.of(new RestTask(nome, progetto, cliente, logoCliente, chiusuraStimata, finalTime, taskInUsoDaInviare));
	
		return restTask;
	}
	
}
