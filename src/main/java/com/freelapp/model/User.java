package com.freelapp.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Utenti")
public class User {

//	public enum Role {
//		STAFF,
//		USER,
//		ADMIN
//	}

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

	@Column(name = "email", nullable = false, unique = true)
	@NotNull(message = "L'email non può essere null")
	@NotBlank(message = "L'email non può essere blank ")
	private String email;
	
	@NotBlank(message = "Inserimento password obbligatorio")
	@Column(name = "password")
	private String password;
	
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
	
	@OneToMany(mappedBy = "utente")
	private List<Tickets> tickets;

//	@Enumerated(EnumType.STRING)
//	@Column(name = "role", nullable = false)
//	private Role role = Role.USER;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Roles> roles;

	@Column(name = "created_at", nullable = true)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = true)
	private LocalDateTime updatedAt;
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getId() {
		return id;
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

//	public Role getRole() {
//		return role;
//	}
//
//	public void setRole(Role role) {
//		this.role = role;
//	}
	

	public String getTelefono() {
		return telefono;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	public void setId(int id) {
		this.id = id;
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

	public List<Tickets> getTickets() {
		return tickets;
	}

	public void setTickets(List<Tickets> tickets) {
		this.tickets = tickets;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	@PrePersist
	protected void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.updatedAt = now;
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}

