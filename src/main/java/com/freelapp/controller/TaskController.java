package com.freelapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import com.freelapp.model.Task;
import com.freelapp.repository.TaskRepository;
import com.freelapp.model.Stato;
import com.freelapp.model.Progetto;
import com.freelapp.repository.ProgettoRepository;
import com.freelapp.model.StatoTask;


@Controller
public class TaskController {

	@Autowired
	private TaskRepository repositTask;
	  
	@Autowired
	private ProgettoRepository repositProgetto;
	
	
			@GetMapping("/Task/{id}")
			public String descrizioneTask(@PathVariable("id") int taskId, Model model) {
		
				model.addAttribute("task", repositTask.getReferenceById(taskId));
	
				return "/Task/descrizioneTask";
		   }
	
			@GetMapping("/Task/insert/{id}")
			public String insertTask(@PathVariable("id") Integer id, Model model) {
				
				Progetto progetto = repositProgetto.findById(id).get();
				
				Task formTask = new Task();
				
				formTask.setProgetto(progetto);
			 
				model.addAttribute("formTask", formTask);
				
				return "/Task/insertTask";
			}
	
	
			@PostMapping("/Task/insert/{id}")
			public String storeTask(@Valid @ModelAttribute("formTask") Task formTask, BindingResult bindingResult, Model model) {
		
				if(bindingResult.hasErrors()) {
					return "/Task/insertTask";
				}
				
				 StatoTask st = StatoTask.IN_CORSO;
				 Stato s = new Stato();
				 s.setTipoStato(st);
				 formTask.setStato(s);
				 
				 repositTask.save(formTask);
				
				 return "redirect:/Progetti/" + formTask.getProgetto().getId();       
			}
		
			
}
