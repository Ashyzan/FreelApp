package com.freelapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Statistica_progetti")
public class StatisticaProgetto {

	@Id
	@Column(name = "progetto_id")
	private int id;
	
	@Column(name = "Tipologia", nullable = false)
	private String tipologia;
	
	@Column(name = "Budget")
	private double budget;
	
	@Column(name = "Tariffa_oraria")
	private double tariffaOraria;
	
	@Column(name = "Ore_massime")
	private int oreMassime;
	
	@Column(name = "Guadagno_effettivo")
	private double guadagnoEffettivo;
	
	@OneToOne
    @MapsId
    @JoinColumn(name = "progetto_id")
    private Progetto progetto;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public double getTariffaOraria() {
		return tariffaOraria;
	}

	public void setTariffaOraria(double tariffaOraria) {
		this.tariffaOraria = tariffaOraria;
	}

	public int getOreMassime() {
		return oreMassime;
	}

	public void setOreMassime(int oreMassime) {
		this.oreMassime = oreMassime;
	}

	public double getGuadagnoEffettivo() {
		return guadagnoEffettivo;
	}

	public void setGuadagnoEffettivo(double guadagnoEffettivo) {
		this.guadagnoEffettivo = guadagnoEffettivo;
	}

	public Progetto getProgetto() {
		return progetto;
	}

	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
	}
	
	
	
}
