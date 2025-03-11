package com.freelapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.freelapp.model.Cliente;
import com.freelapp.model.Progetto;
import com.freelapp.model.User;
import com.freelapp.repository.ClienteRepository;
import com.freelapp.repository.ProgettoRepository;
import com.freelapp.repository.TaskRepository;
import com.freelapp.repository.UserRepository;
import com.freelapp.service.ContatoreService;
import com.freelapp.service.ProgettoService;

import jakarta.validation.Valid;

@Controller
public class ProgettoController {


	@Autowired
	private ProgettoRepository repositProgetto;
	  
	@Autowired
	private UserRepository repositUser;
	
	@Autowired
	private ClienteRepository repositClient;
	
	@Autowired
	private ProgettoService progettoService;
	
	@Autowired
	private TaskRepository repositTask;
	
	@Autowired
	private ContatoreService contatoreservice;
	
			@GetMapping("/Progetti")
			public String listaProgetti(Model model) {
					
				//passo al model i contatore e task in uso (gli static)
				model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
				model.addAttribute("taskInUso", ContatoreController.taskInUso);
				
				//invio al model il booleano del contatore attivato
				//se contatoreAttivato = true avvio animazione su titolo task al contatore;
				model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
				
				return getOnePage(1, model);
			}
			
			@GetMapping("/Progetti/page/{pageNumber}")
			public String getOnePage(@PathVariable("pageNumber") int currentPage, Model model ) {
				
					Page<Progetto> page = progettoService.findPage(currentPage);
					
					int totalPages = page.getTotalPages();
					
					long totalItems = page.getTotalElements();
					
					List<Progetto> listProgetti = page.getContent();
					
					model.addAttribute("list", listProgetti);
					
					model.addAttribute("currentPage", currentPage);
				
					model.addAttribute("totalPages", totalPages);
					
					model.addAttribute("totalItems", totalItems);
					
					contatoreservice.importContatoreInGet(model);
					
					//passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
					if(currentPage != 0) {
						String endPoint = "/Progetti/page/" + currentPage;
						
						model.addAttribute("endPoint", endPoint);						
					} else {
						String endPoint = "/Progetti";
						model.addAttribute("endPoint", endPoint);	
					}
					
					//passo al model i contatore e task in uso (gli static)
					model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
					model.addAttribute("taskInUso", ContatoreController.taskInUso);
					
					//invio al model il booleano del contatore attivato
					//se contatoreAttivato = true avvio animazione su titolo task al contatore;
					model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
					//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
					ContatoreController.contatoreAttivato = false;
				
				return "/Progetti/freelApp-listaProgetti";
			} 
			
			@GetMapping("/progetto-search")
			public String listaProgettiSearch(@Param("input") String input, Model model) {
				
				//passo al model i contatore e task in uso (gli static)
				model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
				model.addAttribute("taskInUso", ContatoreController.taskInUso);
				
				//invio al model il booleano del contatore attivato
				//se contatoreAttivato = true avvio animazione su titolo task al contatore;
				model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
				//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
//				ContatoreController.contatoreAttivato = false;
				
				return progettoBySearch(1, input, model);
			} 
				 
			 @GetMapping("/progetto-search/page/{numberPage}")
			 public String progettoBySearch(@PathVariable("pageNumber") int currentPage, String input,
					 	Model model) {

						 
					Page<Progetto> page = progettoService.findSearchedPage(currentPage,input);

					int totalPages = page.getTotalPages();
						 
					long totalItems = page.getTotalElements();
						 
					List<Progetto> listaClientiSearch = page.getContent();
						
					model.addAttribute("currentPage", currentPage);
					
					model.addAttribute("totalPages", totalPages);
						
					model.addAttribute("totalItems", totalItems);
						
					model.addAttribute("list", listaClientiSearch);	
					
					contatoreservice.importContatoreInGet(model);
					
					//passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
					if(input != null) {
						String endPoint = "/progetto-search?input=" + input;
						
						model.addAttribute("endPoint", endPoint);						
					} else {
						String endPoint = "/progetto-search";
						model.addAttribute("endPoint", endPoint);	
					}
					
					//passo al model i contatore e task in uso (gli static)
					model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
					model.addAttribute("taskInUso", ContatoreController.taskInUso);
					
					//invio al model il booleano del contatore attivato
					//se contatoreAttivato = true avvio animazione su titolo task al contatore;
					model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
					//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
					ContatoreController.contatoreAttivato = false;
						
				return "/Progetti/freelApp-listaProgetti";
			}
	
			@GetMapping("/Progetti/{id}")
			public String descrizioneProgetto(@PathVariable("id") int progettoId, Model model) {
		
				model.addAttribute("progetto", repositProgetto.getReferenceById(progettoId));
				
				//passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
				String endPoint = "/Progetti/" + repositProgetto.getReferenceById(progettoId).getId();
				
				model.addAttribute("endPoint", endPoint);
				
				contatoreservice.importContatoreInGet(model);
				//passo al model i contatore e task in uso (gli static)
				model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
				model.addAttribute("taskInUso", ContatoreController.taskInUso);
				
				//invio al model il booleano del contatore attivato
				//se contatoreAttivato = true avvio animazione su titolo task al contatore;
				model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
				//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
				ContatoreController.contatoreAttivato = false;
	
				return "/Progetti/freelapp-descrizioneProgetto";
		   }
	
			@GetMapping("/Progetti/insert/{id}")
			public String insertProject(@PathVariable("id") Integer id, Model model) {
				
				User utente = repositUser.findById(id).get();

				Progetto formProgetto = new Progetto();
				
				formProgetto.setUtente(utente);
				
				List<Cliente> listaClienti = repositClient.findAll();  
				
				model.addAttribute("formClienti", listaClienti);

				model.addAttribute("formProgetto", formProgetto);
				
				
				//passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
				String endPoint = "/Progetti/insert/" + utente.getId();
				
				model.addAttribute("endPoint", endPoint);
				
				contatoreservice.importContatoreInGet(model);
				//passo al model i contatore e task in uso (gli static)
				model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
				model.addAttribute("taskInUso", ContatoreController.taskInUso);
				//passp al model taskInUsoId che serve alla modale di controllo start e pause in edit e insert
				if(ContatoreController.taskInUso != null) {
			    	model.addAttribute("taskInUsoId",ContatoreController.taskInUso.getId());
			    }else {
			    	model.addAttribute("taskInUsoId",0);
			    }
				
				//invio al model il booleano del contatore attivato
				//se contatoreAttivato = true avvio animazione su titolo task al contatore;
				model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
				//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
				ContatoreController.contatoreAttivato = false;
				
				return "/Progetti/freelapp-insertProgetto";
			}
	
	
			@PostMapping("/Progetti/insert")
			public String storeProgetto(@Valid @ModelAttribute("formProgetto") Progetto formProgetto, BindingResult bindingResult, Model model) {
			    	
				
			    	if(bindingResult.hasErrors()) {
					
			    	    List<Cliente> listaClienti = repositClient.findAll();  
				
			    	    model.addAttribute("formClienti", listaClienti);
			    	    
			    	  //passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
			    	  // quando sarà implementato il login "/Task/insert/progetto-" + 1 diventerà "/Task/insert/progetto-" + userLogged.getId()
						String endPoint = "/Task/insert/progetto-" + 1; 
						
						model.addAttribute("endPoint", endPoint);
						
						contatoreservice.importContatoreInGet(model);
						//passo al model i contatore e task in uso (gli static)
						model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
						model.addAttribute("taskInUso", ContatoreController.taskInUso);
						//passp al model taskInUsoId che serve alla modale di controllo start e pause in edit e insert
						if(ContatoreController.taskInUso != null) {
					    	model.addAttribute("taskInUsoId",ContatoreController.taskInUso.getId());
					    }else {
					    	model.addAttribute("taskInUsoId",0);
					    }
					
			    	    return "/Progetti/freelapp-insertProgetto";
					
				}
		
				repositProgetto.save(formProgetto);
		
				return "redirect:/Progetti";       
			}
	
			
			@GetMapping("/Progetti/edit/{id}")
			public String edit(@PathVariable("id") Integer id, Model model) {
				
				Progetto formProgetto = repositProgetto.findById(id).get();
			
				//User utente = repositUser.findById(id).get();
				
				//formProgetto.setUtente(utente);
				
				List<Cliente> listaClienti = repositClient.findAll();  

				
				model.addAttribute("formProgetto", formProgetto);
				
				model.addAttribute("formClienti", listaClienti);
				
				//passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
				String endPoint = "/Progetti/edit/" + formProgetto.getId();
				
				model.addAttribute("endPoint", endPoint);
				
				contatoreservice.importContatoreInGet(model);
				//passo al model i contatore e task in uso (gli static)
				model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
				model.addAttribute("taskInUso", ContatoreController.taskInUso);
				//passp al model taskInUsoId che serve alla modale di controllo start e pause in edit e insert
				if(ContatoreController.taskInUso != null) {
			    	model.addAttribute("taskInUsoId",ContatoreController.taskInUso.getId());
			    }else {
			    	model.addAttribute("taskInUsoId",0);
			    }
				
				//invio al model il booleano del contatore attivato
				//se contatoreAttivato = true avvio animazione su titolo task al contatore;
				model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
				//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
				ContatoreController.contatoreAttivato = false;
				
				return "/Progetti/freelapp-editProgetto";
			}
			
			
			@PostMapping("/Progetti/edit/{id}")
		    public String updateProgetto(@Valid @ModelAttribute("formProgetto") Progetto formProgetto, BindingResult bindingResult, Model model) {
							
				if(bindingResult.hasErrors()) {
					
					//passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
					String endPoint = "/Progetti/edit/" + formProgetto.getId();
					
					model.addAttribute("endPoint", endPoint);
					
					contatoreservice.importContatoreInGet(model);
					//passo al model i contatore e task in uso (gli static)
					model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
					model.addAttribute("taskInUso", ContatoreController.taskInUso);
					//passp al model taskInUsoId che serve alla modale di controllo start e pause in edit e insert
					if(ContatoreController.taskInUso != null) {
				    	model.addAttribute("taskInUsoId",ContatoreController.taskInUso.getId());
				    }else {
				    	model.addAttribute("taskInUsoId",0);
				    }
					
					return  "/Progetti/freelapp-editProgetto";
				}
 
				repositProgetto.save(formProgetto);
				
				return "redirect:/Progetti"; 
			    }
			
			
			@PostMapping("/Progetti/delete/{id}")
			public String deleteTask(@PathVariable("id") Integer id) {
				
				repositTask.deleteByProgettoId(id);
				
			    repositProgetto.deleteById(id);
			
			    return "redirect:/Progetti";
			}
			
}
