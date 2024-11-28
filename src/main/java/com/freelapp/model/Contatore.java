package com.freelapp.model;

//import java.sql.Time;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table (name = "contatori")
public class Contatore {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "start", nullable = true)
	@NotNull(message = "Start non può essere null")
    private LocalTime start;
	
    @Column(name = "pause", nullable = true)
	private LocalTime pause;
	
    @Column(name = "stop", nullable = true)
	private LocalTime stop;
    
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


	public LocalTime getStart() {
		return start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	public LocalTime getPause() {
		return pause;
	}

	public void setPause(LocalTime pause) {
		this.pause = pause;
	}

	public LocalTime getStop() {
		return stop;
	}

	public void setStop(LocalTime stop) {
		this.stop = stop;
	}

	public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getStop_numbers() {
        return stop_numbers;
    }

    public void setStop_numbers(int stop_numbers) {
        this.stop_numbers = stop_numbers;
    }
    
}
