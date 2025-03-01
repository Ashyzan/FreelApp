package com.freelapp.model;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

public class OreLavorate {
	
	private Date dataOre; // data inserita dall'utente
    private Time oreLavorate; // le ore corrisondenti al finaltime inserite dall'utente
    private LocalDateTime startOre; // paramentro da salvare a DB nello start contatore

}
