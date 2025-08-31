package com.freelapp.model;

//import java.sql.Time;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "SessioniTask")
public class SessioniTask {

	  @Id
	    @Column(name = "contatore_id")
	    private int id;
	  	  
	@OneToOne
    @MapsId
    @JoinColumn(name = "contatore_id")
    private Contatore contatore;
	
    @JoinColumn(name = "tempo")
	private LocalDateTime Time;
    
    @JoinColumn(name = "azione")
	private String Azione;
    
    @JoinColumn(name = "worktime")
	private Long Worktime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Contatore getContatore() {
		return contatore;
	}

	public void setContatore(Contatore contatore) {
		this.contatore = contatore;
	}

	public LocalDateTime getTime() {
		return Time;
	}

	public void setTime(LocalDateTime time) {
		Time = contatore.getTask().getDataModifica();
	}

	public String getAzione() {
		return Azione;
	}

	public void setAzione(String azione) {
		Azione = contatore.getTask().getStato();
		if (Azione == "inattivo") {
			Azione = "-";
		}
		else if (Azione == "in pausa") {
			Azione = "pausa";
		}
		else if (Azione == "in corso") {
			Azione = "avvio";
		}
		else if (Azione == "chiuso") {
			Azione = "stop";
		}
	}

	public Long getWorktime() {
		return Worktime;
	}

	public void setWorktime(Long worktime) {
		Worktime = contatore.getFinaltime();
	}
	
	
}
