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
    
public void contatoreIsActive(Task task, Model model) {
    
    Boolean contatoreIsTrue = false;

	// // se il contatore esiste
	 if (task.getContatore() != null) {

	 	contatoreIsTrue = true;
		
	 	model.addAttribute("finaltime", task.getContatore().getFinaltime());
	 	model.addAttribute("contatoreIsTrue", contatoreIsTrue);
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

//calcola il tempo totale trascorso fra il restart e lo stop

//public Long findTimeRestartStop(LocalDateTime restart_date, LocalDateTime stop_date, Task task) {
//	
//
//	    restart_date = task.getContatore().getRestart();
//	    stop_date = task.getContatore().getStop();
//	    
//	    Long finalTime  = restart_date.until(stop_date, ChronoUnit.SECONDS);
//	    
//	   // Long finalTimeSeconds = finalTime % 60;
//	    
//	    Long prevSec = task.getContatore().getFinaltime();
//	    
//	    Long FinalTimeSeconds3 = finalTime + prevSec;
//	
//	
//	return FinalTimeSeconds3;
//	
//}

}
