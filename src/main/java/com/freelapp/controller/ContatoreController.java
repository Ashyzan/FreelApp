package com.freelapp.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.freelapp.model.Contatore;
import com.freelapp.model.Task;
import com.freelapp.repository.ContatoreRepository;
import com.freelapp.repository.TaskRepository;

@Controller
public class ContatoreController {
	
@Autowired
private TaskRepository repositTask;

@Autowired
private ContatoreRepository repositContatore;


@GetMapping("/Contatore/timer/{id}")
public String gestioneTimer(@PathVariable("id") Integer taskId, Model model) {
	
        // richiamo l'id del task 
	Task task = repositTask.getReferenceById(taskId);
	
	return "/Contatore/timer";
 }


@PostMapping("/start/{id}")
public String startContatore(@PathVariable("id") Integer taskId, 
	@ModelAttribute("contatore") Contatore contatore, Model model)
{		
    	// richiamo l'id del task
    	Task task = repositTask.getReferenceById(taskId);
    
    	// istanzio un nuovo contatore
    	contatore = new Contatore();
    	
    	// associo al task il nuovo contatore
	task.setContatore(contatore);
    	
	//eseguo il TIMESTAMP
	contatore.setStart(LocalDateTime.now());
	
	// collego nel modello html il task e il contatore
	model.addAttribute("task" , task);
	model.addAttribute("contatore" , contatore);

	// salvo il contatore a DB
	repositContatore.save(contatore);
	
	return "/Contatore/timer";
	
}


@PostMapping("/Contatore/pause")
public String pauseContatore(@ModelAttribute("contatore") Contatore contatore, 
	Model model)
{
	
	return "redirect:/Contatore/timer/{id}";
}


@PostMapping("/Contatore/stop")
public String stopContatore(@ModelAttribute("contatore") Contatore contatore, 
	Model model)
{

	return "redirect:/Contatore/timer/{id}";
 }

}
