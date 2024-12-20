package com.freelapp.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
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
	
	boolean b;
	Task t = repositTask.getReferenceById(taskId);
	
	if (t.getContatore() == null) {
	
			Contatore contatore = new Contatore();
	
			contatore.setTask(t);
	
			System.out.println("indice del task: " + contatore.getTask().getId()); 

			b = true;
			model.addAttribute("contatore", contatore);
			model.addAttribute("b", b);

			return "/Contatore/timer";
	   }
	else {
		    Contatore contatore = t.getContatore();
		    
		    b = false;
			model.addAttribute("contatore", contatore);
			model.addAttribute("b", b);

			return "/Contatore/timer";
	}
 }

//@PostMapping("/Contatore/timer/{id}")
//public String gestioneTimer( @Valid @ModelAttribute("contatore") Contatore contatore, BindingResult bindingResult, Model model) {
		
	//if(bindingResult.hasErrors()) {
	  //   return "/Contatore/timer";
	  //}
	
//	System.out.println("indice del task" + contatore.getTask().getId()); 
	
	//repositContatore.save(contatore);
	
	//return "redirect:/Contatore/timer/" + contatore.getTask().getId();
	
  //}


@PostMapping("/Contatore/start")
public String startContatore(@ModelAttribute("contatore") Contatore contatore, @ModelAttribute("b") boolean b, Model model)
{		
	//contatore.setStart(LocalDateTime.now());
	
	System.out.println("indice del task in start" + contatore.getTask().getId()); 
	
	if (b)
	   repositContatore.updateStart(LocalDateTime.now(), contatore.getId());
	
	//repositContatore.save(contatore);
	
	return "redirect:/dashboard";
}


@PostMapping("/Contatore/pause")
public String pauseContatore(@ModelAttribute("contatore") Contatore contatore, Model model)
{
	//contatore.setPause(LocalDateTime.now());
	
	contatore.addStop_numbers();
	
	repositContatore.updatePause(LocalDateTime.now(), contatore.getId());
	
	//repositContatore.save(contatore);
	
	System.out.println(contatore.getStop_numbers());
	
	return "redirect:/dashboard";
}


@PostMapping("/Contatore/stop")
public String stopContatore(@ModelAttribute("contatore") Contatore contatore, Model model)
{
	//contatore.setStop(LocalDateTime.now());

	repositContatore.updateStop(LocalDateTime.now(), contatore.getId());
	//repositContatore.save(contatore);
	
	return "redirect:/dashboard";
 }

}
