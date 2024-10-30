package com.freelapp.model;

import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table (name = "contatore")
public class Contatore {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "start", nullable = false)
	@NotNull(message = "Start non può essere null")
	private Time start;
    
    @Column(name = "pause", nullable = true)
	private Time pause;
	
    @Column(name = "stop", nullable = true)
	private Time stop;
    
    @Column(name = "stop_numbers", nullable = true)
    	private int stop_numbers;
	
    @OneToOne(mappedBy = "contatore")
    private Task task;
    
    // getter e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getPause() {
        return pause;
    }

    public void setPause(Time pause) {
        this.pause = pause;
    }

    public Time getStop() {
        return stop;
    }

    public void setStop(Time stop) {
        this.stop = stop;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    

    
    
	
}
