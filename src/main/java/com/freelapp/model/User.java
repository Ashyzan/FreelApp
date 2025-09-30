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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Utenti")
public class User {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Email(message = "Formato email non valido")
	@NotBlank(message = "L'email è obbligatoria")
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@NotBlank(message = "La password è obbligatoria")
	@Size(min = 8, message = "La password deve avere almeno 8 caratteri")
	@Pattern(
	    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$",
	    message = "La password deve contenere almeno una maiuscola, una minuscola, un numero e un carattere speciale"
	)
	@Column(name = "password", nullable = false)
	private String password;


	@OneToMany(mappedBy = "utente")
	private List<Progetto> progetti;
	
	@OneToMany(mappedBy = "utente")
	private List<Tickets> tickets;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", nullable = false)
	private Roles role;
	

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private UserProfile profile;
	
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private UserFiscalData fiscal;

	@Column(name = "created_at", nullable = true)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = true)
	private LocalDateTime updatedAt;
	
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
	public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
	public int getId() { return id; }
	public List<Progetto> getProgetti() { return progetti; }
	public void setProgetti(List<Progetto> progetti) { this.progetti = progetti; }
	public List<Tickets> getTickets() { return tickets; }
	public void setTickets(List<Tickets> tickets) { this.tickets = tickets; }
	public LocalDateTime getCreatedAt() { return createdAt; }
	public LocalDateTime getUpdatedAt() { return updatedAt; }
	public Roles getRole() { return role; }
	public void setRole(Roles role) { this.role = role; }
	public UserProfile getProfile() { return profile; }
	public void setProfile(UserProfile profile) { this.profile = profile; }
	public UserFiscalData getFiscal() { return fiscal; }
	public void setFiscal(UserFiscalData fiscal) { this.fiscal = fiscal; }
	

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setId(int id) {
		this.id = id;
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

