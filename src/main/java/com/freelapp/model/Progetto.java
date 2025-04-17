package com.freelapp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size; 

@Entity
@Table(name = "Progetti")
public class Progetto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "progetto_id")
	private int id;
	
	@Column(name = "DenominazioneProgetto", nullable = false)
 	@NotBlank(message = "Inserimento denominazione obbligatorio")
	@NotNull(message = "Inserimento denominazione obbligatorio")
	private String name;
	
	@Size(min = 1, max =500 , message="massimo 500 caratteri")
 	@NotBlank(message = "Inserimento descrizione obbligatorio")
	@NotNull(message = "Inserimento descrizione obbligatorio")
	@Column(name = "descrizione", nullable = false)
	private String descrizione;
	
	@Column(name = "DataInizio", nullable = false)
	@DateTimeFormat(pattern= "yyyy-MM-dd")
	@NotNull(message = "Data di inizio obbligatoria")
	private LocalDate dataInizio = LocalDate.now();
	
	@Column(name = "archivia")
	private Boolean archivia = false;
	
	@DateTimeFormat(pattern= "yyyy-MM-dd")
	@Column(name = "dataModifica")
	private LocalDateTime dataModifica;

	@DateTimeFormat(pattern= "yyyy-MM-dd")
	@Column(name = "DataFine")
	private LocalDate dataFine;
	
	@Column(name = "Tipologia", nullable = false)
	@NotNull(message = "Scelta tipologia obbligatoria")
	private String tipologia;
	
	@Column(name = "Budget_monetario")
	private double budgetMonetario = 0.00;
	
	@Column(name = "Tariffa_oraria")
	private double tariffaOraria = 0.00;
	
	@Column(name = "Budget_ore")
	private int budgetOre = 0;
	
	@Column(name = "Guadagno_effettivo")
	private double guadagnoEffettivo;
	
	@OneToMany(mappedBy = "progetto")
	@JsonManagedReference
	@JsonIgnore
	private List<Task> elencoTask;

	@NotNull(message = "Scelta cliente obbligatoria")
	@ManyToOne
	@JoinColumn(name = "ClienteRif", nullable = false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "UtenteRif", nullable = false)
	private User utente;
	
	
	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public double getBudgetMonetario() {
		return budgetMonetario;
	}

	public void setBudgetMonetario(double budgetMonetario) {
		this.budgetMonetario = budgetMonetario;
	}

	public double getTariffaOraria() {
		return tariffaOraria;
	}

	public void setTariffaOraria(double tariffaOraria) {
		this.tariffaOraria = tariffaOraria;
	}

	public int getBudgetOre() {
		return budgetOre;
	}

	public void setBudgetOre(int budgetOre) {
		this.budgetOre = budgetOre;
	}

	public double getGuadagnoEffettivo() {
		return guadagnoEffettivo;
	}

	public void setGuadagnoEffettivo(double guadagnoEffettivo) {
		this.guadagnoEffettivo = guadagnoEffettivo;
	}

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
	
	public LocalDateTime getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(LocalDateTime localDateTime) {
		this.dataModifica = localDateTime;
	}

	public Boolean getArchivia() {
		return archivia;
	}

	public void setArchivia(Boolean archivia) {
		this.archivia = archivia;
	}
}


