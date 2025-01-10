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
	
	// istanzio un nuovo contatore
	Contatore C = new Contatore();
	C.setStart(LocalDateTime.now());
	
	// associo il contatore al task
	task.setContatore(C);
	
	// associo la nuova istanza di contatore all'oggetto contatore nel modello themyleaf
	model.addAttribute("contatore", C);
	
	return "/Contatore/timer";
 }


@PostMapping("/start")
public String startContatore( @ModelAttribute("contatore") Contatore contatore, 
	Model model)
{		
	
	repositContatore.save(contatore);
	
	return "/Contatore/timer";
	
}


@PostMapping("/Contatore/pause")
public String pauseContatore(@ModelAttribute("contatore") Contatore contatore, Model model)
{
	//contatore.setPause(LocalDateTime.now());
	
	//contatore.addStop_numbers();
	
	repositContatore.updatePause(LocalDateTime.now(), contatore.getId());
	
	//repositContatore.save(contatore);
	
	//System.out.println(contatore.getStop_numbers());
	
	return "redirect:/Contatore/timer/{id}";
}


@PostMapping("/Contatore/stop")
public String stopContatore(@ModelAttribute("contatore") Contatore contatore, Model model)
{
	//contatore.setStop(LocalDateTime.now());

	repositContatore.updateStop(LocalDateTime.now(), contatore.getId());
	//repositContatore.save(contatore);
	
	return "redirect:/Contatore/timer/{id}";
 }

}
