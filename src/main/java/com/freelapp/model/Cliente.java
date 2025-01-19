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
	
	@Column(name = "NomeDitta", nullable = false)
	@NotNull(message = "Inserimento del nome obbligatorio")
	@NotBlank(message = "Inserimento del nome obbligatorio")
	private String name;

	@Column(name = "RagioneSociale", nullable = false)
	@NotNull(message = "Inserimento ragione sociale obbligatorio")
	@NotBlank(message = "Inserimento ragione sociale obbligatorio")
	private String ragioneSociale;
	
	@Column(name = "email", nullable = false)
	@NotNull(message = "Inserimento email obbligatorio")
	@NotBlank(message = "Inserimento email obbligatorio")
	private String email;
	
	@Column(name = "Telefono", nullable = false)
	@NotNull(message = "Inserimento numero telefono obbligatorio")
	@NotBlank(message = "Inserimento numero telefono obbligatorio")
	private String telefono;
	
	@Column(name = "Indirizzo", nullable = false)
	@NotNull(message = "Inserimento indirizzo obbligatorio")
	@NotBlank(message = "Inserimento indirizzo obbligatorio")
	private String indirizzo;
	
	@Column(name = "Città", nullable = false)
	@NotNull(message = "Inserimento città obbligatorio")
	@NotBlank(message = "Inserimento città obbligatorio")
	private String city;
	
	@Column(name = "PartitaIva", nullable = false)
	@NotNull(message = "Inserimento partita iva obbligatorio")
	@NotBlank(message = "Inserimento partita iva obbligatorio")
	private String partitaIva;
	
	@OneToMany(mappedBy = "cliente")
	private List<Progetto> progetti;
		
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

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
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

	

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public List<Progetto> getProgetti() {
		return progetti;
	}

	public void setProgetti(List<Progetto> progetti) {
		this.progetti = progetti;
	}
}

