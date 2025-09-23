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
@Table(name = "Utenti")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "NomeUtente", nullable = false)
	@NotNull(message = "Il nome non può essere null")
	@NotBlank(message = "Il nome non può essere blank ")
	private String name;
	
	@Column(name = "Cognome", nullable = false)
	@NotNull(message = "Il cognome non può essere null")
	@NotBlank(message = "Il cognome non può essere blank ")
	private String cognome;

	@Column(name = "email", nullable = false)
	@NotNull(message = "L'email non può essere null")
	@NotBlank(message = "L'email non può essere blank ")
	private String email;
	
	@Column(name = "Telefono", nullable = false)
	@NotNull(message = "Il telefono non può essere null")
	private String telefono;
	
	@Column(name = "PartitaIva", nullable = false)
	@NotNull(message = "La partitaIva non può essere null")
	@NotBlank(message = "La partitaIva non può essere blank ")
	private String partitaIva;
	
	@Column(name = "RAL", nullable = false)
	@NotNull(message = "Il RAL non può essere null")
	private int ral;
	
	@OneToMany(mappedBy = "utente")
	private List<Progetto> progetti;
	
	@OneToMany(mappedBy = "tickets")
	private List<Tickets> tickets;
		
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
	
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
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

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	
	public int getRal() {
		return ral;
	}

	public void setRal(int ral) {
		this.ral= ral;
	}
	

	public List<Progetto> getProgetti() {
		return progetti;
	}

	public void setProgetti(List<Progetto> progetti) {
		this.progetti = progetti;
	}
}

