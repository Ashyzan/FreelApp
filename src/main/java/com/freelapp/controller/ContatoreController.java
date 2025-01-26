package com.freelapp.controller;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

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
	    model.addAttribute("task", task);
	    model.addAttribute("contatore", contatore);
	}

	// se il contatore non esiste:
	else {

	    // istanzio un nuovo contatore
	    Contatore contatore1 = new Contatore();

	    // associo al task il nuovo contatore
	    task.setContatore(contatore1);

	    // eseguo il TIMESTAMP
	    contatore1.setStart(LocalDateTime.now());

	    //LocalDateTime startF = contatore1.getStart();

	    // collego nel modello html il task e il contatore
	    model.addAttribute("task", task);
	    model.addAttribute("contatore", contatore1);

	    // salvo il contatore a DB
	    repositContatore.save(contatore);
	}

	// set pause to null
	task.getContatore().setPause(null);

	while (task.getContatore().getPause() == null && task.getContatore().getStop() == null); {


	    Timer timer = new Timer();

	    TimerTask timerTask = new TimerTask() {
		@Override
		public void run() {
		    
		    // recupero il timestamp di start
		    LocalDateTime start = task.getContatore().getStart();

		    // salvo a db il tempo che scorre nel campo FinalTimeSecondsNow
		    Long FinalTimeMillisec = task.getContatore().FindDifferenceEverySecond(start);

		    repositContatore.save(contatore);
		}
	    };

	    timer.scheduleAtFixedRate(timerTask, 0, 1000);

	    // timer.cancel();
	    
	}

	

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
	    String FinalTime = task.getContatore().findDifference(START, PAUSE);

	    // ad ogni clic la funzione prende gli stop e incrementa di 1
	    task.getContatore().setStop_numbers(task.getContatore().getStop_numbers() + 1);

	    // salvo il contatore
	    repositContatore.save(task.getContatore());

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
	    String FinalTime = task.getContatore().findDifference(START, STOP);

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
