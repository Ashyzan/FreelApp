package com.freelapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.freelapp.model.Cliente;
import com.freelapp.model.Progetto;
import com.freelapp.model.Task;
import com.freelapp.repository.ClienteRepository;
import com.freelapp.repository.ProgettoRepository;
import com.freelapp.repository.TaskRepository;
import com.freelapp.service.ContatoreService;


@Controller
public class DashboardController {


	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProgettoRepository progettoRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ContatoreService contatoreservice;
	

	@GetMapping("/dashboard")
	public String index( Model model){
		
//	passo al model l'endpoint da dare come input hidden a strt/pause/stop del contatore
		String endPoint = "/dashboard";
		model.addAttribute("endPoint", endPoint);
		
			contatoreservice.importContatoreInGet(model);
			model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
			model.addAttribute("taskInUso", ContatoreController.taskInUso);


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
	
}
