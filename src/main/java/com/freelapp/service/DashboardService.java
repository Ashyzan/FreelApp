package com.freelapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freelapp.repository.TaskRepository;

@Service
public class DashboardService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	
	public double guadagnotototaleAnnoInCorsoProgetti() {
		
		double guadagnoProgetti = 1255.50;
		
		return guadagnoProgetti;
	}

}
