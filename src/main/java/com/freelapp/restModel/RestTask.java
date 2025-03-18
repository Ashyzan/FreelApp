package com.freelapp.restModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RestTask {

	private String nome;
	
	private String progetto;
	
	private Integer progettoId;
	
	private String cliente;
	
	private Integer clienteId;
	
	private String logoCliente;

	private LocalDateTime stop;
	
	private Long finalTime;
	
	private Integer taskAttualmenteInUso;
	
	private Integer id;
	
	private String stato;
	
	public RestTask(String nome, String progetto, Integer progettoId, String cliente, 
			Integer clienteId, String logoCliente, Long finalTime, Integer taskAttualmenteInUso, 
			Integer id, String stato) {
		super();
		this.nome = nome;
		this.progetto = progetto;
		this.progettoId = progettoId;
		this.cliente = cliente;
		this.clienteId = clienteId;
		this.logoCliente = logoCliente;
		this.finalTime = finalTime;
		this.taskAttualmenteInUso = taskAttualmenteInUso;
		this.id = id;
		this.stato = stato;
		
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
	
	public Integer getProgettoId() {
		return progettoId;
	}

	public void setProgettoId(Integer progettoId) {
		this.progettoId = progettoId;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public String getLogoCliente() {
		return logoCliente;
	}

	public void setLogoCliente(String logoCliente) {
		this.logoCliente = logoCliente;
	}

	public Long getFinalTime() {
		return finalTime;
	}

	public void setFinalTime(Long finalTime) {
		this.finalTime = finalTime;
	}

	public Integer getTaskAttualmenteInUso() {
		return taskAttualmenteInUso;
	}

	public void setTaskAttualmenteInUso(Integer taskAttualmenteInUso) {
		this.taskAttualmenteInUso = taskAttualmenteInUso;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
	
}
