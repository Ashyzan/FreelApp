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
    
    // verific ache il contatore esista
public void contatoreIsTrue(Task task, Model model) {
    
    Boolean contatoreIsTrue = false;

	// // se il contatore esiste
	 if (task.getContatore() != null) {

	 	contatoreIsTrue = true;
		
	 	model.addAttribute("finaltime", task.getContatore().getFinaltime());
	 	model.addAttribute("contatoreIsTrue", contatoreIsTrue);
	 	}
	
	}

// verifica ache il contatore stia andando, sia attivo
public void contatoreIsRun(Task task, Model model) {
    LocalDateTime START = task.getContatore().getStart();
    LocalDateTime RESTART = task.getContatore().getRestart();
    LocalDateTime PAUSE = task.getContatore().getPause();
    Boolean contatoreIsRun = false;
    
    if (START.isAfter(PAUSE)) {
	contatoreIsRun = true;
	
	Long finaltime = task.getContatore().getFinaltime();

	Long timenow = START.until(LocalDateTime.now(), ChronoUnit.SECONDS);

	
	model.addAttribute("finaltime", finaltime);
 	model.addAttribute("contatoreIsRun", contatoreIsRun);
 	model.addAttribute("timenow", timenow);
 	
    }
    
    else if (RESTART.isAfter(PAUSE)) {
		contatoreIsRun = true;
		
		Long finaltime = task.getContatore().getFinaltime();

		Long timenow = RESTART.until(LocalDateTime.now(), ChronoUnit.SECONDS);

		
		model.addAttribute("finaltime", finaltime);
	 	model.addAttribute("contatoreIsRun", contatoreIsRun);
	 	model.addAttribute("timenow", timenow);
	 	
	    }
    
}

// function time difference start e primo stop
public Long findTime(LocalDateTime start_date, LocalDateTime end_date) {
	//task.getContatore();
    
    	
	Long FinalTimeSeconds1 = start_date.until(end_date, ChronoUnit.SECONDS);
	
		//Long hours = FinalTimeSeconds / 3600;
		//Long minutes = (FinalTimeSeconds % 3600) / 60;
		//Long seconds = FinalTimeSeconds1 % 60;

		//finalTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
	
	return FinalTimeSeconds1;
}

// calcola il tempo totale trascorso tra il restart e la pausa

public Long findTimeRestart(LocalDateTime restart_date, LocalDateTime end_date, Task task) {
	
	    
	    Long finalTime  = restart_date.until(end_date, ChronoUnit.SECONDS);
	    
	    Long prevSec = task.getContatore().getFinaltime();
	    
	    Long FinalTimeSeconds2 = finalTime + prevSec;
	
	
	return FinalTimeSeconds2;
	
}


}
