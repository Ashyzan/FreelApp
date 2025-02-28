package com.freelapp.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
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

public class OreLavorateController {
    
    @Autowired
    private TaskRepository repositTask;
    
    @Autowired
    private ContatoreRepository repositContatore;
    
    @Autowired
    private ContatoreService contatoreservice;
    
    //inserimento con input da parte dell'utente di localDateTime start e FinalTime (conversione minuti e ore in secondi)
    

    
    @PostMapping("/orelavorate/{id}")
    public String gestioneTimer(@PathVariable("id") Integer taskId, @ModelAttribute("contatore") Contatore contatore,
	    Model model, BindingResult bindingresult) {
	// richiamo l'id del task
		Task task = repositTask.getReferenceById(taskId);
		
		LocalDateTime STOP = contatoreservice.findStop(null, null);
		 //model.addAttribute("orelavorate", orelavorate);
		
		//repositContatore.save(task.getContatore().setStop(STOP));
			
		return "/orelavorate";
	
    }
    
  
}
