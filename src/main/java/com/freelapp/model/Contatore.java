package com.freelapp.model;

//import java.sql.Time;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private LocalDateTime start;
	
    @Column(name = "pause", nullable = true)
	private LocalDateTime pause;
	
    @Column(name = "stop", nullable = true)
	private LocalDateTime stop;
    
    @Column(name = "stop_numbers", nullable = true)
    private static int stop_numbers = 0;
	
    //@OneToOne(mappedBy = "contatore")
    //private Task task;
    
	@ManyToOne
	@JoinColumn(name = "TaskRif", nullable = false)
	private Task task;
    
    // getter e setters
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

    public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public static int getStop_numbers() {
        return stop_numbers;
    }
   
    public static void addStop_numbers() {
         stop_numbers++;
     }
}
