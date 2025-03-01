package com.freelapp.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
public class OreLavorateController {
    
    @Autowired
    private TaskRepository repositTask;
    
    @Autowired
    private ContatoreRepository repositContatore;
    
    @Autowired
    private ContatoreService contatoreservice;
    
    //inserimento con input da parte dell'utente di localDateTime start e FinalTime (conversione minuti e ore in secondi)
    
    // creare anche il model di oreLavorate come classe a se stante

    private Date dataOre; // data inserita dall'utente
    private Time oreLavorate; // le ore corrisondenti al finaltime inserite dall'utente
    private LocalDateTime startOre; // paramentro da salvare a DB nello start contatore
    
    // metodo che converte una data in localdatetime
    public LocalDateTime convertToLocalDateTimeViaInstant(Date dataOre) {
        return dataOre.toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDateTime();
    }
    
    // metodo che converte in secondi il Time che arriva dall'utente per salvarlo come finaltime
    public Long FinalOre(LocalTime time) {

    	// recupero i minuti del time
    	int Minuti = time.getMinute();
    	// trasformo da int a long
    	Long minuti = (long) Minuti;
    	
    	// recupero le ore del time
	    int Ore = time.getHour();
	    // trasformo da int a long
    	Long ore = (long) Ore;
    	
	    int intTen = 10;
	    Long longTen = (long) intTen;
	    
     Long secondsOre = ore * 3600;
 	 Long secondsMinuti = minuti * 60;
     Long finalTime = secondsOre + secondsMinuti;
    return finalTime;
    }
    
    // metodo che calcola lo STOP a partire dallo Start e dal finaltime
    public LocalDateTime findStop(LocalDateTime start_date, Long oreLavorate) {
    	// .plusSeconds() aggiunge secondi al localdatetime 
    	LocalDateTime stop_datetime = start_date.plusSeconds(oreLavorate);
    	
    	return stop_datetime;
    }

    // ricevo i parametri dal modello e salvo i dati del contatore
    @PostMapping("/orelavorate/{id}")
    public String gestioneTimer(@PathVariable("id") Integer taskId, @ModelAttribute("contatore") Contatore contatore,Date data,
    		LocalTime time, Model model, BindingResult bindingresult) {
	// richiamo l'id del task
		Task task = repositTask.getReferenceById(taskId);
		
		//LocalDateTime STOP = contatoreservice.findStop(null, null);
		
		Date date = data;
	    LocalTime Time = time; 
	    Long finalTime =  FinalOre(Time);
	    LocalDateTime START = convertToLocalDateTimeViaInstant(date);
	    LocalDateTime STOP = findStop(START, finalTime);
	     
	    task.getContatore().setFinaltime(finalTime); 
	    task.getContatore().setStart(START);
	    task.getContatore().setStop(STOP);
		
		//model.addAttribute("orelavorate", orelavorate);
		
		repositContatore.save(task.getContatore());
			
		return "/orelavorate";
	
    }
    
  
}
