package com.freelapp.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.freelapp.model.Cliente;
import com.freelapp.model.Progetto;
import com.freelapp.model.Task;
import com.freelapp.repository.ClienteRepository;
import com.freelapp.repository.ProgettoRepository;
import com.freelapp.repository.TaskRepository;


@Controller
public class DashboardController {


	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProgettoRepository progettoRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	

	@GetMapping("/dashboard")
	public String index( Model model){
		
		List<Cliente> clienteList = new ArrayList<Cliente> ();

		clienteList = clienteRepository.findAll(Sort.by(Sort.Direction.ASC, "Name"));

		model.addAttribute("clientiList", clienteList);

		
		
		List<Progetto> progettiList = new ArrayList<Progetto> ();

		progettiList = progettoRepository.findAll(Sort.by(Sort.Direction.ASC, "Name"));

		model.addAttribute("progettiList", progettiList);
		
		
		List<Task> taskList = new ArrayList<Task> ();

		taskList = taskRepository.findAll(Sort.by(Sort.Direction.ASC, "Name"));

		model.addAttribute("taskList", taskList);
		
		return "freelApp-dashboard";

	}
	
	@PostMapping("/dashboard")
	public String indexWithTaskSelected(@ModelAttribute("taskId") Integer taskId, Model model) {
		
		Integer taskSelectedId = taskId;
		
		model.addAttribute("taskSelectedId", taskSelectedId);
		
		Task taskSelected = taskRepository.getReferenceById(taskSelectedId);
		
		model.addAttribute("task", taskSelected);
		
		List<Cliente> clienteList = new ArrayList<Cliente> ();

		clienteList = clienteRepository.findAll(Sort.by(Sort.Direction.ASC, "Name"));

		model.addAttribute("clientiList", clienteList);

		
		
		List<Progetto> progettiList = new ArrayList<Progetto> ();

		progettiList = progettoRepository.findAll(Sort.by(Sort.Direction.ASC, "Name"));

		model.addAttribute("progettiList", progettiList);
		
		
		List<Task> taskList = new ArrayList<Task> ();

		taskList = taskRepository.findAll(Sort.by(Sort.Direction.ASC, "Name"));

		model.addAttribute("taskList", taskList);
		
		model.addAttribute("modelProva", "ciao");
		
		return "freelApp-dashboard";
	}
	
	
}
