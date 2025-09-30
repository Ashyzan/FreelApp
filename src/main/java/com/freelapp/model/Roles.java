package com.freelapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ruoli")
public class Roles {
	
	@Id
	private Integer id;
	
	@Column(name = "ruolo")
	private String nomeRuolo;

	public long getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeRuolo() {
		return nomeRuolo;
	}

	public void setNomeRuolo(String nomeRuolo) {
		this.nomeRuolo = nomeRuolo;
	}

}
