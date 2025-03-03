package com.freelapp.restModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RestTask {

	private String nome;
	
	private String progetto;
	
	private String cliente;
	
	private String logoCliente;
	
	private LocalDate chiusuraStimata;

	private LocalDateTime stop;
	
	public RestTask(String nome, String progetto, String cliente, String logoCliente, LocalDate chiusuraStimata) {
		super();
		this.nome = nome;
		this.progetto = progetto;
		this.cliente = cliente;
		this.logoCliente = logoCliente;
		this.chiusuraStimata = chiusuraStimata;
		//this.stop = stop;
	}

	public LocalDateTime getStop() {
		return stop;
	}

	public void setStop(LocalDateTime stop) {
		this.stop = stop;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProgetto() {
		return progetto;
	}

	public void setProgetto(String progetto) {
		this.progetto = progetto;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getLogoCliente() {
		return logoCliente;
	}

	public void setLogoCliente(String logoCliente) {
		this.logoCliente = logoCliente;
	}

	public LocalDate getChiusuraStimata() {
		return chiusuraStimata;
	}

	public void setChiusuraStimata(LocalDate chiusuraStimata) {
		this.chiusuraStimata = chiusuraStimata;
	}
	
}
