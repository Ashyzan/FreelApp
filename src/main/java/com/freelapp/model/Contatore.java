package com.freelapp.model;

//import java.sql.Time;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contatori")
public class Contatore{
    
    @Id
    @Column(name = "task_id")
    private int id;

    @Column(name = "start", nullable = true)
    private LocalDateTime start;
    
    @Column(name = "restart", nullable = true)
    private LocalDateTime restart;

    @Column(name = "pause", nullable = true)
    private LocalDateTime pause;
    
    // tempo in secondi trascorso fra start e pause o stop
    @Column(name = "finaltime", nullable = true)
    private Long finaltime;
    
    @Column(name = "stop", nullable = true)
    private LocalDateTime stop;

    @Column(name = "stop_numbers", nullable = true)
    private int stop_numbers = 0;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "task_id")
    private Task task;
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
	this.start = start;
    }
    
    public LocalDateTime getRestart() {
        return restart;
    }


    public void setRestart(LocalDateTime restart) {
        this.restart = restart;
    }

    public LocalDateTime getPause() {
        return pause;
    }

    public void setPause(LocalDateTime pause) {
        this.pause = pause;
    }

    public LocalDateTime getStop() {
        return stop;
    }

    public void setStop(LocalDateTime stop) {
        this.stop = stop;
    }
    
    public Long getFinaltime() {
        return finaltime;
    }


    public void setFinaltime(Long finaltime) {
        this.finaltime = finaltime;
    }

    public int getStop_numbers() {
        return stop_numbers;
    }

    public void setStop_numbers(int stop_numbers) {
        this.stop_numbers = stop_numbers;
        
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    
    // function time difference start e primo stop
    public Long findDifference(LocalDateTime start_date, LocalDateTime end_date) {
	
	Long FinalTimeSeconds1 = start_date.until(end_date, ChronoUnit.SECONDS);
	
		//Long hours = FinalTimeSeconds / 3600;
		//Long minutes = (FinalTimeSeconds % 3600) / 60;
		Long seconds = FinalTimeSeconds1 % 60;

		//finalTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
	
	return seconds;
    }

    // calcola il tempo totale trascorso tra il restart e la pausa
    
    public Long findTimeRestart(LocalDateTime restart_date, LocalDateTime pause_date, Task task) {
	
 
	    restart_date = task.getContatore().getRestart();
	    pause_date = task.getContatore().getPause();
	    
	    Long finalTime  = restart_date.until(pause_date, ChronoUnit.SECONDS);
	    
	    Long finalTimeSeconds = finalTime % 60;
	    
	    Long prevSec = task.getContatore().getFinaltime();
	    
	    Long FinalTimeSeconds2 = finalTimeSeconds + prevSec;
	
	
	return FinalTimeSeconds2;
	
    }
    
// calcola il tempo totale trascorso fra il restart e lo stop
    
    public Long findTimeRestartStop(LocalDateTime restart_date, LocalDateTime stop_date, Task task) {
	
 
	    restart_date = task.getContatore().getRestart();
	    stop_date = task.getContatore().getStop();
	    
	    Long finalTime  = restart_date.until(stop_date, ChronoUnit.SECONDS);
	    
	    Long finalTimeSeconds = finalTime % 60;
	    
	    Long prevSec = task.getContatore().getFinaltime();
	    
	    Long FinalTimeSeconds3 = finalTimeSeconds + prevSec;
	
	
	return FinalTimeSeconds3;
	
    }
  

}
