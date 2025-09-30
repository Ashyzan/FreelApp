package com.freelapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "user_fiscal_data")
public class UserFiscalData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;
	
	@Column(name = "partita_iva", length = 20)
	private String partitaIva;
	
	@Column(name = "codice_fiscale", length = 20)
	private String codiceFiscale;
	
	@Column(name = "pec")
	private String pec;
	
	@Column(name = "sdi", length = 7)
	private String sdi;
	
	@Column(name = "denominazione")
	private String denominazione;
	
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }
	public String getPartitaIva() { return partitaIva; }
	public void setPartitaIva(String partitaIva) { this.partitaIva = partitaIva; }
	public String getCodiceFiscale() { return codiceFiscale; }
	public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }
	public String getPec() { return pec; }
	public void setPec(String pec) { this.pec = pec; }
	public String getSdi() { return sdi; }
	public void setSdi(String sdi) { this.sdi = sdi; }
	public String getDenominazione() { return denominazione; }
	public void setDenominazione(String denominazione) { this.denominazione = denominazione; }
}
