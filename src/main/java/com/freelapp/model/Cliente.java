package com.freelapp.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Clienti")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "nickname", nullable = false)
	@NotNull(message = "Inserimento nome di etichetta obbligatorio")
	@NotBlank(message = "Inserimento nome di etichetta obbligatorio")
	private String labelCliente;
	
	@Column(name = "tipologia")
	private String tipologia;
	
	@Column(name = "ulrLogo")
	private String urlLogo;
	
	@Column(name = "NomeCliente")
//	@NotNull(message = "Inserimento del nome obbligatorio")
//	@NotBlank(message = "Inserimento del nome obbligatorio")
	private String name;
	
	@Column(name = "CognomeCliente")
	private String surname;

	@Column(name = "RagioneSociale")
//	@NotNull(message = "Inserimento ragione sociale obbligatorio")
//	@NotBlank(message = "Inserimento ragione sociale obbligatorio")
	private String ragioneSociale;
	
	@Column(name = "PartitaIva")
//	@NotNull(message = "Inserimento partita iva obbligatorio")
//	@NotBlank(message = "Inserimento partita iva obbligatorio")
	private String partitaIva;
	
	@Column(name = "codFiscale")
	private String codiceFiscale;
	
	@Column(name = "email", nullable = false)
	@NotNull(message = "Inserimento email obbligatorio")
	@NotBlank(message = "Inserimento email obbligatorio")
	private String email;
	
	@Column(name = "pec")
	private String pec;
	
	@Column(name = "Telefono", nullable = false)
	@NotNull(message = "Inserimento numero telefono obbligatorio")
	@NotBlank(message = "Inserimento numero telefono obbligatorio")
	private String telefono;
	
	@Column(name = "sitoWeb")
	private String sito;
	
	@Column(name = "Indirizzo")
//	@NotNull(message = "Inserimento indirizzo obbligatorio")
//	@NotBlank(message = "Inserimento indirizzo obbligatorio")
	private String indirizzo;
	
	@Column(name = "civico")
	private String civico;
	
	@Column(name = "Città")
//	@NotNull(message = "Inserimento città obbligatorio")
//	@NotBlank(message = "Inserimento città obbligatorio")
	private String city;
	
	@Column(name = "cap", length = 5 )
	private String cap;
	
	@Column(name = "nazionalità", nullable = false)
	@NotNull(message = "Scelta nazionalità obbligatoria")
	@NotBlank(message = "Scelta nazionalità obbligatoria")
	private String nazionalita;
	
	@Column(name = "nomeContatto")
	private String nameContatto;
	
	@Column(name = "cognomeContatto")
	private String surnameContatto;
	
	
	
	@OneToMany(mappedBy = "cliente")
	private List<Progetto> progetti;



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getLabelCliente() {
		return labelCliente;
	}



	public void setLabelCliente(String labelCliente) {
		this.labelCliente = labelCliente;
	}



	public String getTipologia() {
		return tipologia;
	}



	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}



	public String getUrlLogo() {
		return urlLogo;
	}



	public void setUrlLogo(String urlLogo) {
		this.urlLogo = urlLogo;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getSurname() {
		return surname;
	}



	public void setSurname(String surname) {
		this.surname = surname;
	}



	public String getRagioneSociale() {
		return ragioneSociale;
	}



	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}



	public String getPartitaIva() {
		return partitaIva;
	}



	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}



	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getTelefono() {
		return telefono;
	}



	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	public String getIndirizzo() {
		return indirizzo;
	}



	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}



	public String getCivico() {
		return civico;
	}



	public void setCivico(String civico) {
		this.civico = civico;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getCap() {
		return cap;
	}



	public void setCap(String cap) {
		this.cap = cap;
	}



	public String getNazionalita() {
		return nazionalita;
	}



	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}



	public List<Progetto> getProgetti() {
		return progetti;
	}



	public void setProgetti(List<Progetto> progetti) {
		this.progetti = progetti;
	}



	public String getSito() {
		return sito;
	}



	public void setSito(String sito) {
		this.sito = sito;
	}



	public String getNameContatto() {
		return nameContatto;
	}



	public void setNameContatto(String nameContatto) {
		this.nameContatto = nameContatto;
	}



	public String getSurnameContatto() {
		return surnameContatto;
	}



	public void setSurnameContatto(String surnameContatto) {
		this.surnameContatto = surnameContatto;
	}



	public String getPec() {
		return pec;
	}



	public void setPec(String pec) {
		this.pec = pec;
	}
		
	
	
}

