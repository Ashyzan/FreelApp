package com.freelapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
import com.freelapp.model.Task;
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
				
				//restituisce al model questo valore booleano false se non ci sono progetti a db
				//e restituisce true se ci sono progetti a db
				boolean areProjectsOnDb = false;
				if(!repositProgetto.findAll().isEmpty()) {
					areProjectsOnDb = true;
				}
				model.addAttribute("areProjectsOnDb", areProjectsOnDb);
				
				return getOnePage(1, model);
			}
			
			@GetMapping("/Progetti/page/{pageNumber}")
			public String getOnePage(@PathVariable("pageNumber") int currentPage, Model model ) {
					
					
					
					// ordina i progetti per data di inizio
					Page<Progetto> pageByDataInizio = progettoService.orderByDataInizio(currentPage);
					int totalPageByDataInizio = pageByDataInizio.getTotalPages();	
					long totalItemByDatainizio = pageByDataInizio.getTotalElements();		
					List<Progetto> listProgettiByDataInizio = pageByDataInizio.getContent();
					
					//ordina i progetti per cliente
					Page<Progetto> pageByCliente = progettoService.orderByClient(currentPage);
					int totalPageByCliente = pageByCliente.getTotalPages();	
					long totalItemByCliente = pageByCliente.getTotalElements();		
					List<Progetto> listProgettiByCliente = pageByCliente.getContent();
					
					
					model.addAttribute("currentPage", currentPage);
					// passaggio al model delle liste per data inizio
					model.addAttribute("listProgettiByDataInizio", listProgettiByDataInizio);					
					model.addAttribute("totalPageByDataInizio", totalPageByDataInizio);					
					model.addAttribute("totalItemByDatainizio", totalItemByDatainizio);
					// passaggio al model delle liste per cliente
					model.addAttribute("listProgettiByCliente", listProgettiByCliente);					
					model.addAttribute("totalPageByCliente", totalPageByCliente);					
					model.addAttribute("totalItemByCliente", totalItemByCliente);
					
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
					
					//restituisce al model questo valore booleano false se non ci sono progetti a db
					//e restituisce true se ci sono progetti a db
					boolean areProjectsOnDb = false;
					if(!repositProgetto.findAll().isEmpty()) {
						areProjectsOnDb = true;
					}
					model.addAttribute("areProjectsOnDb", areProjectsOnDb);
				
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
				
				//restituisce al model questo valore booleano false se non ci sono progetti a db
				//e restituisce true se ci sono progetti a db
				boolean areProjectsOnDb = false;
				if(!repositProgetto.findAll().isEmpty()) {
					areProjectsOnDb = true;
				}
				model.addAttribute("areProjectsOnDb", areProjectsOnDb);
				
				return progettoBySearch(1, input, model);
			} 
				 
			 @GetMapping("/progetto-search/page/{numberPage}")
			 public String progettoBySearch(@PathVariable("pageNumber") int currentPage, String input,
					 	Model model) {

				 // ordina i progetti per data di inizio
					Page<Progetto> pageByDataInizio = progettoService.findSearchedPageByDataInizio(currentPage,input);
					int totalPageByDataInizio = pageByDataInizio.getTotalPages();	
					long totalItemByDatainizio = pageByDataInizio.getTotalElements();		
					List<Progetto> listProgettiByDataInizio = pageByDataInizio.getContent();
					
					//ordina i progetti per cliente
					Page<Progetto> pageByCliente = progettoService.findSearchedPageByClient(currentPage,input);
					int totalPageByCliente = pageByCliente.getTotalPages();	
					long totalItemByCliente = pageByCliente.getTotalElements();		
					List<Progetto> listProgettiByCliente = pageByCliente.getContent();
					
					model.addAttribute("currentPage", currentPage);
					// passaggio al model delle liste per data inizio
					model.addAttribute("listProgettiByDataInizio", listProgettiByDataInizio);					
					model.addAttribute("totalPageByDataInizio", totalPageByDataInizio);					
					model.addAttribute("totalItemByDatainizio", totalItemByDatainizio);
					// passaggio al model delle liste per cliente
					model.addAttribute("listProgettiByCliente", listProgettiByCliente);					
					model.addAttribute("totalPageByCliente", totalPageByCliente);					
					model.addAttribute("totalItemByCliente", totalItemByCliente);
					
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
					
					//restituisce al model questo valore booleano false se non ci sono progetti a db
					//e restituisce true se ci sono progetti a db
					boolean areProjectsOnDb = false;
					if(!repositProgetto.findAll().isEmpty()) {
						areProjectsOnDb = true;
					}
					model.addAttribute("areProjectsOnDb", areProjectsOnDb);
						
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
			
			@PostMapping("/Progetti/archivia/{id}")
			public String archiviaProgetto(@PathVariable("id") Integer id, Model model) {
				
				Progetto progetto = repositProgetto.findById(id).get();
				
				progetto.setArchivia(true);
				model.addAttribute("progetto",progetto);
				repositProgetto.save(progetto);
				
				return "redirect:/Progetti";
				
			}
			
			@PostMapping("/Progetti/de-archivia/{id}")
			public String deArchiviaProgetto(@PathVariable("id") Integer id, Model model) {
				
				Progetto progetto = repositProgetto.findById(id).get();
				
				progetto.setArchivia(false);
				model.addAttribute("progetto",progetto);
				repositProgetto.save(progetto);
				
				return "redirect:/Progetti/archivio";
				
			}
			
			@GetMapping("/Progetti/archivio")
			public String progettiArchivio(Model model) {
				
				//passo al model i contatore e task in uso (gli static)
				model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
				model.addAttribute("taskInUso", ContatoreController.taskInUso);
				
				//invio al model il booleano del contatore attivato
				//se contatoreAttivato = true avvio animazione su titolo task al contatore;
				model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
				
				//restituisce al model questo valore booleano false se non ci sono progetti a db
				//e restituisce true se ci sono progetti a db
				boolean areProjectsOnDb = false;
				if(!repositProgetto.findAll().isEmpty()) {
					areProjectsOnDb = true;
				}
				model.addAttribute("areProjectsOnDb", areProjectsOnDb);
				
			
				return progettiArchivioPagination(1, model);
			}
			
			@GetMapping("/Progetti/archivio/page/{pageNumber}")
			public String progettiArchivioPagination(@PathVariable("pageNumber") int currentPage, Model model) {
				
				List<Progetto> progettiAll = repositProgetto.findAll();
				List<Progetto> progettiArchiviati = new ArrayList<Progetto>();
				
				for (Progetto progetto : progettiAll) {
					
					if(progetto.getArchivia() == null) {
						progetto.setArchivia(false);
						repositProgetto.save(progetto);
					}
					if(progetto.getArchivia() == true) {
						progettiArchiviati.add(progetto);
						
					}
				}
				
				model.addAttribute("progettiArchiviati",progettiArchiviati);
				
				// ordina i progetti per data di inizio
				Page<Progetto> pageByArchivio = progettoService.orderByArchivio(currentPage);
				int totalPageByArchivio = pageByArchivio.getTotalPages();	
				long totalItemByArchivio = pageByArchivio.getTotalElements();		
				List<Progetto> listProgettiByArchivio = pageByArchivio.getContent();
				model.addAttribute("currentPage", currentPage);
				// passaggio al model delle liste per data inizio
				model.addAttribute("totalPageByArchivio", totalPageByArchivio);					
				model.addAttribute("totalItemByArchivio", totalItemByArchivio);					
				model.addAttribute("listProgettiByArchivio", listProgettiByArchivio);

				//passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
				if(currentPage != 0) {
					String endPoint = "/Progetti/archivio/page/" + currentPage;
					
					model.addAttribute("endPoint", endPoint);						
				} else {
					String endPoint = "/Progetti/archivio";
					model.addAttribute("endPoint", endPoint);	
				}
				
				//restituisce al model questo valore booleano false se non ci sono progetti a db
				//e restituisce true se ci sono progetti a db
				boolean areProjectsOnDb = false;
				if(!repositProgetto.findAll().isEmpty()) {
					areProjectsOnDb = true;
				}
				model.addAttribute("areProjectsOnDb", areProjectsOnDb);
				
				return "/Progetti/freelApp-archivioProgetti";
				
			}
			
			// FILTRO DI RICERCA PER PROGETTI ARCHIVIATI
			
			@GetMapping("/progetto-archivio-search")
			public String listaProgettiArchiviatiSearch(@Param("input") String input, Model model) {
				
				model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
				model.addAttribute("taskInUso", ContatoreController.taskInUso);
				model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);

				List<Progetto> progettiAll = repositProgetto.findAll();
				List<Progetto> progettiArchiviati = new ArrayList<Progetto>();
				
				for (Progetto progetto : progettiAll) {
					
					if(progetto.getArchivia() == null) {
						progetto.setArchivia(false);
						repositProgetto.save(progetto);
					}
					if(progetto.getArchivia() == true) {
						progettiArchiviati.add(progetto);
						
					}
				}
				
				model.addAttribute("progettiArchiviati",progettiArchiviati);
				
				boolean areProjectsOnDb = false;
				if(!repositProgetto.findAll().isEmpty()) {
					areProjectsOnDb = true;
				}
				model.addAttribute("areProjectsOnDb", areProjectsOnDb);
				return progettoBySearchArchiviati(1, input, model);
			} 
				 
			 @GetMapping("/progetto-archivio-search/page/{numberPage}")
			 public String progettoBySearchArchiviati(@PathVariable("pageNumber") int currentPage, String input,
					 	Model model) {
				 
					Page<Progetto> pageByArchivio = progettoService.findSearchedPageByArchiviati(currentPage,input);
					int totalPageByArchivio = pageByArchivio.getTotalPages();	
					long totalItemByArchivio = pageByArchivio.getTotalElements();		
					List<Progetto> listProgettiByArchivio = pageByArchivio.getContent();
					
					model.addAttribute("currentPage", currentPage);
					model.addAttribute("listProgettiByArchivio", listProgettiByArchivio);					
					model.addAttribute("totalPageByArchivio", totalPageByArchivio);					
					model.addAttribute("totalItemByArchivio", totalItemByArchivio);
									
					contatoreservice.importContatoreInGet(model);

					if(input != null) {
						String endPoint = "/progetto-archivio-search?input=" + input;
						
						model.addAttribute("endPoint", endPoint);						
					} else {
						String endPoint = "/progetto-archivio-search";
						model.addAttribute("endPoint", endPoint);	
					}

					model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
					model.addAttribute("taskInUso", ContatoreController.taskInUso);
					model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
					ContatoreController.contatoreAttivato = false;
					
					boolean areProjectsOnDb = false;
					if(!repositProgetto.findAll().isEmpty()) {
						areProjectsOnDb = true;
					}
					model.addAttribute("areProjectsOnDb", areProjectsOnDb);
						
				return "/Progetti/freelApp-archivioProgetti";
			}
			
}
