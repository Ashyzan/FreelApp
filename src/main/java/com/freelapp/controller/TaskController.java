package com.freelapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.freelapp.model.Progetto;
import com.freelapp.model.Task;
import com.freelapp.repository.ProgettoRepository;
//import com.freelapp.model.Stato;
//import com.freelapp.repository.StatoRepository;
import com.freelapp.repository.TaskRepository;

import jakarta.validation.Valid;
//import com.freelapp.model.StatoTask;

@Controller
public class TaskController {

	@Autowired
	private TaskRepository repositTask;
	  
	@Autowired
	private ProgettoRepository repositProgetto;
	
//	@Autowired
//	private StatoRepository repositStato;
	
			@GetMapping("/Task/{id}")
			public String descrizioneTask(@PathVariable("id") int taskId, Model model) {
		
				model.addAttribute("task", repositTask.getReferenceById(taskId));
	
				return "/Task/descrizioneTask";
		   }
	
			@GetMapping("/Task/insert/progetto-{id}")
			public String insertTask(@PathVariable("id") Integer id,
				
				Progetto progetto ,Model model) {
			    	
			    	// richiamo il progetto tramite id
			    	progetto = repositProgetto.getReferenceById(id);
			    
			    	// istanzio un nuovo task
			    	Task newTask = new Task();
			    	// attribuisco il task al progetto
			    	newTask.setProgetto(progetto);
				
			    	// riporto nel modello il task
				model.addAttribute("task" , newTask);
				
				
				return "/Task/insertTask";
			}
	

			@PostMapping("/Task/insert/{id}")
			public String storeTask(@PathVariable("id") Integer id , 
				@ModelAttribute("task") Task task, 
				BindingResult bindingResult, Model model) {
			    	
			    	// richiamo il progetto tramite id
			    	Progetto progetto = repositProgetto.getReferenceById(id);
			    
			    	// attribuisco il task passato dal modello al progetto (progettoRif)
				task.setProgetto(progetto);
				
				// restituisco il task al modello
				model.addAttribute("task", task);
				
				// salvo il task
				repositTask.save(task);
				
				 return "redirect:/dashboard";       
			}
			
			
			@GetMapping("/Task/edit/{id}")
			public String edit(@PathVariable("id") Integer id, Model model) {
				
//				List<Stato> listStati = repositStato.findAll();  
				
				Task formTask = repositTask.getReferenceById(id);
				
//			    model.addAttribute("statiForm", listStati);
				model.addAttribute("formTask", formTask);
				
				return "/Task/editTask";
			}
			
			
			@PostMapping("/Task/edit/{id}")
		    public String updateTask(@Valid @ModelAttribute("formTask") Task formTask, BindingResult bindingResult, Model model) {					
				
				if(bindingResult.hasErrors()) {
//				   model.addAttribute("statiForm", repositStato.findAll());
				   return  "/Task/editTask";				
				   }
 
				repositTask.save(formTask);
				
				return "redirect:/Progetti/" + formTask.getProgetto().getId(); 
				     
			    }
		
}