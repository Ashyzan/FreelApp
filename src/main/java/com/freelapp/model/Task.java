package com.freelapp.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; 
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "tasks")
public class Task{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "DenominazioneTask", nullable = false)
 	@NotBlank(message = "La denominazione del task non può essere null")
	@NotNull(message = "La denominazione del task non può essere null")
	private String name;
	
	@Column(name = "Descrizione", nullable = false)
	@Size(min = 1, max =500 , message="massimo 500 caratteri")
 	@NotBlank(message = "La descrizione del progetto non può essere blank")
	@NotNull(message = "La descrizione del progetto non può essere null")
	private String descrizione;
		
	@Column(name = "DataInizio", nullable = false)
	@NotNull(message = "La data di inizio non può essere null")
	private LocalDate dataInizio;
	
	@NotNull(message = "La data di chiusura stimata non può essere null")
	@Column(name = "DataChiusuraStimata", nullable = false)
	private LocalDate dataChiusuraStimata;
	
	//@NotNull(message = "La data di chiusura definitiva non può essere null")
	@Column(name = "DataChiusuraDefinitiva", nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataChiusuraDefinitiva;

	//@OneToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "contatore_id", referencedColumnName = "id")
	//private Contatore contatore;
	
	@OneToMany(mappedBy = "task")
	private List<Contatore> contatori;

	@ManyToOne
	@JoinColumn(name = "ProgettoRif", nullable = false)
	private Progetto progetto;
	
	@ManyToOne
	@JoinColumn(name = "StatoRif", nullable = false)
	private Stato stato;
	
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

	public LocalDate getDataChiusuraStimata() {
		return dataChiusuraStimata;
	}

	public void setDataChiusuraStimata(LocalDate dataChiusuraStimata) {
		this.dataChiusuraStimata = dataChiusuraStimata;
	}
	
	public LocalDate getDataChiusuraDefinitiva() {
		return dataChiusuraStimata;
	}

	public void setDataChiusuraDefinitiva(LocalDate dataChiusuraStimata) {
		this.dataChiusuraStimata = dataChiusuraStimata;
	}

	public Progetto getProgetto() {
		return progetto;
	}

	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
	}
	
	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}
	
	public List<Contatore> getContatori() {
		return contatori;
	}

	public void setContatori(List<Contatore> contatori) {
		this.contatori = contatori;
	}
}



