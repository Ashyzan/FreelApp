package com.freelapp.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.freelapp.model.Contatore;
import com.freelapp.model.SessioniTask;
import com.freelapp.model.Task;
import com.freelapp.repository.ContatoreRepository;
import com.freelapp.repository.SessionitaskRepository;
import com.freelapp.repository.TaskRepository;
import com.freelapp.service.ContatoreService;
import com.freelapp.service.OreLavorateService;
import com.freelapp.service.TaskService;

@Controller
public class OreLavorateController {
    
    @Autowired
    private TaskRepository repositTask;
    
    @Autowired
    private TaskService taskservice;
    
    @Autowired
    private ContatoreRepository repositContatore;
    
    @Autowired
    private OreLavorateService orelavorateservice;
    
    @Autowired
    private ContatoreService contatoreservice;
    
    @Autowired
    private SessionitaskRepository SessioniTaskRepository;

    
    @PostMapping("/orelavorate/{id}")
    public String gestioneTimer(@PathVariable("id") Integer taskId, @ModelAttribute("contatore") Contatore contatore,
    		@ModelAttribute("date") String data, @ModelAttribute("time")String time, Model model, 
    		BindingResult bindingresult, @ModelAttribute("aggiungiOre") Boolean aggiungiOre) {
    	// richiamo l'id del task
		
		Task task = repositTask.getReferenceById(taskId);
		
		// formatto la data da stringa (come mi arriva da thymeleaf) a localdate
		LocalDate date = LocalDate.parse(data);
		// formatto il time in localtime
	    LocalTime Time = LocalTime.parse(time); 
	    // eseguo il metodo per calcolare il finalTime in secondi
	    Long finalTime =  orelavorateservice.FinalOre(Time);
	    
	    // definisco lo start del contatore da localdate a localdatetime 
	  LocalDateTime START = date.atStartOfDay();
	    
	    // calcolo lo stop a partire dallo start e dal finaltime (in secondi) richiamando il metodo perposto dal service
	    LocalDateTime STOP = orelavorateservice.findStop(START, finalTime);
	   
	    SessioniTask SessioniTask = new SessioniTask();
	    
	    if(task.getContatore() == null) {
	    	if(!aggiungiOre) { // sovrascrivi e chiudi
	    	contatore = new Contatore();
	    	task.setContatore(contatore);
	    	task.getContatore().setFinaltime(finalTime); 
	    	task.getContatore().setStart(START);
	    	task.getContatore().setStop(STOP);
	    	task.setStato("chiuso");
	    	
	    	// sessione cronologia task
			SessioniTask.setContatore(contatore);
			SessioniTask.setAzione("ore lavorate");
			SessioniTask.setTime(task.getDataModifica());
			SessioniTask.setWorktime(contatoreservice.calcoloFinalTimeString(task));
			SessioniTask.setVariazione(contatoreservice.findTimeToString(START, STOP));
			SessioniTaskRepository.save(SessioniTask);
	    	
	    	// salvo in automatico la data fine task in corrispondenza dello stop contatore
	    	taskservice.setStopTaskDate(STOP, taskId);
			repositTask.save(task);
	    	}
	    	// aggiungi e lascia aperto
	    	else {
	    		contatore = new Contatore();
		    		task.setContatore(contatore);
	    		task.getContatore().setFinaltime(finalTime); 
	    		task.getContatore().setStart(START);
	    		task.getContatore().setPause(STOP);
	    		task.setStato("in pausa");
	    		
		    	// sessione cronologia task
				SessioniTask.setContatore(contatore);
				SessioniTask.setAzione("ore lavorate");
				SessioniTask.setTime(task.getDataModifica());
				SessioniTask.setWorktime(contatoreservice.calcoloFinalTimeString(task));
				SessioniTask.setVariazione(contatoreservice.findTimeToString(START, task.getContatore().getPause()));
				SessioniTaskRepository.save(SessioniTask);
	    		
	    	}
	    }
	    
	    else if(task.getContatore() != null ) {
	    	
	    	if(task.getContatore().getStop() != null) {
	    			// non fare nulla
	    	}
	    else 	if(aggiungiOre) { // aggiungi e lascia aperto
				    		task.getContatore().setRestart(START);
				    		task.getContatore().setPause(STOP);
				    		Long finalTimeAdd	= task.getContatore().getFinaltime() + finalTime;
				    		task.getContatore().setFinaltime(finalTimeAdd);
				    		task.setStato("in pausa");
				    		repositTask.save(task); 
				    		
					    	// sessione cronologia task
							SessioniTask.setContatore(contatore);
							SessioniTask.setAzione("ore lavorate");
							SessioniTask.setTime(task.getDataModifica());
							SessioniTask.setWorktime(contatoreservice.calcoloFinalTimeString(task));
							SessioniTask.setVariazione(contatoreservice.findTimeToString(START, task.getContatore().getPause()));
							SessioniTaskRepository.save(SessioniTask);
				    	}
					    	else {		// sovrascrivi e chiudi
						    	task.getContatore().setStart(START);
						    	task.getContatore().setStop(STOP);
						    	task.getContatore().setFinaltime(finalTime); 
						    	
						    	// salvo in automatico la data fine task in corrispondenza dello stop contatore
						    	taskservice.setStopTaskDate(STOP, taskId);
						    	
						    	//setto lo stato in chiuso
						    	task.setStato("chiuso");
								repositTask.save(task); 
								
						    	// sessione cronologia task
								SessioniTask.setContatore(contatore);
								SessioniTask.setAzione("ore lavorate");
								SessioniTask.setTime(task.getDataModifica());
								SessioniTask.setWorktime(contatoreservice.calcoloFinalTimeString(task));
								SessioniTask.setVariazione(contatoreservice.findTimeToString(START, task.getContatore().getPause()));
								SessioniTaskRepository.save(SessioniTask);
						    	}
		    	
	    		

	    	}
	    	
	    	
    	model.addAttribute("contatore", contatore);
    	
    	repositContatore.save(task.getContatore());
	    
		
		return "redirect:/Task/{id}";
	
    }
    
  
}
