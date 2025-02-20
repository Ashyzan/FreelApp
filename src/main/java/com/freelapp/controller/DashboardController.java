package com.freelapp.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
		
//		if(ContatoreController.contatoreInUso != null) {
			if (ContatoreController.contatoreInUso != null) {
				
				Task task =taskRepository.getReferenceById(ContatoreController.taskInUso.getId());
				 contatoreservice.contatoreIsTrue(task, model);

				    contatoreservice.contatoreIsRun(task, model);
				    
				    

				    boolean contatoreIsRun = contatoreservice.contatoreIsRun(task, model);

				    LocalDateTime restartTime = task.getContatore().getRestart();

				    LocalDateTime timeNow = LocalDateTime.now();

				    Long FinalTime = task.getContatore().getFinaltime();
				    
				   // contatoreservice.timeExeed(bindingresult, task, model);

				    if (contatoreIsRun == true && restartTime == null) {

					task.getContatore()
						.setFinaltime((long) (timeNow.getSecond() - task.getContatore().getStart().getSecond()));

			    	//contatoreservice.timeExeed(bindingresult, task, model);

				    } else if (contatoreIsRun == true && restartTime != null) {

					task.getContatore().setFinaltime(
						(long) (FinalTime + (timeNow.getSecond() - task.getContatore().getRestart().getSecond())));
					//contatoreservice.timeExeed(bindingresult, task, model);
				    }

				    model.addAttribute("finaltime", task.getContatore().getFinaltime());

				}
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
