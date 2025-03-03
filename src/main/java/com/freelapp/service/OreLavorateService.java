package com.freelapp.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import org.springframework.stereotype.Service;

@Service
public class OreLavorateService {
	
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

}
