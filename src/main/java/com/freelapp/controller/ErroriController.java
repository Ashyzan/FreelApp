package com.freelapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.freelapp.model.Task;
import com.freelapp.repository.TaskRepository;
import com.freelapp.service.ContatoreService;
import com.freelapp.service.TaskService;

@Controller
@RequestMapping("/Errori")
public class ErroriController {
	
	@Autowired 
	private ContatoreService contatoreservice;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@GetMapping("/MaxUploadSizeExceeded")
	public String MaxUploadSizeExceeded(Model model) {
		
//		passo al model l'endpoint da dare come input hidden a strt/pause/stop del contatore
		String endPoint = "/Errori/MaxUploadSizeExceeded";
		model.addAttribute("endPoint", endPoint);
		
		contatoreservice.importContatoreInGet(model);
		model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
		model.addAttribute("taskInUso", ContatoreController.taskInUso);
		model.addAttribute("contatoreAttivatoDaRapidButton", ContatoreController.contatoreAttivatoDaRapidButton);
		
		//inizializzo a false così al reload successivo js non genera i tasti del contatore
		ContatoreController.contatoreAttivatoDaRapidButton = false;
		
		//metodo che passa al model le informazioni sul task in uso per generare la modale STOP	
		taskService.informationFromTaskInUsoToModel(model);

			//invio al model il booleano del contatore attivato
		//se contatoreAttivato = true avvio animazione su titolo task al contatore;
		model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
		//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
		ContatoreController.contatoreAttivato = false;
		
		// invio al model il booleano del contatore cliccato prima del refresh pagina
		// se contatoreCliccatoPreRefresh = true avvio animazione che porta la schermata in basso su mobile;
		model.addAttribute("contatoreCliccatoPreRefresh", ContatoreController.contatoreCliccatoPreRefresh);

		// inizializzo a false così che al refresh esegue animazione solo se era stato cliccato in precedenza
		ContatoreController.contatoreCliccatoPreRefresh = false;
		
		//passa al model la lista di tutti i task esclusi quelli chiusi
		List<Task> taskList = new ArrayList<Task> ();
		taskList = taskRepository.findAllNotClosed();
		model.addAttribute("taskList", taskList);
		
		return "/Errori/MaxUploadSizeExceeded";
		
	}
	

}
