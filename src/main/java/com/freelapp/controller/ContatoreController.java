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
    public String gestioneTimer(@PathVariable("id") Integer taskId, @ModelAttribute("contatore") Contatore contatore,
	    Model model) {

	// richiamo l'id del task
	Task task = repositTask.getReferenceById(taskId);

	// creo un boleano per stabilire se il contatore esiste
	Boolean contatoreIsTrue = false;

	// se il contatore esiste
	if (task.getContatore() != null) {

	    contatoreIsTrue = true;
	    model.addAttribute("finaltime", task.getContatore().getFinaltime());

	}

	model.addAttribute("contatoreIsTrue", contatoreIsTrue);

	model.addAttribute("contatore", contatore);

	return "/Contatore/timer";
    }

	// @GetMapping("/start/{id}")
	// public String getstartContatore(@PathVariable("id") Integer taskId, @ModelAttribute("contatore") Contatore contatore,
	// Model model) {

	// 	// richiamo l'id del task
	// Task task = repositTask.getReferenceById(taskId);

	// // se il contatore esiste, restituiscilo al modello
	// if (task.getContatore() != null && task.getContatore().getStop() == null && task.getContatore().getStart() != null) {

	// 	//////// parte per javascript
	// 	// creo un boleano per stabilire se il contatore esiste
	// 	Boolean contatoreIsTrue = true;
	//     model.addAttribute("finaltime", task.getContatore().getFinaltime());
	// 	model.addAttribute("contatore", task.getContatore());
	// }

	// 	return "/Contatore/timer"; 
	// }

    @PostMapping("/start/{id}")
    public String startContatore(@PathVariable("id") Integer taskId, @ModelAttribute("contatore") Contatore contatore,
	    Model model) {

	// richiamo l'id del task
	Task task = repositTask.getReferenceById(taskId);

	//////// parte per javascript //////////////////
		// creo un boleano per stabilire se il contatore esiste
		// Boolean contatoreIsTrue = false;

		// // se il contatore esiste
		// if (task.getContatore() != null) {

		// 	contatoreIsTrue = true;
		// 	model.addAttribute("finaltime", task.getContatore().getFinaltime());

		// }
	//////// parte per javascript //////////////////

	// se il contatore esiste, restituiscilo al modello
	if (task.getContatore() != null && task.getContatore().getStop() == null && task.getContatore().getStart() != null) {

		

	    // imposta il valore di restart
	    task.getContatore().setRestart(LocalDateTime.now());

	    // collego nel modello html il task e il contatore
	    model.addAttribute("task", task);
	    model.addAttribute("contatore", contatore);
	}
	// verifico che il contatore esista e lo stop sia nullo
	else if (task.getContatore() != null && task.getContatore().getStop() != null) {

	}
	// verifico se il contatore è stato resettato
	else if (task.getContatore() != null && task.getContatore().getStart() == null) {
	 // imposta il valore di start
	    task.getContatore().setStart(LocalDateTime.now());

	    // collego nel modello html il task e il contatore
	    model.addAttribute("task", task);
	    model.addAttribute("contatore", contatore);
	}

	// se il contatore non esiste:
	else {

	    // istanzio un nuovo contatore
	    contatore = new Contatore();

	    // associo al task il nuovo contatore
	    task.setContatore(contatore);

	    // eseguo il TIMESTAMP
	    contatore.setStart(LocalDateTime.now());

	    // collego nel modello html il task e il contatore
	    model.addAttribute("task", task);
	    model.addAttribute("contatore", contatore);

	    // salvo il contatore a DB
	    repositContatore.save(contatore);
	}

	repositContatore.save(task.getContatore());

	return "/Contatore/timer";

    }

    @PostMapping("/Contatore/pause/{id}")
    public String pauseContatore(@PathVariable("id") Integer taskId, Model model) {

	// richiamo l'id del task
	Task task = repositTask.getReferenceById(taskId);
	Contatore contatore = task.getContatore();

	// verifica che il contatore esista e che non sia stato resettato
	if (contatore != null && contatore.getStop() == null && contatore.getStart() != null) {

	    
	    // recupero timestamp di inizio e pausa
	    LocalDateTime PAUSE = task.getContatore().getPause();
	    LocalDateTime START = task.getContatore().getStart();
	    LocalDateTime RESTART = task.getContatore().getRestart();

	    // se la pausa non esiste
	    if (PAUSE == null) {

		PAUSE = LocalDateTime.now();

		contatore.setPause(PAUSE);

		// metodo che calcola la differenza fra i due timestamp
		Long FinalTime = contatore.findDifference(START, PAUSE);

		// imposto il finaltime differenza fra stop e pausa - tipo Long
		contatore.setFinaltime(FinalTime);

		// ad ogni clic la funzione prende gli stop e incrementa di 1
		contatore.setStop_numbers(contatore.getStop_numbers() + 1);

		// salvo il contatore
		repositContatore.save(contatore);

	    }

	    // se la pausa esiste già
	    else {

		// imposto comunque una nuova pausa
		contatore.setPause(LocalDateTime.now());

		// metodo che calcola il tempo trascorso partendo dal restart e la nuova pausa
		Long FinalTime = contatore.findTimeRestart(RESTART, PAUSE, task);

		// imposto il finaltime differenza fra stop e pausa - tipo Long
		contatore.setFinaltime(FinalTime);

		// ad ogni clic la funzione prende gli stop e incrementa di 1
		contatore.setStop_numbers(contatore.getStop_numbers() + 1);

		// salvo il contatore
		repositContatore.save(contatore);
	    }

	}

	// verifico che il contatore non sia stoppato, se stoppato non faccio nulla
	else if (task.getContatore() != null && task.getContatore().getStop() != null) {
	}

	// else se il contatore non esiste schiacciare la pausa non esegue nessun
	// comando

	return "/Contatore/timer";
    }

    @PostMapping("/Contatore/stop/{id}")
    public String stopContatore(@PathVariable("id") Integer taskId, Model model) {
	// richiamo l'id del task
	Task task = repositTask.getReferenceById(taskId);

	
	// verifica che il contatore esista 
	if (task.getContatore() != null ) {
		
		// setto le variabili
		LocalDateTime STOP = task.getContatore().getStop();
		LocalDateTime START = task.getContatore().getStart();
		LocalDateTime RESTART = task.getContatore().getRestart();
		LocalDateTime PAUSE = task.getContatore().getPause();
		Contatore contatore = task.getContatore();


	    // verifica che il contatore sia ancora stoppabile, cioè non abbia già lo stop
	    // settato e che non sia stato resettato
	    if (task.getContatore().getStop() == null && contatore.getStart() != null) {

		 // verifica che il contatore sia in fase di run (ma non abbia pause)
		 if (PAUSE == null || RESTART == null) {

		    // eseguo il TIMESTAMP dello STOP e chiudo il contatore
		     STOP = LocalDateTime.now();

		     contatore.setStop(STOP);
		 
		     if(PAUSE != null) {
			 
			 // metodo che calcola la differenza fra i due timestamp
			 Long FinalTime = contatore.findDifference(START, PAUSE);
			 
			 // imposto il finaltime differenza fra stop e pausa - tipo Long
			 contatore.setFinaltime(FinalTime);
			 
			 repositContatore.save(task.getContatore());
		     }
		     else {
			// metodo che calcola la differenza fra i due timestamp
			    Long FinalTime = contatore.findDifference(START, STOP);

			    // imposto il finaltime differenza fra stop e pausa - tipo Long
			    contatore.setFinaltime(FinalTime);

			    repositContatore.save(task.getContatore());
		     }
		}

		  // se invece il contatore è in fase di run, ma è stato riavviato
		  else if (RESTART != null && PAUSE.isBefore(RESTART)) {

		    // eseguo il TIMESTAMP dello STOP e chiudo il contatore
		    task.getContatore().setStop(LocalDateTime.now());

		    // eseguo il TIMESTAMP dello STOP e chiudo il contatore
		     STOP = LocalDateTime.now();

		     contatore.setStop(STOP);

		    // metodo che calcola il tempo trascorso partendo dal restart e la nuova pausa
		    Long FinalTime = contatore.findTimeRestartStop(RESTART, STOP, task);

		    // imposto il finaltime differenza fra stop e pausa - tipo Long
		    contatore.setFinaltime(FinalTime);

		    repositContatore.save(task.getContatore());
		}

		// verifico che il contatore sia non attivo
		  else if (RESTART != null && PAUSE.isAfter(RESTART)) {

		   // eseguo il TIMESTAMP dello STOP e chiudo il contatore
		   task.getContatore().setStop(LocalDateTime.now());
		   
		    repositContatore.save(task.getContatore());
		}
		

	    }

	}

	return "/Contatore/timer";
    }

    @PostMapping("/Contatore/reset/{id}")
    public String resetContatore(@PathVariable("id") Integer taskId) {

	// richiamo l'id del task
	Task task = repositTask.getReferenceById(taskId);

	// verifica che il contatore esista
	if (task.getContatore() != null) {

	    // eseguo il RESET
	    task.getContatore().setStop(null);
	    task.getContatore().setStart(null);
	    task.getContatore().setPause(null);
	    task.getContatore().setFinaltime(null);
	    task.getContatore().setStop_numbers(0);
	    task.getContatore().findDifference(LocalDateTime.now(), LocalDateTime.now());

	    // salvo il contatore
	    repositContatore.save(task.getContatore());

	}

	return "/Contatore/timer";
    }
}
