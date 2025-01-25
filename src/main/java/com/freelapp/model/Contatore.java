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

    @Column(name = "pause", nullable = true)
    private LocalDateTime pause;
    
    @Column(name = "finalTime", nullable = true)
    private String finalTime;
    
    @Column (name = "FinalTimeSecondsNow", nullable = true)
    private Long FinalTimeSecondsNow;

    public String getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(String finalTime) {
        this.finalTime = finalTime;
    }

    public Long getFinalTimeSecondsNow() {
        return FinalTimeSecondsNow;
    }

    public void setFinalTimeSecondsNow(Long finalTimeSecondsNow) {
        FinalTimeSecondsNow = finalTimeSecondsNow;
    }

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
    
    // function time difference
    public String findDifference(LocalDateTime start_date, LocalDateTime end_date) {
	
	Long FinalTimeSeconds = start_date.until(end_date, ChronoUnit.SECONDS);
	
	Long hours = FinalTimeSeconds / 3600;
	Long minutes = (FinalTimeSeconds % 3600) / 60;
	Long seconds = FinalTimeSeconds % 60;

	finalTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
	
	return finalTime;
    }
    
    // metodo per calcolare lo scorrere del tempo in secondi
    
    public Long FindDifferenceEverySecond(LocalDateTime start_date) {
	
	LocalDateTime timeNow = LocalDateTime.now();
	
	Long FinalTimeSecondsNow = start_date.until(timeNow, ChronoUnit.SECONDS);
	
	return FinalTimeSecondsNow;
	
    }
    

}
