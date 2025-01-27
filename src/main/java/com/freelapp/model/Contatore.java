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

	
	return FinalTimeSeconds1;
    }

    // calcola il tempo totale trascorso
    
    public Long findTimeRestart(LocalDateTime restart_date, LocalDateTime pause_date, Task task) {
	
	
	    
	    restart_date = task.getContatore().getRestart();
	    pause_date = task.getContatore().getRestart();
	    
	    Long finalTime  = restart_date.until(pause_date, ChronoUnit.SECONDS);
	    
	    Long FinalTimeSeconds2 = finalTime + task.getContatore().getFinaltime();
	
	
	return FinalTimeSeconds2;
	
    }
  

}
