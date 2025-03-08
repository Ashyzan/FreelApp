package com.freelapp.service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.freelapp.controller.ContatoreController;
import com.freelapp.model.Contatore;
import com.freelapp.model.Task;
import com.freelapp.repository.ContatoreRepository;
import com.freelapp.repository.TaskRepository;

@Service
public class ContatoreService {
    @Autowired
    private ContatoreRepository repositContatore;

    @Autowired
    private TaskRepository taskrepository;

    // verifica che il contatore esista
    public void contatoreIsTrue(Task task, Model model) {

	Boolean contatoreIsTrue = false;

	// // se il contatore esiste
	if (task.getContatore().getId() != 0) {

	    contatoreIsTrue = true;

	    // model.addAttribute("finaltime", task.getContatore().getFinaltime());
	    model.addAttribute("contatoreIsTrue", contatoreIsTrue);
	}

    }

// verifica ache il contatore stia andando cioè sia attivo
    public Boolean contatoreIsRun(Task task, Model model) {
	LocalDateTime START = task.getContatore().getStart();
	LocalDateTime STOP = task.getContatore().getStop();
	LocalDateTime RESTART = task.getContatore().getRestart();
	LocalDateTime PAUSE = task.getContatore().getPause();
	Boolean contatoreIsRun = false;

	// CASO 1 : RUN - CONTATORE SENZA PAUSE
	if ((PAUSE == null) && (STOP == null)) {
	    contatoreIsRun = true;
	    model.addAttribute("contatoreIsRun", contatoreIsRun);

	}
	// CASO 2 : RUN - CONTATORE RIAVVIATO
	else if ((RESTART != null) && (RESTART.isAfter(PAUSE))) {
	    contatoreIsRun = true;
	    model.addAttribute("contatoreIsRun", contatoreIsRun);

	}
	// CASO 3: STOP - CONTATORE FERMO START E STOP
	else if ((PAUSE != null) && (PAUSE.isAfter(START)) || (STOP != null)) {
	    contatoreIsRun = false;
	    model.addAttribute("contatoreIsRun", contatoreIsRun);
	}

	// CASO 4: STOP - CONTATORE FERMO RESTART E STOP
	else if ((PAUSE != null) && (PAUSE.isAfter(RESTART)) || (STOP != null)) {
	    contatoreIsRun = false;
	    model.addAttribute("contatoreIsRun", contatoreIsRun);
	}

	return contatoreIsRun;
    }

// function time difference start e stop
    public Long findTime(LocalDateTime start_date, LocalDateTime end_date) {

	Long FinalTimeSeconds = start_date.until(end_date, ChronoUnit.SECONDS);

	// Long hours = FinalTimeSeconds / 3600;
	// Long minutes = (FinalTimeSeconds % 3600) / 60;
	// Long seconds = FinalTimeSeconds1 % 60;
	// finalTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);

	return FinalTimeSeconds;
    }
    
    // metodo che calcola lo STOP a partire dallo Start e dal finaltime
    public LocalDateTime findStop(LocalDateTime start_date, Long oreLavorate) {
    	// .plusSeconds() aggiunge secondi al localdatetime 
    	LocalDateTime stop_datetime = start_date.plusSeconds(oreLavorate);
    	
    	return stop_datetime;
    }

    // METODO controlla che il timer non ecceda le ore consentite altrimenti mette
    // in stop
    public void timeExeed(BindingResult bindingresult, Task task, Model model) {
	// (8766 sono le ore presenti in un anno)

	LocalDateTime START = task.getContatore().getStart();

	if (task.getContatore().getFinaltime() >= 31557600l) {

	    task.getContatore().setStop(LocalDateTime.now());

	    task.getContatore().setFinaltime(31557600l);

	    repositContatore.save(task.getContatore());

	    ObjectError errorTimeExeed = new ObjectError("errorTimeExeed",
		    "Il task eccede le dimensioni consentite ed è stato messo in stop. "
			    + "Avviane uno nuovo per continuare.");

	    bindingresult.addError(errorTimeExeed);

	    String errorTimeExeedmessage = errorTimeExeed.getDefaultMessage();

	    model.addAttribute("errorTimeExeedmessage", errorTimeExeedmessage);

	}
    }

    // metodo che mette in pausa tutti i contatori attivi
    public void pauseOtherTimers() {

	List<Contatore> allContatori = repositContatore.findAll();
	List<Task> listaTask = taskrepository.findAll();

	allContatori.forEach(n -> {
		if(n.getTask().getStato() != "chiuso" || n.getTask().getStato() != "inattivo") {
			
			if ((n.getPause() == null) && (n.getStop() == null)){
				
				n.setPause(LocalDateTime.now());
				Long FinalTimeSeconds = n.getStart().until(n.getPause(), ChronoUnit.SECONDS);
				n.setFinaltime(FinalTimeSeconds);
				
//				listaTask.forEach(task -> {
					n.getTask().setStato("in pausa");
//					taskrepository.save(task);	    			
//				});
				
			} else if((n.getRestart() != null) && (n.getRestart().isAfter(n.getPause()))){
				n.setPause(LocalDateTime.now());
				Long FinalTimeSeconds = n.getRestart().until(n.getPause(), ChronoUnit.SECONDS);
				Long oldFinalTime = n.getFinaltime();
				n.setFinaltime(oldFinalTime + FinalTimeSeconds);
				
//				listaTask.forEach(task -> {	    		
					n.getTask().setStato("in pausa");
//					taskrepository.save(task);
//				});
			}
			repositContatore.save(n);
		}

	});

    }
    public void importContatoreInGet(Model model) {
		if (ContatoreController.contatoreInUso != null) {
			
			Task task =taskrepository.getReferenceById(ContatoreController.taskInUso.getId());
			 contatoreIsTrue(task, model);

			    contatoreIsRun(task, model);
			    
			    boolean contatoreIsRun = contatoreIsRun(task, model);

			    LocalDateTime restartTime = task.getContatore().getRestart();

			    LocalDateTime timeNow = LocalDateTime.now();

			    Long FinalTime = task.getContatore().getFinaltime();
			    
//			    timeExeed(bindingresult, task, model);

			    if (contatoreIsRun == true && restartTime == null) {
			    	
			    	task.getContatore().setFinaltime(findTime( task.getContatore().getStart(), timeNow));
				
//			    	timeExeed(bindingresult, task, model);

			    } else if (contatoreIsRun == true && restartTime != null) {

			    	task.getContatore().setFinaltime(FinalTime + findTime(task.getContatore().getRestart(), timeNow));
				
//			    	timeExeed(bindingresult, task, model);
			    }

			    model.addAttribute("finaltime", task.getContatore().getFinaltime());

			}
	}
}
