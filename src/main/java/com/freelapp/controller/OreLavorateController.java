package com.freelapp.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.freelapp.model.Contatore;
import com.freelapp.model.Task;
import com.freelapp.repository.ContatoreRepository;
import com.freelapp.repository.TaskRepository;
import com.freelapp.service.ContatoreService;
import com.freelapp.service.OreLavorateService;

@Controller
public class OreLavorateController {
    
    @Autowired
    private TaskRepository repositTask;
    
    @Autowired
    private ContatoreRepository repositContatore;
    
    @Autowired
    private OreLavorateService orelavorateservice;
    

    // ricevo i parametri dal modello e salvo i dati del contatore
    @PostMapping("/orelavorate/{id}")
    public String gestioneTimer(@PathVariable("id") Integer taskId, @ModelAttribute("contatore") Contatore contatore,
    		@ModelAttribute("date") String data, @ModelAttribute("time")String time, Model model, BindingResult bindingresult) {
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
	    
	    if(contatore == null) {
	    	
	    	contatore = new Contatore();
	    	task.setContatore(contatore);
	    	
	    	task.getContatore().setFinaltime(finalTime); 
	    	task.getContatore().setStart(START);
	    	task.getContatore().setStop(STOP);
	    }
	    
	    else {
	    	task.getContatore().setFinaltime(finalTime); 
	    	task.getContatore().setStart(START);
	    	task.getContatore().setStop(STOP);
	    }
	    
		model.addAttribute("contatore", contatore);
		   
		repositContatore.save(task.getContatore());
		
		return "redirect:/Task/{id}";
	
    }
    
  
}
