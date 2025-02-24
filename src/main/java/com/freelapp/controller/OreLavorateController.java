package com.freelapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.freelapp.model.Contatore;
import com.freelapp.model.Task;
import com.freelapp.repository.TaskRepository;

public class OreLavorateController {
    
    @Autowired
    private TaskRepository repositTask;
    
    //inserimento con input da parte dell'utente di localDateTime start e FinalTime (conversione minuti e ore in secondi)
    // scrivere un metodo per calcolo inverso al fine di salvare lo stop LocalDateTime.

    
    @GetMapping("/orelavorate/{id}")
    public String gestioneTimer(@PathVariable("id") Integer taskId, @ModelAttribute("contatore") Contatore contatore,
	    Model model, BindingResult bindingresult) {
	// richiamo l'id del task
		Task task = repositTask.getReferenceById(taskId);
	return "/orelavorate";
	
    }
}
