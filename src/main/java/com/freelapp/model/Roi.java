package com.freelapp.model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "roi")
public class Roi {

		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id")
		private int id;
		
		@OneToOne
	    @MapsId
	    @JoinColumn(name = "progetto_id")
	    private Progetto progetto;
		
		@ManyToOne
		@NotNull(message = "La scelta del progetto è obbligatoria")
		@JoinColumn(name = "ProgettoRif", nullable = false)
		private Task task;
		
	

}
