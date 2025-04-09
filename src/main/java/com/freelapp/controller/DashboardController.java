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
import com.freelapp.service.TaskService;


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
	
	@Autowired
	private TaskService taskService;
	

	@GetMapping("/dashboard")
	public String index( Model model){
		
//	passo al model l'endpoint da dare come input hidden a strt/pause/stop del contatore
		String endPoint = "/dashboard";
		model.addAttribute("endPoint", endPoint);
		
		contatoreservice.importContatoreInGet(model);
		model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
		model.addAttribute("taskInUso", ContatoreController.taskInUso);
	
		//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
		taskService.informationFromTaskInUsoToModel(model);
		
		
		//invio al model il booleano del contatore attivato
		//se contatoreAttivato = true avvio animazione su titolo task al contatore;
		model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
		//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
		ContatoreController.contatoreAttivato = false;

		List<Cliente> clienteList = new ArrayList<Cliente> ();

		clienteList = clienteRepository.findAll(Sort.by(Sort.Direction.ASC, "Name"));

		model.addAttribute("clientiList", clienteList);

		
		
		List<Progetto> progettiList = new ArrayList<Progetto> ();

		progettiList = progettoRepository.findAll(Sort.by(Sort.Direction.ASC, "Name"));

		model.addAttribute("progettiList", progettiList);
		
		
		//passa al model la lista di tutti i task esclusi quelli chiusi
		List<Task> taskList = new ArrayList<Task> ();
		taskList = taskRepository.findAllNotClosed();
		model.addAttribute("taskList", taskList);
		
		
//		// lista che restituisce i task per modale delle ore lavorate sulla dashboard
//		List<Task> taskListOreLavorate = new ArrayList<Task> ();
//		taskList = taskRepository.findAll(Sort.by(Sort.Direction.DESC, "Name"));
//		taskList.forEach( task -> {
//			
//			
//				if( task.getContatore() == null) {
//				taskListOreLavorate.add(task);
//				
//						}
//				
//				else if( task.getContatore() != null) {
//					Boolean contatoreAttivo;
//					contatoreAttivo = contatoreservice.contatoreIsRun(task);
//		
//					if(task.getContatore().getStop() == null  && contatoreAttivo == false) {
//					
//							 taskListOreLavorate.add(task); 
//									
//							 }	
//						}
//			model.addAttribute("taskListOreLavorate", taskListOreLavorate);
//				} );

		
		return "freelApp-dashboard";

			}
	
}
