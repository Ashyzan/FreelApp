package com.freelapp.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.freelapp.model.PermessiUtenti;
import com.freelapp.model.Roles;
import com.freelapp.model.User;

public class DatabaseUserDetails implements UserDetails {
	private final String username;
	private final String password;
	private final Set<GrantedAuthority> authorities;
	private final int id;
	
	public DatabaseUserDetails(User user) {
		this.id = user.getId();
		this.username = user.getEmail();
		this.password = user.getPassword();
		this.authorities = new HashSet<>();
		
		Roles role = user.getRole();
		if (role != null) {
			// Aggiungo authority per il ruolo (con prefisso ROLE_)
			this.authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getNomeRuolo()));
			// Aggiungo authorities per i permessi del ruolo
			if (role.getPermissions() != null) {
				for (PermessiUtenti p : role.getPermissions()) {
					if (p != null && p.getNomePermesso() != null) {
						this.authorities.add(new SimpleGrantedAuthority(p.getNomePermesso()));
					}
				}
			}
		}
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
}
