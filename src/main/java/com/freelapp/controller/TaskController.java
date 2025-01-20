package com.freelapp.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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


@Controller
public class TaskController {

	@Autowired
	private TaskRepository repositTask;
	  
	@Autowired
	private ProgettoRepository repositProgetto;
	
//	@Autowired
//	private StatoRepository repositStato;
	
			@GetMapping("/Task")
			public String iMieiTask(Model model) {
				
				model.addAttribute("taskList");
				
				return "/Task/freelapp-listaTask";
			}
	
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
				

				if(bindingResult.hasErrors()) {
				  bindingResult.addError(
				   new ObjectError("Errore", "Huston abbiamo un problema"));

				   return  "/Task/insertTask";				
				   }

				// salvo il task
				repositTask.save(task);
				
				 return "redirect:/dashboard";       
			}
			
			
			@GetMapping("/Task/edit/{id}")
			public String edit(@PathVariable("id") Integer id, Model model) { 
				
				Task formTask = repositTask.getReferenceById(id);
				model.addAttribute("formTask", formTask);
				
				return "/Task/editTask";
			}
			
			
			@PostMapping("/Task/edit/{id}")
		    public String updateTask(@PathVariable("id") Integer id, @ModelAttribute("formTask") Task formTask, BindingResult bindingResult, Model model) {					
				repositTask.getReferenceById(id);
 
				repositTask.save(formTask);
				
    				if(bindingResult.hasErrors()) {
    				    bindingResult.addError(new ObjectError("Errore", "c'è un errore nel salvataggio del form"));
    				    
    				    return  "/Task/editTask";				
    				}

	// 				if (bindingResult.hasErrors()) {
    //     List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
    //     // Here you can change ok to badRequest depending on your use case.
    //     return ResponseEntity.ok(new ErrorResponse("404", "Validation failure", errors));
    //     // In case if you want to fail the request, you need to use the below: 
    //     // return ResponseEntity.badRequest().body(new ErrorResponse("404", "Validation failure", errors));
    // }
				return "redirect:/dashboard"; 
				     
			    }
			
			@PostMapping("Task/delete/{id}")
			public String deleteTask(@PathVariable("id") Integer id) {
				
				repositTask.deleteById(id);
			
				return "redirect:/dashboard"; 
			}
			
		
}