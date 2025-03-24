package com.freelapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freelapp.model.Task;
import com.freelapp.repository.TaskRepository;
import com.freelapp.restModel.RestTask;
import com.freelapp.service.ContatoreService;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class FreelappRestController {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ContatoreService contatoreService;
	
	@GetMapping("/task/{id}")
	public Optional<RestTask> get(@PathVariable("id") Integer id){
		
		Task task = taskRepository.getReferenceById(id);
		
		String nome = task.getName();
		
		String progetto = task.getProgetto().getName();
		
		Integer progettoId = task.getProgetto().getId();
		
		String cliente = task.getProgetto().getCliente().getLabelCliente();
		
		Integer clienteId = task.getProgetto().getCliente().getId();
		
		String logoCliente = task.getProgetto().getCliente().getLogoPath();
		
		Long finalTime = 0l;
		
		Integer taskInUsoDaInviare = 0;
		
		Integer idTask = task.getId();
		
		String statoTask = task.getStato();
		
		if(ContatoreController.taskInUso != null) {
			taskInUsoDaInviare = ContatoreController.taskInUso.getId();
		} 
		
		if(task.getContatore() != null && task.getContatore().getFinaltime() != null) {
			
			finalTime = task.getContatore().getFinaltime();
			
		}
		
		//LocalDateTime stop = task.getContatore().getStop();

	
		Optional<RestTask> restTask = Optional.of(new RestTask(nome, progetto, progettoId, 
				cliente, clienteId, logoCliente, finalTime, taskInUsoDaInviare, 
				idTask, statoTask));
	
		return restTask;
	}
	
	@GetMapping("/searchMode/taskList")
	public List<RestTask> searchListOreLavorate(@RequestParam String input) {
		
		List<RestTask> taskListOreLavorate = new ArrayList<RestTask> ();
		
		if(input == "") {
			List<RestTask> taskListOreLAvorateEmpty = new ArrayList<RestTask> ();
			return taskListOreLAvorateEmpty;
		}
		
		
		// lista che restituisce i task per modale delle ore lavorate sulla dashboard
		List<Task> taskList = new ArrayList<Task> ();
		taskList = taskRepository.searchOreLavorate(input);
		taskList.forEach( task -> {
			
			RestTask taskTemporaneo = new RestTask(null, null, null, null, null, null, null, null, null, null);
			
			taskTemporaneo.setNome(task.getName());
			taskTemporaneo.setProgetto(task.getProgetto().getName());
			taskTemporaneo.setProgettoId(task.getProgetto().getId());
			taskTemporaneo.setCliente(task.getProgetto().getCliente().getLabelCliente());
			taskTemporaneo.setClienteId(task.getProgetto().getCliente().getId());
			taskTemporaneo.setLogoCliente(task.getProgetto().getCliente().getLogoPath());
			taskTemporaneo.setFinalTime(0l);
			taskTemporaneo.setTaskAttualmenteInUso(0);
			taskTemporaneo.setId(task.getId());
			taskTemporaneo.setStato(task.getStato());
		
				if( task.getContatore() == null) {
				taskListOreLavorate.add(taskTemporaneo);
				
						}
				
				else if( task.getContatore() != null) {
					Boolean contatoreAttivo;
					contatoreAttivo = contatoreService.contatoreIsRun(task);
		
					if(task.getContatore().getStop() == null  && contatoreAttivo == false) {
					
							 taskListOreLavorate.add(taskTemporaneo); 
									
							 }	
						}
		
				} );
		
		return taskListOreLavorate;
	}
	
//	@GetMapping(value = "/task/timeExceed/{id}", produces = MediaType.TEXT_HTML_VALUE)
//	public String timeExceedError(@PathVariable("id")Integer id, Model model) {
//		
//		Task taskInUso = taskRepository.getReferenceById(id);
//		
//		contatoreService.timeExeed(taskInUso, model);
//		
//		return "Errori/timeExceeded";
//	}
}
