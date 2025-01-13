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


@PostMapping("/Contatore/pause/{id}")
public String pauseContatore(@PathVariable("id") Integer contatoreId)
{
    	// richiamo il contatore a DB
	Contatore contat = repositContatore.getReferenceById(contatoreId);
	
	contat.setPause(LocalDateTime.now());
	
	// recupero timestamp di inizio e pausa
	LocalDateTime PAUSE = contat.getPause();
	LocalDateTime START = contat.getStart();
	
	// metodo che calcola la differenza fra i due timestamp
	String FinalTime = contat.findDifference(START, PAUSE);

	// ad ogni clic la funzione prende gli stop e incrementa di 1
	contat.setStop_numbers(contat.getStop_numbers() + 1);
	
	// salvo il contatore
	repositContatore.save(contat);
	
	return "/Contatore/timer";
}


@PostMapping("/Contatore/stop/{id}")
public String stopContatore(@PathVariable("id") Integer contatoreId)
{
    	// richiamo il contatore a DB
    	Contatore contat = repositContatore.getReferenceById(contatoreId);
    	
	//eseguo il TIMESTAMP dello STOP
	contat.setStop(LocalDateTime.now());

	// DA IMPLEMENTARE CALCOLO FINALE DEL TEMPO TRASCORSO FRA LO START E LO STOP
	
	// salvo il contatore
    	repositContatore.save(contat);
    	
	return "/Contatore/timer";
 }

}
