package com.freelapp.model;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "PermessiUtenti")
public class PermessiUtenti {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "nomePermesso", nullable = false, unique = true)
	private String nomePermesso;
	
	@ManyToMany(mappedBy = "permissions")
	private List<Roles> roles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomePermesso() {
		return nomePermesso;
	}

	public void setNomePermesso(String nomePermesso) {
		this.nomePermesso = nomePermesso;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}
}