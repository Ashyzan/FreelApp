package com.freelapp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Long id;
    
    @Column(name = "tempo")
    private LocalDateTime time;
    
    @Column(name = "azione")
    private String azione;
    
    @Column(name = "worktime")
    private Long worktime;
    
	@ManyToOne
	@JoinColumn(name = "contatore_id", nullable = false)
	private Contatore contatore;

    // Costruttore vuoto
    public SessioniTask() {
    }

    // Costruttore con parametri
    public SessioniTask(Contatore contatore, LocalDateTime time, String azione, Long worktime) {
        this.contatore = contatore;
        this.time = time;
        this.azione = azione;
        this.worktime = worktime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getWorktime() {
        return worktime;
    }

    public void setWorktime(Long worktime) {
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
            
            // Imposta il worktime dal finaltime del contatore
            if (contatore.getFinaltime() != null) {
                this.worktime = contatore.getFinaltime();
            }
            
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
 // metodo che restituisce il finaltime formattato in HH:MM:SS
 	public String Timer(Contatore contatore) {		
 	    Long finaltime = contatore.getFinaltime(); 
 	    Long HH = finaltime / 3600;
 	    Long MM= (finaltime % 3600) / 60;
 	    Long SS = finaltime % 60;  
 	    String timer = String.format("%02d:%02d:%02d", HH, MM, SS);	    
 		return timer;
 	}

 	public String dateToString() {
        return  time.getDayOfMonth() + "/" + time.getMonthValue()  + "/" + time.getYear();
    }
 	
    public String timeToString() {
        return  time.getHour() + ":" + time.getMinute() ;
        		
    }
    public String azioneToString() {
        return  azione;
        		
    } 
    
    public String tempoToString() {
        return  Timer(contatore);
        		
    } 
    

    
    public String variazioneToString() {
    	Long finaltime = contatore.getFinaltime();
    	String stato = contatore.getTask().getStato();
    	if(stato == "avvio") {
    		
    		System.out.println("********************************************    primo if");
    		
    		return " -- " ;
    	//	LocalDateTime ora = LocalDateTime.now();
    		
    		
    	}
    	
    	else if(stato == "pausa") {
    		System.out.println("********************************************     secondo if");
    		return  "differenza";
    	}
    	System.out.println("********************************************     altro if");
    	return " - - ";
        		
    }
}
