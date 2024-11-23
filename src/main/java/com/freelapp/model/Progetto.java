package com.freelapp.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; 

@Entity
@Table(name = "Progetti")
public class Progetto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "DenominazioneProgetto", nullable = false)
 	@NotBlank(message = "La denominazione del progetto non può essere null")
	@NotNull(message = "La denominazione del progetto non può essere null")
	private String name;
	
	@Size(min = 1, max =500 , message="massimo 500 caratteri")
 	@NotBlank(message = "La descrizione del progetto non può essere blank")
	@NotNull(message = "La descrizione del progetto non può essere null")
	@Column(name = "descrizione", nullable = true)
	private String descrizione;
	
	@Column(name = "DataInizio", nullable = false)
	@NotNull(message = "La data di inizio non può essere null")
	private LocalDate dataInizio;
	
	@NotNull(message = "La data di fine non può essere null")
	@Column(name = "DataFine", nullable = false)
	private LocalDate dataFine;
	
	@OneToMany(mappedBy = "progetto")
	private List<Task> elencoTask;

	@ManyToOne
	@JoinColumn(name = "ClienteRif", nullable = false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "UtenteRif", nullable = false)
	private User utente;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}

	public LocalDate getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDate dataFine) {
		this.dataFine = dataFine;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public User getUtente() {
		return utente;
	}
	
	public void setUtente(User utente) {
		this.utente = utente;
	}
	
	public List<Task> getElencoTask() {
		return elencoTask;
	}

	public void setElencoTask(List<Task> elencoTask) {
		this.elencoTask = elencoTask;
	}
}


