package com.freelapp.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "ruoli")
public class Roles {
	
	@Id
	private Integer id;
	
	@Column(name = "ruolo_permessi")
	private String nomeRuolo;
	
	@OneToMany(mappedBy = "role")
	private List<User> users;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "role_permission",
		joinColumns = @JoinColumn(name = "role_id"),
		inverseJoinColumns = @JoinColumn(name = "permission_id"),
		uniqueConstraints = @UniqueConstraint(name = "uk_role_permission", columnNames = {"role_id","permission_id"})
	)
	private List<PermessiUtenti> permissions;

	public Integer getId() {
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<PermessiUtenti> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermessiUtenti> permissions) {
		this.permissions = permissions;
	}
}
