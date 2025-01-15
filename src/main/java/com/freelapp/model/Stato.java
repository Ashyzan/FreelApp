//package com.freelapp.model;
//
//import java.util.List;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//import jakarta.persistence.Column;
//import jakarta.persistence.Id;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.OneToMany;
//import jakarta.validation.constraints.NotNull;
//
//@Entity
//@Table(name = "Stati")
//public class Stato {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//	
//	@Column(name = "Stato", nullable = false)
//	@NotNull(message = "Lo stato non può essere null")
//	private StatoTask tipoStato;
//	
//	@OneToMany(mappedBy = "stato")
//	private List<Task> tasks;
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public StatoTask getTipoStato() {
//		return tipoStato;
//	}
//
//	public void setTipoStato(StatoTask tipoStato) {
//		this.tipoStato = tipoStato;
//	}
//	
//	public List<Task> getTasks() {
//		return tasks;
//	}
//
//	public void setTasks(List<Task> tasks) {
//		this.tasks = tasks;
//	}
//}
