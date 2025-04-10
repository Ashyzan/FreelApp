package com.freelapp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tasks")
public class Task{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
	private int id;
	
	@Column(name = "DenominazioneTask", nullable = false)
 	@NotBlank(message = "La denominazione del task è obbligatoria")
	@NotNull(message = "La denominazione del task è obbligatoria")
	private String name;
	
	@Column(name = "Descrizione", nullable = false)
	@NotBlank(message = "La descrizione del task è obbligatoria")
	@NotNull(message = "La descrizione del task è obbligatoria")
	@Size(max =500 , message="massimo 500 caratteri")
	private String descrizione;
		
	@Column(name = "DataInizio", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")

	@NotNull(message = "Inserimento data di inizio obbligatorio")
	private LocalDate dataInizio = LocalDate.now();
	
	@Column(name = "DataChiusuraStimata")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataChiusuraStimata;
	
	@Column(name = "DataChiusuraDefinitiva")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataChiusuraDefinitiva;
	
	@Column(name = "DataModifica")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime dataModifica ;

	@ManyToOne
	@NotNull(message = "La scelta del progetto è obbligatoria")
	@JoinColumn(name = "ProgettoRif", nullable = false)
	private Progetto progetto;
	
	@Column(name = "stato")
	private String stato = "inattivo";
	
	@OneToOne(mappedBy = "task", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Contatore contatore;

	
	// getter setter
	
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
	    return dataChiusuraDefinitiva;
	}

	public void setDataChiusuraDefinitiva(LocalDate dataChiusuraDefinitiva) {
	    this.dataChiusuraDefinitiva = dataChiusuraDefinitiva;
	}

	public Progetto getProgetto() {
	    return progetto;
	}

	public void setProgetto(Progetto progetto) {
	    this.progetto = progetto;
	}

	public String getStato() {
	    return stato;
	}


	public void setStato(String stato) {
	    this.stato = stato;
	}

	public Contatore getContatore() {
	    return contatore;
	}

	public void setContatore(Contatore contatore) {
	    this.contatore = contatore;
	    this.contatore.setTask(this); // setting the parent class as the value for the child instance
	}
	
	public LocalDateTime getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(LocalDateTime dataModifica) {
		this.dataModifica = dataModifica;
		
	}
	
	

}



