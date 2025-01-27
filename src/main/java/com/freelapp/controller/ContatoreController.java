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
	
	model.addAttribute("contatoreIsTrue" , contatoreIsTrue);
	
	model.addAttribute("contatore", contatore);
	
	return "/Contatore/timer";
    }

    @PostMapping("/start/{id}")
    public String startContatore(@PathVariable("id") Integer taskId, @ModelAttribute("contatore") Contatore contatore,
	    Model model) {

	// richiamo l'id del task
	Task task = repositTask.getReferenceById(taskId);
    	
    	// se il contatore esiste, restituiscilo al modello
    	if (task.getContatore() != null) {
  
    	// collego nel modello html il task e il contatore
    	    model.addAttribute("task" , task);
    	    model.addAttribute("contatore" , contatore);
    	}
    	
    	// se il contatore non esiste:
    	else  {
    	    
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
    	}
	

	// set pause to null
	//task.getContatore().setPause(null);
	
	repositContatore.save(task.getContatore());
	
//System.out.println("****************************************** ANNULLO LA PAUSA ");
//	while (task.getContatore().getPause() == null && task.getContatore().getStop() == null); {
//
//	    Contatore contatore3 = task.getContatore();
//	    
//	    Timer timer = new Timer();
//System.out.println("****************************************** FUORI DAL TIMER ");
//	    TimerTask timerTask = new TimerTask() {
//		@Override
//		public void run() {
//		    
//		    // recupero il timestamp di start
//		    LocalDateTime start = task.getContatore().getStart();
//
//System.out.println("****************************************** lo start è " + start);
//		    // salvo a db il tempo che scorre nel campo FinalTimeSecondsNow
//		    int FinalTimeSec = task.getContatore().FindDifferenceEverySecond(start);
//		    
//		    task.getContatore().setFinalTimeSecondsNow(FinalTimeSec);
//
//		    repositContatore.save(contatore3);
//		}
//	    };
//
//	    timer.scheduleAtFixedRate(timerTask, 0, 1000);
//
//	    // timer.cancel();
//	    
//	}

	

	return "/Contatore/timer";

    }

    @PostMapping("/Contatore/pause/{id}")
    public String pauseContatore(@PathVariable("id") Integer taskId) {

	// richiamo l'id del task
	Task task = repositTask.getReferenceById(taskId);

	// verifica che il contatore esista
	if (task.getContatore() != null) {

	    task.getContatore().setPause(LocalDateTime.now());

	    // recupero timestamp di inizio e pausa
	    LocalDateTime PAUSE = task.getContatore().getPause();
	    LocalDateTime START = task.getContatore().getStart();

	    // metodo che calcola la differenza fra i due timestamp
	    Long FinalTime = task.getContatore().findDifference(START, PAUSE);
	    
	    // imposto il finaltime differenza fra stop e pausa - tipo Long
	    task.getContatore().setFinaltime(FinalTime);

	    // ad ogni clic la funzione prende gli stop e incrementa di 1
	    task.getContatore().setStop_numbers(task.getContatore().getStop_numbers() + 1);

	    // salvo il contatore
	    repositContatore.save(task.getContatore());
	    
//	    model.addAttribute("task", task);
//	    model.addAttribute("contatore", task.getContatore());
	    

	}

	return "/Contatore/timer";
    }

    @PostMapping("/Contatore/stop/{id}")
    public String stopContatore(@PathVariable("id") Integer taskId) {
	// richiamo l'id del task
	Task task = repositTask.getReferenceById(taskId);

	// verifica che il contatore esista
	if (task.getContatore() != null) {

	    // verifica che il contatore sia ancora attivo
	    if (task.getContatore().getStop() == null) {

		// eseguo il TIMESTAMP dello STOP
		task.getContatore().setStop(LocalDateTime.now());
		// recupero timestamp di start e stop
	    }
	    LocalDateTime STOP = task.getContatore().getStop();
	    LocalDateTime START = task.getContatore().getStart();

	    // metodo che calcola la differenza fra i due timestamp
	    Long FinalTime = task.getContatore().findDifference(START, STOP);

	    // salvo il contatore
	    repositContatore.save(task.getContatore());
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
	    task.getContatore().setStop_numbers(0);
	    task.getContatore().findDifference(LocalDateTime.now(), LocalDateTime.now());

	    // salvo il contatore
	    repositContatore.save(task.getContatore());

	}

	return "/Contatore/timer";
    }
}
