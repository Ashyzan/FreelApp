package com.freelapp.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.freelapp.model.Task;
import com.freelapp.model.User;
import com.freelapp.repository.ClienteRepository;
import com.freelapp.repository.ProgettoRepository;
import com.freelapp.repository.TaskRepository;
import com.freelapp.repository.UserRepository;
import com.freelapp.service.ContatoreService;
import com.freelapp.service.ProgettoService;
import com.freelapp.service.TaskService;

import jakarta.validation.Valid;

@Controller
public class ProgettoController {


	private static final boolean Task = false;
	
	//variablili che verranno utilizzate per memorizzare la scelta effettuata durante la sessione
	public static boolean ordinaElencoProgettiPerData = true;

	public static boolean ordinaElencoProgettiPerCliente = false;
	
	//variabile che memorizza l'ultima pagina consultata nella lista Progetti e serve per mantenerla durante la sessione
	private int currentPageListaProgetti = 1;
	
	//variabile che passo al model del search progetti per dirgli che siamo in modalità search
	private boolean searchMode = false;
	
	//variabile che memorizza l'ultima pagina consultata nella ricerca da lista Progetti e serve per mantenerla durante
	//consultazione del dettaglio prima di tornare alla ricerca o fino a nuova ricerca
	private int lastVisitedPageInProgettiSearch = 1;
	
	//variabile che memorizza l'input inserito nella ricerca da lista Progetti e serve per mantenerlo durante
	//consultazione del dettaglio prima di tornare alla ricerca o fino a nuova ricerca
	private String lastInputInProgettiSearch = "";

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
	
	@Autowired
	private TaskService taskService;
	
			@GetMapping("/Progetti")
			public String listaProgetti(Model model) {
				
				//essendo fuori dalla modalità search reinizializzo la varibile
				searchMode = false;
				
				//reinizzializzazione variabili per memorizz input,ultima paginavisitate usate 
				//nel dettaglio progetto selezionato dalla modalità ricerca
				lastInputInProgettiSearch = "";
				lastVisitedPageInProgettiSearch = 1;
				
				//passo al model i contatore e task in uso (gli static)
				model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
				model.addAttribute("taskInUso", ContatoreController.taskInUso);
				
				//passo al model la scelta effettuata per ordinamento lista progetti per data o cliente
				model.addAttribute("ordinaElencoProgettiPerData", ordinaElencoProgettiPerData);
				model.addAttribute("ordinaElencoProgettiPerCliente", ordinaElencoProgettiPerCliente);
				
				//invio al model il booleano del contatore attivato
				//se contatoreAttivato = true avvio animazione su titolo task al contatore;
				model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
				
				//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
				taskService.informationFromTaskInUsoToModel(model);
				
				//passa al model la lista di tutti i task esclusi quelli chiusi
				List<Task> taskList = new ArrayList<Task> ();
				taskList = repositTask.findAllNotClosed();
				model.addAttribute("taskList", taskList);
				
				//restituisce al model questo valore booleano false se non ci sono progetti a db
				//e restituisce true se ci sono progetti a db
				boolean areProjectsOnDb = false;
				if(!repositProgetto.findAll().isEmpty()) {
					areProjectsOnDb = true;
				}
				model.addAttribute("areProjectsOnDb", areProjectsOnDb);
				
				//se siamo ad inizio sessione currentPageListaProgetti == 1 altrimenti terrà in memoria l'ultima pagina visitata
				return getOnePage(currentPageListaProgetti, model);
			}
			
			@GetMapping("/Progetti/page/{pageNumber}")
			public String getOnePage(@PathVariable("pageNumber") int currentPage, Model model ) {
					
					//aggiorna la variabile che memorizza l'ultima pagina visitata
					currentPageListaProgetti = currentPage;
					
					//essendo fuori dalla modalità search reinizializzo la varibile
					searchMode = false;
					model.addAttribute("searchMode", searchMode);
		
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
					
					//passo al model la scelta effettuata per ordinamento lista progetti per data o cliente
					model.addAttribute("ordinaElencoProgettiPerData", ordinaElencoProgettiPerData);
					model.addAttribute("ordinaElencoProgettiPerCliente", ordinaElencoProgettiPerCliente);
		
					//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
					ContatoreController.contatoreAttivato = false;
					
					//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
					taskService.informationFromTaskInUsoToModel(model);
					
					//passa al model la lista di tutti i task esclusi quelli chiusi
					List<Task> taskList = new ArrayList<Task> ();
					taskList = repositTask.findAllNotClosed();
					model.addAttribute("taskList", taskList);
					
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

				//passo al model questo booleano per dirgli che siamo in modalità search
				searchMode = true;
				model.addAttribute("searchMode", searchMode);
				
				//passo al model i contatore e task in uso (gli static)
				model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
				model.addAttribute("taskInUso", ContatoreController.taskInUso);
				
				//invio al model il booleano del contatore attivato
				//se contatoreAttivato = true avvio animazione su titolo task al contatore;
				model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
				
				//passo al model la scelta effettuata per ordinamento lista progetti per data o cliente
				model.addAttribute("ordinaElencoProgettiPerData", ordinaElencoProgettiPerData);
				model.addAttribute("ordinaElencoProgettiPerCliente", ordinaElencoProgettiPerCliente);
		
				//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
				//ContatoreController.contatoreAttivato = false;
				
				//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
				taskService.informationFromTaskInUsoToModel(model);
				
				//passa al model la lista di tutti i task esclusi quelli chiusi
				List<Task> taskList = new ArrayList<Task> ();
				taskList = repositTask.findAllNotClosed();
				model.addAttribute("taskList", taskList);
				
				//restituisce al model questo valore booleano false se non ci sono progetti a db
				//e restituisce true se ci sono progetti a db
				boolean areProjectsOnDb = false;
				if(!repositProgetto.findAll().isEmpty()) {
					areProjectsOnDb = true;
				}
				model.addAttribute("areProjectsOnDb", areProjectsOnDb);
				
				
				//assegnazione variabile per memorizz input che sarà usata nel dettaglio progetto selezionato dalla modalità ricerca
				lastInputInProgettiSearch = input;
				lastVisitedPageInProgettiSearch = 1;
				
				return progettoBySearch(lastVisitedPageInProgettiSearch, input, model);
			} 
				 
			 @GetMapping("/progetto-search-input={input}/page/{numberPage}")
			 public String progettoBySearch(@PathVariable("numberPage") int currentPage, @PathVariable("input") String input,
					 	Model model) {
				 
				 	//passo al model l'input inserito per mostrare all'utente cosa ha inserito come input
				 	model.addAttribute("inputInserito", input);				 
				 	//passo al model questo booleano per dirgli che siamo in modalità search
				 	searchMode = true;
				 	model.addAttribute("searchMode", searchMode);
				 	
				 	//assegnazione variabile per memorizz pagina corrente che sarà usata nel dettaglio progetto selezionato dalla modalità ricerca
				 	lastVisitedPageInProgettiSearch = currentPage;

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
					
					//passo al model la scelta effettuata per ordinamento lista progetti per data o cliente
					model.addAttribute("ordinaElencoProgettiPerData", ordinaElencoProgettiPerData);
					model.addAttribute("ordinaElencoProgettiPerCliente", ordinaElencoProgettiPerCliente);
		
					//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
					ContatoreController.contatoreAttivato = false;
					
					//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
					taskService.informationFromTaskInUsoToModel(model);
					
					//passa al model la lista di tutti i task esclusi quelli chiusi
					List<Task> taskList = new ArrayList<Task> ();
					taskList = repositTask.findAllNotClosed();
					model.addAttribute("taskList", taskList);
					
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
				Progetto progetto = repositProgetto.getReferenceById(progettoId);
				
				model.addAttribute("progetto", progetto);
				
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
	
				//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
				taskService.informationFromTaskInUsoToModel(model);
				
				//passa al model la lista di tutti i task esclusi quelli chiusi
				List<Task> taskList = new ArrayList<Task> ();
				taskList = repositTask.findAllNotClosed();
				model.addAttribute("taskList", taskList);
				
				//passa a modello nel caso in base alla tipologia i risultati delle statistiche
				progettoService.calcoloStatisticheTipologiaFromProgettoToModel(progetto, model);
				
				//se si arriva al dettaglio progetto dalla ricerca su lista progetti passo al model
				// questo booleano per dirgli che siamo in modalità search, l'ultima pagina visita in search 
				//e l'input inserito (variabili inizializzata ad inizio controller) che verranno usati nel button dedicato
				//per tornare alla ricerca
				if(searchMode == true) {
					model.addAttribute("searchMode", searchMode);
					model.addAttribute("lastVisitedPageInProgettiSearch", lastVisitedPageInProgettiSearch);
					model.addAttribute("lastInputInProgettiSearch", lastInputInProgettiSearch);
				}
				
				
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
				
				//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
				taskService.informationFromTaskInUsoToModel(model);
				
				//passa al model la lista di tutti i task esclusi quelli chiusi
				List<Task> taskList = new ArrayList<Task> ();
				taskList = repositTask.findAllNotClosed();
				model.addAttribute("taskList", taskList);
				
				//invio al model il booleano del contatore attivato
				//se contatoreAttivato = true avvio animazione su titolo task al contatore;
				model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
				//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
				ContatoreController.contatoreAttivato = false;
				
				//metodo che passa al model i valori inerenti la tipologia progetto per le statistiche
				progettoService.tipologiaFromProgettoToModel(formProgetto, model);
				
				//restituisce al model questo valore booleano false se non ci sono clienti a db
				//e restituisce true se ci sono clienti a db
				boolean areClientsOnDb = false;
				if(!repositClient.findAll().isEmpty()) {
					areClientsOnDb = true;
				}
				model.addAttribute("areClientsOnDb", areClientsOnDb);
				
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
						
						//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
						taskService.informationFromTaskInUsoToModel(model);
						
						//passa al model la lista di tutti i task esclusi quelli chiusi
						List<Task> taskList = new ArrayList<Task> ();
						taskList = repositTask.findAllNotClosed();
						model.addAttribute("taskList", taskList);
						
						//metodo che passa al model i valori inerenti la tipologia progetto per le statistiche
						progettoService.tipologiaFromProgettoToModel(formProgetto, model);
					
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
				
				//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
				taskService.informationFromTaskInUsoToModel(model);
				
				//passa al model la lista di tutti i task esclusi quelli chiusi
				List<Task> taskList = new ArrayList<Task> ();
				taskList = repositTask.findAllNotClosed();
				model.addAttribute("taskList", taskList);
				
				//invio al model il booleano del contatore attivato
				//se contatoreAttivato = true avvio animazione su titolo task al contatore;
				model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
				//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
				ContatoreController.contatoreAttivato = false;
				
				//metodo che passa al model i valori inerenti la tipologia progetto per le statistiche
				progettoService.tipologiaFromProgettoToModel(formProgetto, model);
				
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
					
					//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
					taskService.informationFromTaskInUsoToModel(model);
					
					//passa al model la lista di tutti i task esclusi quelli chiusi
					List<Task> taskList = new ArrayList<Task> ();
					taskList = repositTask.findAllNotClosed();
					model.addAttribute("taskList", taskList);
					
					//metodo che passa al model i valori inerenti la tipologia progetto per le statistiche
					progettoService.tipologiaFromProgettoToModel(formProgetto, model);
					
					return  "/Progetti/freelapp-editProgetto";
				}
 
				formProgetto.setDataModifica(LocalDateTime.now());
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
				progetto.setDataModifica(LocalDateTime.now());
				repositProgetto.save(progetto);
				
				return "redirect:/Progetti";
				
			}
			
			@PostMapping("/Progetti/de-archivia/{id}")
			public String deArchiviaProgetto(@PathVariable("id") Integer id, Model model) {
				
				Progetto progetto = repositProgetto.findById(id).get();
				
				progetto.setArchivia(false);
				model.addAttribute("progetto",progetto);
				progetto.setDataModifica(LocalDateTime.now());
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
				
				//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
				taskService.informationFromTaskInUsoToModel(model);
				
				//passa al model la lista di tutti i task esclusi quelli chiusi
				List<Task> taskList = new ArrayList<Task> ();
				taskList = repositTask.findAllNotClosed();
				model.addAttribute("taskList", taskList);
				
				//restituisce al model questo valore booleano false se non ci sono progetti archiviati a db
				//e restituisce true se ci sono progetti a db
				boolean areProjectsArchivedOnDb = false;
					if(!repositProgetto.findByArchivia(true).isEmpty()) {
						areProjectsArchivedOnDb = true;
					}
				
				model.addAttribute("areProjectsArchivedOnDb", areProjectsArchivedOnDb);
				
			
				return progettiArchivioPagination(1, model);
			}
			
			@GetMapping("/Progetti/archivio/page/{pageNumber}")
			public String progettiArchivioPagination(@PathVariable("pageNumber") int currentPage, Model model) {
				
				//passaggio al model della lista dei progetti archiviati
				List<Progetto> progettiArchiviati = repositProgetto.findByArchivia(true);
				model.addAttribute("progettiArchiviati",progettiArchiviati);
				
				
				Page<Progetto> pageByArchivio = progettoService.findByArchivio(currentPage);
				int totalPageByArchivio = pageByArchivio.getTotalPages();	
				long totalItemByArchivio = pageByArchivio.getTotalElements();		
				List<Progetto> listProgettiByArchivio = pageByArchivio.getContent();
				model.addAttribute("currentPage", currentPage);
				
				model.addAttribute("totalPageByArchivio", totalPageByArchivio);					
				model.addAttribute("totalItemByArchivio", totalItemByArchivio);					
				model.addAttribute("listProgettiByArchivio", listProgettiByArchivio);
				
				contatoreservice.importContatoreInGet(model);

				//passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
				if(currentPage != 0) {
					String endPoint = "/Progetti/archivio/page/" + currentPage;
					
					model.addAttribute("endPoint", endPoint);						
				} else {
					String endPoint = "/Progetti/archivio";
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
				
				//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
				taskService.informationFromTaskInUsoToModel(model);
				
				//passa al model la lista di tutti i task esclusi quelli chiusi
				List<Task> taskList = new ArrayList<Task> ();
				taskList = repositTask.findAllNotClosed();
				model.addAttribute("taskList", taskList);
				
				//restituisce al model questo valore booleano false se non ci sono progetti a db
				//e restituisce true se ci sono progetti archiviati a db
				boolean areProjectsArchivedOnDb = false;
					if(!repositProgetto.findByArchivia(true).isEmpty()) {
						areProjectsArchivedOnDb = true;
					}
				model.addAttribute("areProjectsArchivedOnDb", areProjectsArchivedOnDb);
				
				return "/Progetti/freelApp-archivioProgetti";
				
			}
			
			// FILTRO DI RICERCA PER PROGETTI ARCHIVIATI
			
			@GetMapping("/progetto-archivio-search")
			public String listaProgettiArchiviatiSearch(@Param("input") String input, Model model) {
				
				//passo al model i contatore e task in uso (gli static)
				model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
				model.addAttribute("taskInUso", ContatoreController.taskInUso);
				
				//invio al model il booleano del contatore attivato
				//se contatoreAttivato = true avvio animazione su titolo task al contatore;
				model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);

				//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
				taskService.informationFromTaskInUsoToModel(model);
				
				//passa al model la lista di tutti i task esclusi quelli chiusi
				List<Task> taskList = new ArrayList<Task> ();
				taskList = repositTask.findAllNotClosed();
				model.addAttribute("taskList", taskList);
				
				//restituisce al model questo valore booleano false se non ci sono progetti a db
				//e restituisce true se ci sono progetti archiviati a db
				boolean areProjectsArchivedOnDb = false;
					if(!repositProgetto.findByArchivia(true).isEmpty()) {
						areProjectsArchivedOnDb = true;
					}
				model.addAttribute("areProjectsArchivedOnDb", areProjectsArchivedOnDb);
				
				return progettoBySearchArchiviati(1, input, model);
			} 
				 
			 @GetMapping("/progetto-archivio-search/page/{numberPage}")
			 public String progettoBySearchArchiviati(@PathVariable("pageNumber") int currentPage, String input,
					 	Model model) {
				 
					Page<Progetto> pageByArchivio = progettoService.findSearchedPageByArchiviati(true, currentPage,input);
					int totalPageByArchivio = pageByArchivio.getTotalPages();	
					long totalItemByArchivio = pageByArchivio.getTotalElements();		
					List<Progetto> listProgettiByArchivio = pageByArchivio.getContent();
					
					model.addAttribute("currentPage", currentPage);
					model.addAttribute("progettiArchiviati", listProgettiByArchivio);					
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

					ContatoreController.contatoreAttivato = false;
					model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
					model.addAttribute("taskInUso", ContatoreController.taskInUso);
					model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
					//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
					taskService.informationFromTaskInUsoToModel(model);
					
					//passa al model la lista di tutti i task esclusi quelli chiusi
					List<Task> taskList = new ArrayList<Task> ();
					taskList = repositTask.findAllNotClosed();
					model.addAttribute("taskList", taskList);
					
					boolean areProjectsArchivedOnDb = false;
					if(!repositProgetto.findByArchivia(true).isEmpty()) {
						areProjectsArchivedOnDb = true;
					}
				
					model.addAttribute("areProjectsArchivedOnDb", areProjectsArchivedOnDb);
						
				return "/Progetti/freelApp-archivioProgetti";
			}
			 
			 
			@PostMapping("/Progetti/chiudi/{id}")
			public String chiudiProgetto(@PathVariable("id") Integer id, Model model) {
				
				Progetto progetto = repositProgetto.findById(id).get();
				
				
				//Creo una lista di tutti i task del progetto
				List<Task> taskProgettoList = new ArrayList<Task>();
				taskProgettoList = repositTask.findByProgettoId(id);
				
				//metodo del service che mette in pausa e in stop tutti i contatori attivi 
				//del progetto da chiudere
				contatoreservice.pauseAndStopTimersForClosingProject(taskProgettoList);
				
				//per ogni task del progetto da chiudere viene settato lo stato in chiuso e la data di 
				//chiusura definitiva
				taskProgettoList.forEach(task -> {
					task.setStato("chiuso");
					task.setDataChiusuraDefinitiva(LocalDate.now());
					repositTask.save(task);
				});
				
				progetto.setDataFine(LocalDate.now());
				progetto.setDataModifica(LocalDateTime.now());
				repositProgetto.save(progetto);
				
				//tolgo dallo static il task/contatore che erano in uso se appartenenti al progetto chiuso
				ContatoreController.contatoreInUso = null;
				ContatoreController.taskInUso = null;
				
				return "redirect:/Progetti";
				
			}
			
			@PostMapping("/Progetti/apri/{id}")
			public String apriProgetto(@PathVariable("id") Integer id, Model model) {
				
				Progetto progetto = repositProgetto.findById(id).get();
				//setta la data di fine progetto = null così da essere aperto di nuovo
				progetto.setDataFine(null);
				progetto.setDataModifica(LocalDateTime.now());
				repositProgetto.save(progetto);
				
				return "redirect:/Progetti";
				
			}
			
			
			
}
