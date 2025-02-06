package com.freelapp.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.freelapp.controller.ContatoreController;
import com.freelapp.model.Contatore;
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

 
    
}
