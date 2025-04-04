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
import org.springframework.ui.Model;

import com.freelapp.controller.ContatoreController;
import com.freelapp.model.Task;
import com.freelapp.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public List<Task> findAllNotClosed(){
		//restituisce la lista dei task attivi(non chiusi)
		return taskRepository.findAllNotClosed();
	}
	
	public Page<Task> findPage(int pageNumber){
		Pageable pageable = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
		//restituisce la lista dei task attivi(non chiusi)
		return taskRepository.findAllNotClosed(pageable);
		
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
	
	// metodo che restituisce il finaltime formattato in HH:MM:SS
	public String Timer(Task task) {		
	    Long finaltime = task.getContatore().getFinaltime(); 
	    Long HH = finaltime / 3600;
	    Long MM= (finaltime % 3600) / 60;
	    Long SS = finaltime % 60;  
	    String timer = String.format("%02d:%02d:%02d", HH, MM, SS);	    
		return timer;
	}
	
	//passa al model l'id, il nome e il progetto del taskInUso se diverso da null
	//impegato per la modale di stop
	public void informationFromTaskInUsoToModel(Model model) {
		
		Integer taskInUsoId = 0;
		String taskInUsoName = null;
		String taskInUsoProgetto = null;
		
		if(ContatoreController.taskInUso != null) {
			taskInUsoId = ContatoreController.taskInUso.getId();
			taskInUsoName = ContatoreController.taskInUso.getName();
			taskInUsoProgetto = ContatoreController.taskInUso.getProgetto().getName();
		}
		
		model.addAttribute("taskInUsoId", taskInUsoId);
		model.addAttribute("taskInUsoName", taskInUsoName);
		model.addAttribute("taskInUsoProgetto", taskInUsoProgetto);
	}
	
}
