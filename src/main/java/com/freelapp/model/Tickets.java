package com.freelapp.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tickets")
public class Tickets {
	
	public enum Status { OPEN, PENDING, CLOSED }
	public enum Priority { LOW, MEDIUM, HIGH }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "utente", nullable = false)
	private User utente;
	
	@Size(min = 1, max = 120, message = "massimo 120 caratteri")
	@Column(name = "subject", nullable = false)
	private String subject;
	
	@Size(min = 1, max =2000 , message="massimo 2000 caratteri")
	@Column(name = "messaggio", nullable = false)
	private String messaggio;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private Status status = Status.OPEN;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "priority", nullable = true)
	private Priority priority;
	
	@Column(name = "tipologia")
	private String tipologiaTicket;

	@ManyToOne
	@JoinColumn(name = "assigned_to", nullable = true)
	private User assignedTo;

	@ManyToOne
	@JoinColumn(name = "closed_by", nullable = true)
	private User closedBy;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@Column(name = "closed_at", nullable = true)
	private LocalDateTime closedAt;

	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TicketMessage> messages;

	public int getId() {
		return id;
	}


	public User getUtente() {
		return utente;
	}

	public void setUtente(User utente) {
		this.utente = utente;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public String getSubject() {
		return subject;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getTipologiaTicket() {
		return tipologiaTicket;
	}

	public void setTipologiaTicket(String tipologiaTicket) {
		this.tipologiaTicket = tipologiaTicket;
	}

	public User getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}

	public User getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(User closedBy) {
		this.closedBy = closedBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public LocalDateTime getClosedAt() {
		return closedAt;
	}

	public void setClosedAt(LocalDateTime closedAt) {
		this.closedAt = closedAt;
	}

	public List<TicketMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<TicketMessage> messages) {
		this.messages = messages;
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
