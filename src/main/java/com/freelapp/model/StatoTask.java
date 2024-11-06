package com.freelapp.model;

public enum StatoTask {
	IN_CORSO(0),
	IN_PAUSA(1),
	COMPLETATO(2);
	
	int numerazione;
	
	private StatoTask (int numerazione){
		this.numerazione = numerazione;
		 }
	
	 public int getNumerazione() {
	        return numerazione;
	    }
	
	
}

