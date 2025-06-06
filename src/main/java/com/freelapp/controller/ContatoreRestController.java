package com.freelapp.controller;

import java.time.LocalDateTime;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.freelapp.model.Contatore;
import com.freelapp.model.Task;
import com.freelapp.repository.ContatoreRepository;
import com.freelapp.repository.TaskRepository;
import com.freelapp.service.ContatoreService;
import com.freelapp.service.TaskService;

@RestController
public class ContatoreRestController {
	
	 @Autowired
    private ContatoreService contatoreservice;
  
    @Autowired
	  private TaskService taskservice;

    @Autowired
    private TaskRepository repositTask;

    @Autowired
    private ContatoreRepository repositContatore;
	
	 @GetMapping("/start/{id}")
    public JSONObject startContatore(@PathVariable("id") Integer taskId, @ModelAttribute("contatore") Contatore contatore,
    		// l'endpoint passato dal model serve a far ritornare sulla pagina di partenza dopo aver cliccato su start
    		@ModelAttribute("endPoint") String endPoint,
	    Model model, BindingResult bindingresult) {
    	
    	//creazione json
		JSONObject JsonObj = new JSONObject();
    	
    	// metto in pausa gli altri contatori
	    	contatoreservice.pauseOtherTimers();
    	
	// richiamo l'id del task
	Task task = repositTask.getReferenceById(taskId);

	// CONTATORE IN RESTART
	if ((task.getContatore() != null) && (task.getContatore().getStop() == null)
		&& (task.getContatore().getStart() != null)) {

		
		
	    // CONTATORE IS RUN
	    if (contatoreservice.contatoreIsRun(task) != false) {

//	    	// metto in pausa gli altri contatori
//	    	contatoreservice.pauseOtherTimers();
	    	
	    	boolean contatoreIsRun = contatoreservice.contatoreIsRun(task);

		    LocalDateTime restartTime = task.getContatore().getRestart();

		    Long FinalTime = task.getContatore().getFinaltime();
		    LocalDateTime timeNow = LocalDateTime.now();
		if (contatoreIsRun == true && restartTime == null) {

			task.getContatore()
				.setFinaltime((long) (timeNow.getSecond() - task.getContatore().getStart().getSecond()));

		    } else if (contatoreIsRun == true && restartTime != null) {

			task.getContatore().setFinaltime(
				(long) (FinalTime + (timeNow.getSecond() - task.getContatore().getRestart().getSecond())));
		    }
		
		model.addAttribute("finaltime", task.getContatore().getFinaltime());
		Integer finalTime = task.getContatore().getFinaltime().intValue();
		JsonObj.put("finaltime", finalTime);
		// parte per javascript
		
		model.addAttribute("contatoreIsTrue", contatoreservice.contatoreIsTrue(task));
		//contatoreservice.contatoreIsRun(task, model);
		 model.addAttribute("contatoreIsRun", contatoreservice.contatoreIsRun(task));
		 JsonObj.put("contatoreIsTrue", contatoreservice.contatoreIsTrue(task));
		 JsonObj.put("contatoreIsRun", contatoreservice.contatoreIsRun(task));
	    }

	    // CONTATORE IS NOT RUN
	    else if (contatoreservice.contatoreIsRun(task) != true) {

		// imposta il valore di restart
		task.getContatore().setRestart(LocalDateTime.now());
		task.setStato("in corso");

		Long FinalTime = task.getContatore().getFinaltime();

		// collego nel modello html il task e il contatore
		model.addAttribute("task", task);
		model.addAttribute("contatore", contatore);
		model.addAttribute("finaltime", FinalTime);
		JsonObj.put("task", task.getId());
		JsonObj.put("contatore", contatore.getId());
		JsonObj.put("finaltime", FinalTime);
		task.setDataModifica(LocalDateTime.now());
		repositContatore.save(task.getContatore());

		// parte per javascript
		model.addAttribute("contatoreIsTrue", contatoreservice.contatoreIsTrue(task));
		model.addAttribute("contatoreIsRun", contatoreservice.contatoreIsRun(task));
		JsonObj.put("contatoreIsTrue", contatoreservice.contatoreIsTrue(task));
		JsonObj.put("contatoreIsRun", contatoreservice.contatoreIsRun(task));
	    }

	}

	// CONTATORE IN STOP
	else if ((task.getContatore() != null) && (task.getContatore().getStop() != null)) {
	    // se il contatore ha lo stop, non fare nulla

	    // Parte per Javascript
	    Long FinalTime = task.getContatore().getFinaltime();
	    model.addAttribute("contatoreIsTrue", contatoreservice.contatoreIsTrue(task));
	    JsonObj.put("contatoreIsTrue", contatoreservice.contatoreIsTrue(task));
	    contatoreservice.contatoreIsRun(task);
	    model.addAttribute("finaltime", FinalTime);
	    JsonObj.put("finaltime", FinalTime);
	}

	// CONTATORE RESETTATO
	else if ((task.getContatore() != null) && (task.getContatore().getStart() == null)) {
	    // imposta il valore di start
	    task.getContatore().setStart(LocalDateTime.now());
	    task.setStato("in corso");

	    // collego nel modello html il task e il contatore
	    model.addAttribute("task", task);
	    model.addAttribute("contatore", contatore);
	    JsonObj.put("task", task.getId());
		JsonObj.put("contatore", contatore.getId());
	    task.setDataModifica(LocalDateTime.now());
	    repositContatore.save(task.getContatore());

	    // Parte per Javascript
	    Long FinalTime = task.getContatore().getFinaltime();
	    model.addAttribute("contatoreIsTrue", contatoreservice.contatoreIsTrue(task));
	    model.addAttribute("contatoreIsRun", contatoreservice.contatoreIsRun(task));
	    model.addAttribute("finaltime", FinalTime);
	    JsonObj.put("contatoreIsTrue", contatoreservice.contatoreIsTrue(task));
		JsonObj.put("contatoreIsRun", contatoreservice.contatoreIsRun(task));
		JsonObj.put("finaltime", FinalTime);
	}

	// CONTATORE IN START
	else {
		
		// Appena avvi un nuovo timer metti in pausa eventuali timer attivi
		

	    // istanzio un nuovo contatore
	    contatore = new Contatore();

	    // associo al task il nuovo contatore
	    task.setContatore(contatore);
	    
	 // metto in pausa gli altri contatori
    	contatoreservice.pauseOtherTimers();

	    // eseguo il TIMESTAMP
	    contatore.setStart(LocalDateTime.now());
	    task.setStato("in corso");
	    contatore.setFinaltime(0l);

	    // collego nel modello html il task e il contatore
	    model.addAttribute("task", task);
	    model.addAttribute("contatore", contatore);
	    JsonObj.put("task", task.getId());
	    JsonObj.put("contatore", contatore.getId());
	    
	    task.setDataModifica(LocalDateTime.now());
	    // salvo il contatore a DB
	    repositContatore.save(contatore);
	    // parte per javascript: serve per collegare il finaltime da java a javascript
	    // sul frontend
	    model.addAttribute("contatoreIsTrue", contatoreservice.contatoreIsTrue(task));
	    model.addAttribute("contatoreIsRun", contatoreservice.contatoreIsRun(task));
	    JsonObj.put("contatoreIsTrue", contatoreservice.contatoreIsTrue(task));
		JsonObj.put("contatoreIsRun", contatoreservice.contatoreIsRun(task));

	}

	ContatoreController.contatoreInUso = contatore;
	ContatoreController.taskInUso = task;
	//contatoreAttivato = true; 
	ContatoreController.contatoreCliccatoPreRefresh = true;
	

//	return "/Contatore/timer";
	//return "redirect:" + endPoint;
	return JsonObj;

    }
	

}
