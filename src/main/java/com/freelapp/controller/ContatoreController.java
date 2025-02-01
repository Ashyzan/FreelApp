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
import com.freelapp.service.ContatoreService;

@Controller
public class ContatoreController {

    @Autowired
    private ContatoreService contatoreservice;

    @Autowired
    private TaskRepository repositTask;

    @Autowired
    private ContatoreRepository repositContatore;

    @GetMapping("/Contatore/timer/{id}")
    public String gestioneTimer(@PathVariable("id") Integer taskId, @ModelAttribute("contatore") Contatore contatore,
	    Model model) {

	// richiamo l'id del task
	Task task = repositTask.getReferenceById(taskId);

	// parte per javascript: serve per collegare il finaltime da java a javascript
	// sul frontend
	contatoreservice.contatoreIsTrue(task, model);

	if (task.getContatore() != null) {

	    contatoreservice.contatoreIsRun(task, model);
	}

	model.addAttribute("contatore", contatore);

	return "/Contatore/timer";
    }

    @PostMapping("/start/{id}")
    public String startContatore(@PathVariable("id") Integer taskId, @ModelAttribute("contatore") Contatore contatore,
	    Model model) {

	// richiamo l'id del task
	Task task = repositTask.getReferenceById(taskId);

	// se il contatore esiste, restituiscilo al modello
	if ((task.getContatore() != null) && (task.getContatore().getStop() == null)
		&& (task.getContatore().getStart() != null)) {

	    // imposta il valore di restart
	    task.getContatore().setRestart(LocalDateTime.now());

	    // parte per javascript: serve per collegare il finaltime da java a javascript
	    // sul frontend
	    contatoreservice.contatoreIsTrue(task, model);
	    contatoreservice.contatoreIsRun(task, model);

	    // collego nel modello html il task e il contatore
	    model.addAttribute("task", task);
	    model.addAttribute("contatore", contatore);
	}
	// verifico che il contatore ha lo stop
	else if ((task.getContatore() != null) && (task.getContatore().getStop() != null)) {
	    // se il contatore ha lo stop, non fare nulla
	}
	// verifico se il contatore è stato resettato
	else if ((task.getContatore() != null) && (task.getContatore().getStart() == null)) {
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
	    contatore.setFinaltime(0l);

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
	if ((contatore != null) && (contatore.getStop() == null) && (contatore.getStart() != null)) {

	    // parte per javascript: serve per collegare il finaltime da java a javascript
	    // sul frontend
	    contatoreservice.contatoreIsTrue(task, model);
	    contatoreservice.contatoreIsRun(task, model);

	    // recupero timestamp di inizio e pausa
	    LocalDateTime PAUSE = task.getContatore().getPause();
	    LocalDateTime START = task.getContatore().getStart();
	    LocalDateTime RESTART = task.getContatore().getRestart();

	    // se la pausa non esiste
	    if (PAUSE == null) {

		PAUSE = LocalDateTime.now();

		contatore.setPause(PAUSE);

		// metodo che calcola la differenza fra i due timestamp
		Long FinalTime = contatoreservice.findTime(START, PAUSE);

		// imposto il finaltime differenza fra stop e pausa - tipo Long
		contatore.setFinaltime(FinalTime);

		// ad ogni clic la funzione prende gli stop e incrementa di 1
		contatore.setStop_numbers(contatore.getStop_numbers() + 1);

		// salvo il contatore
		repositContatore.save(contatore);

	    }

	    // se la pausa esiste già
	    else if (PAUSE != null) {

		// imposto comunque una nuova pausa
		PAUSE = LocalDateTime.now();

		contatore.setPause(PAUSE);

		// metodo che calcola il tempo trascorso partendo dal restart e la nuova pausa
		Long FinalTime = contatore.getFinaltime() + contatoreservice.findTime(RESTART, PAUSE);

		// imposto il finaltime differenza fra stop e pausa - tipo Long
		contatore.setFinaltime(FinalTime);

		// ad ogni clic la funzione prende gli stop e incrementa di 1
		contatore.setStop_numbers(contatore.getStop_numbers() + 1);

		// salvo il contatore
		repositContatore.save(contatore);
	    }

	}

	// verifico che il contatore non sia stoppato, se stoppato non faccio nulla
	else if ((task.getContatore() != null) && (task.getContatore().getStop() != null)) {
	}

	// else se il contatore non esiste schiacciare la pausa non esegue nessun
	// comando

	return "/Contatore/timer";
    }

    @PostMapping("/Contatore/stop/{id}")
    public String stopContatore(@PathVariable("id") Integer taskId, Model model) {
	// richiamo l'id del task
	Task task = repositTask.getReferenceById(taskId);

	// parte per javascript: serve per collegare il finaltime da java a javascript
	// sul frontend
	contatoreservice.contatoreIsTrue(task, model);
	contatoreservice.contatoreIsRun(task, model);

	// verifica che il contatore esista
	if (task.getContatore() != null) {

	    // setto le variabili
	    LocalDateTime STOP = task.getContatore().getStop();
	    LocalDateTime START = task.getContatore().getStart();
	    LocalDateTime RESTART = task.getContatore().getRestart();
	    LocalDateTime PAUSE = task.getContatore().getPause();
	    Contatore contatore = task.getContatore();

	    // IL CONTATORE ESISTE E NON È RESETTATO

	    if ((STOP == null) && (START != null)) {

		// CASO 1: IL CONTATORE è FERMO e RESTART è NULLO
		if ((PAUSE != null) && (RESTART == null)) {

		    // eseguo il TIMESTAMP dello STOP e chiudo il contatore
		    STOP = LocalDateTime.now();

		    contatore.setStop(STOP);

		    repositContatore.save(task.getContatore());
		}

		// CASO 2: IL CONTATORE è FERMO E RESTART è prima della PAUSA
		else if ((PAUSE != null) && (RESTART.isBefore(PAUSE))) {

		    // eseguo il TIMESTAMP dello STOP e chiudo il contatore
		    STOP = LocalDateTime.now();

		    contatore.setStop(STOP);

		    repositContatore.save(task.getContatore());
		}

		// CASO 3: IL CONTATORE è ATTIVO E NON È MAI STATO FERMATO
		else if ((PAUSE == null) && (RESTART == null)) {

		    // eseguo il TIMESTAMP dello STOP e chiudo il contatore
		    STOP = LocalDateTime.now();
		    // setto lo stop a db
		    contatore.setStop(STOP);
		    // calcolo il tempo trascorso
		    Long FinalTime1 = contatoreservice.findTime(START, STOP);
		    // imposto il finaltime
		    contatore.setFinaltime(FinalTime1);
		    // salvo il contatore
		    repositContatore.save(contatore);
		}

		// CASO 4: IL CONTATORE È ATTIVO ED È STATO PIU VOLTE AVVIATO
		else if ((RESTART != null) && (RESTART.isAfter(PAUSE))) {

		    // eseguo il TIMESTAMP dello STOP e chiudo il contatore
		    STOP = LocalDateTime.now();

		    contatore.setStop(STOP);

		    // metodo che calcola la differenza fra i due timestamp
		    Long lastTime = contatoreservice.findTime(RESTART, STOP);

		    Long prevSec = task.getContatore().getFinaltime();

		    Long FinalTime = lastTime + prevSec;

		    // imposto il finaltime differenza fra stop e pausa - tipo Long
		    contatore.setFinaltime(FinalTime);

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
	    task.getContatore().setRestart(null);

	    // salvo il contatore
	    repositContatore.save(task.getContatore());

	}

	return "/Contatore/timer";
    }
}
