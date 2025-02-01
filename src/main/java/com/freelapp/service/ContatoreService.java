package com.freelapp.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.freelapp.model.Task;
import com.freelapp.repository.ContatoreRepository;
import com.freelapp.repository.TaskRepository;

@Service
public class ContatoreService {
    
    @Autowired
    private TaskRepository repositTask;

    @Autowired
    private ContatoreRepository repositContatore;
    
    // verifica che il contatore esista
public void contatoreIsTrue(Task task, Model model) {
    
    Boolean contatoreIsTrue = false;

	// // se il contatore esiste
	 if (task.getContatore() != null) {

	 	contatoreIsTrue = true;
		
	 	model.addAttribute("finaltime", task.getContatore().getFinaltime());
	 	model.addAttribute("contatoreIsTrue", contatoreIsTrue);
	 	}
	
	}

// verifica ache il contatore stia andando cioè sia attivo
public void contatoreIsRun(Task task, Model model) {
    LocalDateTime START = task.getContatore().getStart();
    LocalDateTime STOP = task.getContatore().getStop();
    LocalDateTime RESTART = task.getContatore().getRestart();
    LocalDateTime PAUSE = task.getContatore().getPause();
    Boolean contatoreIsRun = false;
    Long timenow = 0l;
    
    // CASO 1 : contatore mai messo in pausa
    if (PAUSE == null && STOP == null) {
	contatoreIsRun = true;
	
	Long finaltime = task.getContatore().getFinaltime();

//	timenow = START.until(LocalDateTime.now(), ChronoUnit.SECONDS);
//
//	finaltime = finaltime + timenow;
	
	model.addAttribute("finaltime", finaltime);
 	model.addAttribute("contatoreIsRun", contatoreIsRun);
 	model.addAttribute("timenow", timenow);
 	
    }
 // CASO 2 : contatore riavviato
    else if (RESTART != null && RESTART.isAfter(PAUSE)) {
		contatoreIsRun = true;
		
		Long finaltime = task.getContatore().getFinaltime();

		timenow = RESTART.until(LocalDateTime.now(), ChronoUnit.SECONDS);

		finaltime = finaltime + timenow;
		
		model.addAttribute("finaltime", finaltime);
	 	model.addAttribute("contatoreIsRun", contatoreIsRun);
	 	model.addAttribute("timenow", timenow);
	 	
	    }
    // SE IL CONTATORE NON STA ANDANDO
    else if ((PAUSE != null) && (PAUSE.isAfter(RESTART)) || (STOP != null)) {
	contatoreIsRun = false;
	Long finaltime = task.getContatore().getFinaltime();
	model.addAttribute("finaltime", finaltime);
 	model.addAttribute("contatoreIsRun", contatoreIsRun);
    }
    
}

// function time difference start e stop
public Long findTime(LocalDateTime start_date, LocalDateTime end_date) {
    	
	Long FinalTimeSeconds = start_date.until(end_date, ChronoUnit.SECONDS);
	
		//Long hours = FinalTimeSeconds / 3600;
		//Long minutes = (FinalTimeSeconds % 3600) / 60;
		//Long seconds = FinalTimeSeconds1 % 60;
		//finalTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
	
	return FinalTimeSeconds;
}


}
