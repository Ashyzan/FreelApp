package com.freelapp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.freelapp.controller.ProgettoController;
import com.freelapp.service.ContatoreService;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;



@Entity
@Table(name = "SessioniTask")
public class SessioniTask {
	

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "tempo")
    private LocalDateTime time;
    
    @Column(name = "azione")
    private String azione;
    
    @Column(name = "worktime")
    private String worktime;
    
    @Column(name = "variazione")
    private String variazione;
    
	public String getVariazione() {
		return variazione;
	}

	public void setVariazione(String variazione) {
		this.variazione = variazione;
	}

	@ManyToOne
	@JoinColumn(name = "contatore_id", nullable = false)
	private Contatore contatore;

    // Costruttore vuoto
    public SessioniTask() {
    }

    // Costruttore con parametri
    public SessioniTask(Contatore contatore, LocalDateTime time, String azione, String worktime) {
        this.contatore = contatore;
        this.time = time;
        this.azione = azione;
        this.worktime = worktime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Contatore getContatore() {
        return contatore;
    }

    public void setContatore(Contatore contatore) {
        this.contatore = contatore;
        // Popola automaticamente i campi dal contatore se disponibile
        if (contatore != null) {
            populateFromContatore();
        }
    }
 
    
    public LocalDateTime getTime() {

        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getAzione() {
        return azione;
    }

    public void setAzione(String azione) {
        this.azione = azione;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    /**
     * Metodo per popolare i campi dal contatore
     * Chiamato automaticamente quando si imposta il contatore
     */
    

    public void populateFromContatore() {
        if (contatore != null && contatore.getTask() != null) {
            // Imposta il tempo dalla data di modifica del task
            this.time = contatore.getTask().getDataModifica();
            
           
            
            // Imposta l'azione basata sullo stato del task
            String stato = contatore.getTask().getStato();
            if (stato != null) {
                switch (stato) {
                    case "inattivo":
                        this.azione = "-";
                        break;
                    case "in pausa":
                        this.azione = "pausa";
                        break;
                    case "in corso":
                        this.azione = "avvio";
                        break;
                    case "chiuso":
                        this.azione = "stop";
                        break;
                    default:
                        this.azione = stato;
                        break;
                }
            }
        }
    }

    /**
     * Metodo per aggiornare i campi dal contatore
     * Utile per aggiornamenti successivi
     */
    public void updateFromContatore() {
        populateFromContatore();
    }
 

    

}
