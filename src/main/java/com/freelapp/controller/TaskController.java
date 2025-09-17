package com.freelapp.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import com.freelapp.model.Contatore;
import com.freelapp.model.Progetto;
import com.freelapp.model.SessioniTask;
import com.freelapp.model.Task;
import com.freelapp.repository.ClienteRepository;
import com.freelapp.repository.ProgettoRepository;
import com.freelapp.repository.SessionitaskRepository;
//import com.freelapp.model.Stato;
//import com.freelapp.repository.StatoRepository;
import com.freelapp.repository.TaskRepository;
import com.freelapp.service.ContatoreService;
import com.freelapp.service.TaskService;

import jakarta.validation.Valid;

@Controller
public class TaskController {
	
	//static per la gestione dei filtri lista progetto
	public static boolean filtriAttiviInListaTask = false;
	public static String statoTaskInListaTask = "";
	public static String ordinaTaskInListaTask = "";
	public static int clienteIdTaskInListaTask = -1;
	public static int progettoIdTaskInListaTask = -1;
	public static String dataPerOrdinamentoTask = "";
	
	//variabile che passo al model del search task per dirgli che siamo in modalità search
	private boolean searchMode = false;
	
	//variabile che memorizza l'ultima pagina consultata nella lista Task e serve per mantenerla durante la sessione
	private int currentPageListaTask = 1;
	
	//variabile che memorizza l'ultima pagina consultata nella ricerca da lista Task e serve per mantenerla durante
	//consultazione del dettaglio prima di tornare alla ricerca o fino a nuova ricerca
	private int lastVisitedPageInTaskSearch = 1;
	
	//variabile che memorizza l'input inserito nella ricerca da lista task e serve per mantenerlo durante
	//consultazione del dettaglio prima di tornare alla ricerca o fino a nuova ricerca
	private String lastInputInTaskSearch = "";

    @Autowired
    private TaskRepository repositTask;

    @Autowired
    private ProgettoRepository repositProgetto;
    
    @Autowired
    private ClienteRepository repositoryCliente;

    @Autowired
    private TaskService taskService;
    
    @Autowired
    private ContatoreService contatoreservice;
    
    @Autowired
    private SessionitaskRepository sessioniTaskRepo;

//	@Autowired
//	private StatoRepository repositStato;

    @GetMapping("/Task")
    public String iMieiTask(Model model) {
    	
    	//essendo fuori dalla modalità search reinizializzo la varibile
		searchMode = false;
		model.addAttribute("searchMode", searchMode);
		
		//reinizzializzazione variabili per memorizz input,ultima paginavisitate usate 
		//nel dettaglio progetto selezionato dalla modalità ricerca
		lastInputInTaskSearch = "";
		lastVisitedPageInTaskSearch = 1;
    	
		//passo al model i contatore e task in uso (gli static)
		model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
		model.addAttribute("taskInUso", ContatoreController.taskInUso);
		model.addAttribute("contatoreAttivatoDaRapidButton", ContatoreController.contatoreAttivatoDaRapidButton);
		model.addAttribute("filtriAttiviInListaTask", filtriAttiviInListaTask);
		model.addAttribute("filtroIdClienteSelezionato", clienteIdTaskInListaTask);
		model.addAttribute("filtroIdProgettoSelezionato", progettoIdTaskInListaTask);
		
		//metodo del serviceProgetto che passa al model la stringa per indicare all'utente i filtri selezionati
		taskService.stringaFiltriInListaTask(model);
		
		//invio al model il booleano del contatore attivato
		//se contatoreAttivato = true avvio animazione su titolo task al contatore;
		model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
		//inizializzo a false così al reload successivo js non genera i tasti del contatore
		ContatoreController.contatoreAttivatoDaRapidButton = false;
		
		// invio al model il booleano del contatore cliccato prima del refresh pagina
		// se contatoreCliccatoPreRefresh = true avvio animazione che porta la schermata in basso su mobile;
		model.addAttribute("contatoreCliccatoPreRefresh", ContatoreController.contatoreCliccatoPreRefresh);

		// inizializzo a false così che al refresh esegue animazione solo se era stato cliccato in precedenza
		ContatoreController.contatoreCliccatoPreRefresh = false;
		
		//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
		taskService.informationFromTaskInUsoToModel(model);
		
		// restituisce al model questo valore booleano false se non ci sono progetti a db
		// e restituisce true se ci sono progetti a db
		boolean areTasksOnDb = false;
		if (!repositTask.findAllNotClosed().isEmpty()) {
			areTasksOnDb = true;
		}
		model.addAttribute("areTasksOnDb", areTasksOnDb);
		
		
		// lista che restituisce i task per modale delle ore lavorate
				List<Task> taskListOreLavorate = new ArrayList<Task> ();
				List<Task> taskList = repositTask.findAll(Sort.by(Sort.Direction.DESC, "Name"));
				taskList.forEach( task -> {
					
					
						if( task.getContatore() == null) {
						taskListOreLavorate.add(task);
						
								}
						
						else if( task.getContatore() != null) {
							Boolean contatoreAttivo;
							contatoreAttivo = contatoreservice.contatoreIsRun(task);
				
							if(task.getContatore().getStop() == null  && contatoreAttivo == false) {
							
									 taskListOreLavorate.add(task); 
											
									 }	
								}
					model.addAttribute("taskListOreLavorate", taskListOreLavorate);
						} );
			//contatoreservice.importContatoreInGet(model);
	//se siamo ad inizio sessione currentPageListaTask == 1 altrimenti terrà in memoria l'ultima pagina visitata
	return getOnePage(currentPageListaTask, model);
    }

    
    @GetMapping("/Task/page/{pageNumber}")
    public String getOnePage(@PathVariable("pageNumber") int currentPage, Model model) {
    	
    	//aggiorna la variabile che memorizza l'ultima pagina visitata
		currentPageListaTask = currentPage;
					
		//essendo fuori dalla modalità search reinizializzo la varibile
		searchMode = false;
		model.addAttribute("searchMode", searchMode);

		Page<Task> page = taskService.findPage(currentPage);
	
		int totalPages = page.getTotalPages();
	
		long totalItems = page.getTotalElements();
	
		List<Task> listTask = page.getContent();
		
	
		model.addAttribute("list", listTask);
	
		model.addAttribute("currentPage", currentPage);
	
		model.addAttribute("totalPages", totalPages);
	
		model.addAttribute("totalItems", totalItems);
	
		contatoreservice.importContatoreInGet(model);
		
		//passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
		if(currentPage != 0) {
			String endPoint = "/Task/page/" + currentPage;
			
			model.addAttribute("endPoint", endPoint);						
		} else {
			String endPoint = "/Task";
			model.addAttribute("endPoint", endPoint);	
		}
		
		//passo al model i contatore e task in uso (gli static)
		model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
		model.addAttribute("taskInUso", ContatoreController.taskInUso);
		model.addAttribute("contatoreAttivatoDaRapidButton", ContatoreController.contatoreAttivatoDaRapidButton);
		model.addAttribute("filtriAttiviInListaTask", filtriAttiviInListaTask);
		model.addAttribute("filtroIdClienteSelezionato", clienteIdTaskInListaTask);
		model.addAttribute("filtroIdProgettoSelezionato", progettoIdTaskInListaTask);
		
		//metodo del serviceProgetto che passa al model la stringa per indicare all'utente i filtri selezionati
		taskService.stringaFiltriInListaTask(model);
		
		//invio al model il booleano del contatore attivato
		//se contatoreAttivato = true avvio animazione su titolo task al contatore;
		model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
		//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
		ContatoreController.contatoreAttivato = false;
		
		//inizializzo a false così al reload successivo js non genera i tasti del contatore
		ContatoreController.contatoreAttivatoDaRapidButton = false;
		
		// invio al model il booleano del contatore cliccato prima del refresh pagina
		// se contatoreCliccatoPreRefresh = true avvio animazione che porta la schermata in basso su mobile;
		model.addAttribute("contatoreCliccatoPreRefresh", ContatoreController.contatoreCliccatoPreRefresh);

		// inizializzo a false così che al refresh esegue animazione solo se era stato cliccato in precedenza
		ContatoreController.contatoreCliccatoPreRefresh = false;
		
		//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
		taskService.informationFromTaskInUsoToModel(model);
	
			// restituisce al model questo valore booleano false se non ci sono progetti a db
			// e restituisce true se ci sono progetti a db
			boolean areTasksOnDb = false;
			if (!repositTask.findAllNotClosed().isEmpty()) {
				areTasksOnDb = true;
			}
			model.addAttribute("areTasksOnDb", areTasksOnDb);
	
		return "/Task/freelApp-listaTask";
    }

    
    
    @GetMapping("/task-search")
    public String listaTaskSearch(@Param("input") String input, Model model) {
    	
    	//passo al model questo booleano per dirgli che siamo in modalità search
		searchMode = true;
		model.addAttribute("searchMode", searchMode);
  
    	//passo al model i contatore e task in uso (gli static)
		model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
		model.addAttribute("taskInUso", ContatoreController.taskInUso);
		model.addAttribute("contatoreAttivatoDaRapidButton", ContatoreController.contatoreAttivatoDaRapidButton);
		
		//invio al model il booleano del contatore attivato
		//se contatoreAttivato = true avvio animazione su titolo task al contatore;
		model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
		//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
		taskService.informationFromTaskInUsoToModel(model);
		
		//invio al model il booleano del contatore attivato
		//se contatoreAttivato = true avvio animazione su titolo task al contatore;
		model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
		//inizializzo a false così al reload successivo js non genera i tasti del contatore
		ContatoreController.contatoreAttivatoDaRapidButton = false;
		
		//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
//		ContatoreController.contatoreAttivato = false;
		
		// invio al model il booleano del contatore cliccato prima del refresh pagina
		// se contatoreCliccatoPreRefresh = true avvio animazione che porta la schermata in basso su mobile;
		model.addAttribute("contatoreCliccatoPreRefresh", ContatoreController.contatoreCliccatoPreRefresh);

		// inizializzo a false così che al refresh esegue animazione solo se era stato cliccato in precedenza
		ContatoreController.contatoreCliccatoPreRefresh = false;

		// restituisce al model questo valore booleano false se non ci sono progetti a db
		// e restituisce true se ci sono progetti a db
		boolean areTasksOnDb = false;
		if (!repositTask.findAllNotClosed().isEmpty()) {
			areTasksOnDb = true;
		}
		model.addAttribute("areTasksOnDb", areTasksOnDb);
		
		//assegnazione variabile per memorizz input che sarà usata nel dettaglio progetto selezionato dalla modalità ricerca
		lastInputInTaskSearch = input;
		lastVisitedPageInTaskSearch = 1;

	return taskBySearch(lastVisitedPageInTaskSearch, input, model);
    }

    @GetMapping("/task-search-input={input}/page/{numberPage}")
    public String taskBySearch(@PathVariable("numberPage") int currentPage,  @PathVariable("input") String input,
    			Model model) {
    	
    	//passo al model l'input inserito per mostrare all'utente cosa ha inserito come input
		model.addAttribute("inputInserito", input);				 
		//passo al model questo booleano per dirgli che siamo in modalità search
		searchMode = true;
		model.addAttribute("searchMode", searchMode);
		
		//assegnazione variabile per memorizz pagina corrente che sarà usata nel dettaglio taskselezionato dalla modalità ricerca
		lastVisitedPageInTaskSearch = currentPage;

		Page<Task> page = taskService.findSearchedPage(currentPage, input);
	
		int totalPages = page.getTotalPages();
	
		long totalItems = page.getTotalElements();
	
		List<Task> listaTaskSearch = page.getContent();
	
		model.addAttribute("currentPage", currentPage);
	
		model.addAttribute("totalPages", totalPages);
	
		model.addAttribute("totalItems", totalItems);
	
		model.addAttribute("list", listaTaskSearch);
		
		contatoreservice.importContatoreInGet(model);
		
		//passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
		if(input != null) {
			String endPoint = "/task-search?input=" + input;
			
			model.addAttribute("endPoint", endPoint);						
		} else {
			String endPoint = "/task-search";
			model.addAttribute("endPoint", endPoint);
		}
		
		//passo al model i contatore e task in uso (gli static)
		model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
		model.addAttribute("taskInUso", ContatoreController.taskInUso);
		model.addAttribute("contatoreAttivatoDaRapidButton", ContatoreController.contatoreAttivatoDaRapidButton);
		
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
		
		//inizializzo a false così al reload successivo js non genera i tasti del contatore
		ContatoreController.contatoreAttivatoDaRapidButton = false;
		
		// restituisce al model questo valore booleano false se non ci sono progetti a db
		// e restituisce true se ci sono progetti a db
		boolean areTasksOnDb = false;
		if (!repositTask.findAllNotClosed().isEmpty()) {
			areTasksOnDb = true;
		}
		model.addAttribute("areTasksOnDb", areTasksOnDb);

	return "/Task/freelApp-listaTask";
    }
    
    
    @PostMapping("/task-lista-filtri")
			public String filtriListaProgetto(Model model, @ModelAttribute("statoTask") String statoTask,
					@ModelAttribute("ordinaTask") String ordinaTask, @ModelAttribute("dataOrdinamentoTask") String dataOrdinamentoTask,
					@ModelAttribute("clienteSelezionatoIdPerBackEnd") Integer clienteSelezionatoIdPerBackEnd, @ModelAttribute("progettoSelezionatoId") Integer progettoSelezionatoId) {
				 
				filtriAttiviInListaTask = true;
				System.out.println("filtriAttiviInListaProgetto: " + filtriAttiviInListaTask);
				statoTaskInListaTask = statoTask;
					System.out.println("statoProgetto: " + statoTaskInListaTask);
				ordinaTaskInListaTask = ordinaTask;
					System.out.println("ordinaProgetto: " + ordinaTaskInListaTask);
				dataPerOrdinamentoTask = dataOrdinamentoTask;
				System.out.println("dataOrdinamentoProgetto: " + dataPerOrdinamentoTask);
				clienteIdTaskInListaTask = clienteSelezionatoIdPerBackEnd;
					System.out.println("clienteId: " + clienteIdTaskInListaTask);
				progettoIdTaskInListaTask = progettoSelezionatoId;
					System.out.println("progettoId: " + progettoIdTaskInListaTask);
				
				//riporto la lista alla prima pagina
				currentPageListaTask = 1;
					
				return "redirect:/Task";
			}
    
    
    @PostMapping("/task-list-filtri/reset")
			public String resetFiltriProgetto(Model model) {
				
				filtriAttiviInListaTask = false;
				System.out.println("filtriAttiviInListaProgetto: " + filtriAttiviInListaTask);
				statoTaskInListaTask = "";
					System.out.println("statoProgetto: " + statoTaskInListaTask);
				ordinaTaskInListaTask = "";
					System.out.println("statoProgetto: " + ordinaTaskInListaTask);
				clienteIdTaskInListaTask = -1;
					System.out.println("clienteId: " + clienteIdTaskInListaTask);
				progettoIdTaskInListaTask = -1;
					System.out.println("clienteId: " + clienteIdTaskInListaTask);
				dataPerOrdinamentoTask = "";
					System.out.println("dataOrdinamentoProgetto: " + dataPerOrdinamentoTask);
					
				model.addAttribute("filtriAttiviInListaProgetto", filtriAttiviInListaTask);
				
				//riporto la lista alla prima pagina
				currentPageListaTask = 1;
				
				return "redirect:/Task";
			}
    

    @GetMapping("/Task/{id}")
    public String descrizioneTask(@PathVariable("id") int taskId, Model model) {

		Task task = repositTask.getReferenceById(taskId);
		
		List <SessioniTask> sessioniTask = sessioniTaskRepo.findByContatoreId(taskId);
	
		model.addAttribute("task", task);
		model.addAttribute("sessioniTask", sessioniTask);

		// passo il finaltime formattato per la voce "timer" di tipo string sul
		// dettaglio task

		if (task.getContatore() != null) {
			String timeInHHMMSS = taskService.Timer(task);
			model.addAttribute("timeInHHMMSS", timeInHHMMSS);
		}

		//  passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
		String endPoint = "/Task/" + task.getId();

		model.addAttribute("endPoint", endPoint);

		contatoreservice.importContatoreInGet(model);

		model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
		model.addAttribute("taskInUso", ContatoreController.taskInUso);
		model.addAttribute("contatoreAttivatoDaRapidButton", ContatoreController.contatoreAttivatoDaRapidButton);

		// invio al model il booleano del contatore attivato
		// se contatoreAttivato = true avvio animazione su titolo task al contatore;
		model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);

		// inizializzo a false così che al refresh o cambio pagina non esegue animazione
		// ma solo allo start
		ContatoreController.contatoreAttivato = false;
		
		// invio al model il booleano del contatore cliccato prima del refresh pagina
		// se contatoreCliccatoPreRefresh = true avvio animazione che porta la schermata in basso su mobile;
		model.addAttribute("contatoreCliccatoPreRefresh", ContatoreController.contatoreCliccatoPreRefresh);
		System.out.println("contatoreCliccatoPreRefresh : " + ContatoreController.contatoreCliccatoPreRefresh);

		// inizializzo a false così che al refresh esegue animazione solo se era stato cliccato in precedenza
		ContatoreController.contatoreCliccatoPreRefresh = false;
		
		//inizializzo a false così al reload successivo js non genera i tasti del contatore
		ContatoreController.contatoreAttivatoDaRapidButton = false;
		

		// metodo che passa al model le informazioni sul task in uso per generare la
		// modale STOP
		taskService.informationFromTaskInUsoToModel(model);

		// passa al model la lista di tutti i task esclusi quelli chiusi
		List<Task> taskList = new ArrayList<Task>();
		taskList = repositTask.findAllNotClosed();
		model.addAttribute("taskList", taskList);
		
		//se si arriva al dettaglio progetto dalla ricerca su lista progetti passo al model
		// questo booleano per dirgli che siamo in modalità search, l'ultima pagina visita in search 
		//e l'input inserito (variabili inizializzata ad inizio controller) che verranno usati nel button dedicato
		//per tornare alla ricerca
		if(searchMode == true) {
			model.addAttribute("searchMode", searchMode);
			model.addAttribute("lastVisitedPageInTaskSearch", lastVisitedPageInTaskSearch);
			model.addAttribute("lastInputInTaskSearch", lastInputInTaskSearch);
		}
		
		//sezione che manda al model le statistiche(non grafici)
		String guadagnoAttualeTask = null;
		String pauseTask = null;
		//verifico se il task ha il contatore calcolo il guadagno altrimenti informo utente che non ha contatore attivo
		if(task.getContatore() != null) {
			guadagnoAttualeTask = taskService.calcoloGuadagnoTaskDaFinalTime(task) + " €";
			pauseTask = String.valueOf(task.getContatore().getStop_numbers());
		} else {
			guadagnoAttualeTask = "Task inattivo";
			pauseTask = "-";
		}
		
		model.addAttribute("guadagnoAttualeTask", guadagnoAttualeTask);
		model.addAttribute("pauseTask", pauseTask);
		
		if(task.getDataChiusuraStimata() == null) {
			String dataChiusuraStimataNonDisponibile =  "Inserire data di chiusura stimata per il calcolo";
			model.addAttribute("dataChiusuraStimataNonDisponibile", dataChiusuraStimataNonDisponibile);
		} else {taskService.inLineaConChiusuraStimata(task);}
		
	
	return "/Task/freelapp-descrizioneTask";
    }

    @GetMapping("/Task/insert/progetto-{id}")
    public String insertTask(@PathVariable("id") Integer id,

	    Progetto progetto, Model model) {

    	
	// richiamo il progetto tramite id
	progetto = repositProgetto.getReferenceById(id);

	// istanzio un nuovo task
	Task newTask = new Task();
	
	// attribuisco il task al progetto
	newTask.setProgetto(progetto);

	// riporto nel modello il task
	model.addAttribute("task", newTask);
	
	//riporto al model l'id del progetto in uso
	model.addAttribute("progettoId", progetto.getId());
	
	//riporta al model il nome del progetto in uso
	model.addAttribute("taskProgettoName", progetto.getName());

	//passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
	String endPoint = "/Task/insert/progetto-" ;
	
	model.addAttribute("endPoint", endPoint);
	
	contatoreservice.importContatoreInGet(model);
	//passo al model i contatore e task in uso (gli static)
	model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
	model.addAttribute("taskInUso", ContatoreController.taskInUso);
	model.addAttribute("contatoreAttivatoDaRapidButton", ContatoreController.contatoreAttivatoDaRapidButton);
	
	//inizializzo a false così al reload successivo js non genera i tasti del contatore
	ContatoreController.contatoreAttivatoDaRapidButton = false;
	
	
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
	
	// invio al model il booleano del contatore cliccato prima del refresh pagina
	// se contatoreCliccatoPreRefresh = true avvio animazione che porta la schermata in basso su mobile;
	model.addAttribute("contatoreCliccatoPreRefresh", ContatoreController.contatoreCliccatoPreRefresh);

	// inizializzo a false così che al refresh esegue animazione solo se era stato cliccato in precedenza
	ContatoreController.contatoreCliccatoPreRefresh = false;
	
	return "/Task/freelapp-insertTask";
    }

    @PostMapping("/Task/insert/progetto")
    public String storeTask(@Valid @ModelAttribute("task") Task task,
	    BindingResult bindingResult, Model model) {


	Progetto progetto = task.getProgetto();
	
	if (bindingResult.hasErrors()) {
		
		model.addAttribute("progetto", progetto);

		//passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
		String endPoint = "/Task/insert/progetto" ;
		
		model.addAttribute("endPoint", endPoint);
		
		contatoreservice.importContatoreInGet(model);
		//passo al model i contatore e task in uso (gli static)
		model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
		model.addAttribute("taskInUso", ContatoreController.taskInUso);
		model.addAttribute("contatoreAttivatoDaRapidButton", ContatoreController.contatoreAttivatoDaRapidButton);
	
		//inizializzo a false così al reload successivo js non genera i tasti del contatore
		ContatoreController.contatoreAttivatoDaRapidButton = false;
		
		//riporto al model l'id del progetto in uso
		model.addAttribute("progettoId", progetto.getId());
	
		//riporta al model il nome del progetto in uso
		model.addAttribute("taskProgettoName", progetto.getName());

		
		//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
		taskService.informationFromTaskInUsoToModel(model);
		
		//passa al model la lista di tutti i task esclusi quelli chiusi
		List<Task> taskList = new ArrayList<Task> ();
		taskList = repositTask.findAllNotClosed();
		model.addAttribute("taskList", taskList);

	    return "/Task/freelapp-insertTask";
	}

	//aggiungo gruppo dataOra di ultima modifica che coincide con la creazione per visualizzare task in alto 
	//negli elenchi ordinati per data di modifica
	task.setDataModifica(LocalDateTime.now());
	
	// salvo il task
	repositTask.save(task);

	return "redirect:/Task";
    }

    // Inserimento nuovo Task da tasto rapido (senza progetto agganciato)

    @GetMapping("/Task/newTask")
    private String newTaskWithoutProgetto(Model model) {
    	

	// istanzio un nuovo task
	Task newTask = new Task();

	// riporto nel modello il task
	model.addAttribute("task", newTask);

	// riporto nel modello l'elenco dei progetti attivi
	model.addAttribute("listaProgetti", repositProgetto.findByActiveProject());

	//  passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
	String endPoint = "/Task/newTask";
	model.addAttribute("endPoint", endPoint);
	
	contatoreservice.importContatoreInGet(model);
	
	//passo al model i contatore e task in uso (gli static)
	model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
	model.addAttribute("taskInUso", ContatoreController.taskInUso);
	model.addAttribute("contatoreAttivatoDaRapidButton", ContatoreController.contatoreAttivatoDaRapidButton);
	
	//inizializzo a false così al reload successivo js non genera i tasti del contatore
	ContatoreController.contatoreAttivatoDaRapidButton = false;
	
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
	
	// invio al model il booleano del contatore cliccato prima del refresh pagina
	// se contatoreCliccatoPreRefresh = true avvio animazione che porta la schermata in basso su mobile;
	model.addAttribute("contatoreCliccatoPreRefresh", ContatoreController.contatoreCliccatoPreRefresh);

	// inizializzo a false così che al refresh esegue animazione solo se era stato cliccato in precedenza
	ContatoreController.contatoreCliccatoPreRefresh = false;
	
	//restituisce al model questo valore booleano false se non ci sono clienti a db
	//e restituisce true se ci sono clienti a db
	boolean areClientsOnDb = false;
	if(!repositoryCliente.findAll().isEmpty()) {
			areClientsOnDb = true;
	}
	model.addAttribute("areClientsOnDb", areClientsOnDb);
	
	// restituisce al model questo valore booleano false se non ci sono progetti a
	// db
	// e restituisce true se ci sono progetti a db
	boolean areProjectsOnDb = false;
	if (!repositProgetto.findAll().isEmpty()) {
		areProjectsOnDb = true;
	}
	model.addAttribute("areProjectsOnDb", areProjectsOnDb);
	
	return "/Task/freelapp-insertTask-noProgetto";
    }

    @PostMapping("/Task/newTask")
    private String saveNewTaskWithoutProgetto(@Valid @ModelAttribute("task") Task task, BindingResult bindingResult,
	    Model model) {
    	

	if (bindingResult.hasErrors()) {

	    // riporto nel modello l'elenco dei progetti disponibili
	    model.addAttribute("listaProgetti", repositProgetto.findAll());
	    
	    //  passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
		String endPoint = "/Task/newTask";
		model.addAttribute("endPoint", endPoint);
		
		contatoreservice.importContatoreInGet(model);
		
		//passo al model i contatore e task in uso (gli static)
		model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
		model.addAttribute("taskInUso", ContatoreController.taskInUso);
		model.addAttribute("contatoreAttivatoDaRapidButton", ContatoreController.contatoreAttivatoDaRapidButton);
	
		//inizializzo a false così al reload successivo js non genera i tasti del contatore
		ContatoreController.contatoreAttivatoDaRapidButton = false;
		
		//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
		taskService.informationFromTaskInUsoToModel(model);
		
		//passa al model la lista di tutti i task esclusi quelli chiusi
		List<Task> taskList = new ArrayList<Task> ();
		taskList = repositTask.findAllNotClosed();
		model.addAttribute("taskList", taskList);
		
		//restituisce al model questo valore booleano false se non ci sono clienti a db
		//e restituisce true se ci sono clienti a db
		boolean areClientsOnDb = false;
		if(!repositoryCliente.findAll().isEmpty()) {
			areClientsOnDb = true;
		}
		model.addAttribute("areClientsOnDb", areClientsOnDb);
	
		// restituisce al model questo valore booleano false se non ci sono progetti a
		// db
		// e restituisce true se ci sono progetti a db
		boolean areProjectsOnDb = false;
		if (!repositProgetto.findAll().isEmpty()) {
			areProjectsOnDb = true;
		}
		model.addAttribute("areProjectsOnDb", areProjectsOnDb);
	

	    return "/Task/freelapp-insertTask-noProgetto";
	}

	//aggiungo gruppo dataOra di ultima modifica che coincide con la creazione per visualizzare task in alto 
	//negli elenchi ordinati per data di modifica
	task.setDataModifica(LocalDateTime.now());
	
	// salvo il task
	repositTask.save(task);

	return "redirect:/Task";

    }

    @GetMapping("/Task/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {

	Task formTask = repositTask.getReferenceById(id);
	model.addAttribute("formTask", formTask);
	
	//passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
    String endPoint = "/Task/edit/"+ formTask.getId();
    model.addAttribute("endPoint", endPoint);
    
    contatoreservice.importContatoreInGet(model);
    
  //passo al model i contatore e task in uso (gli static)
  	model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
  	model.addAttribute("taskInUso", ContatoreController.taskInUso);
  	model.addAttribute("contatoreAttivatoDaRapidButton", ContatoreController.contatoreAttivatoDaRapidButton);
	
	//inizializzo a false così al reload successivo js non genera i tasti del contatore
	ContatoreController.contatoreAttivatoDaRapidButton = false;
  	
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
	
	// invio al model il booleano del contatore cliccato prima del refresh pagina
	// se contatoreCliccatoPreRefresh = true avvio animazione che porta la schermata in basso su mobile;
	model.addAttribute("contatoreCliccatoPreRefresh", ContatoreController.contatoreCliccatoPreRefresh);

	// inizializzo a false così che al refresh esegue animazione solo se era stato cliccato in precedenza
	ContatoreController.contatoreCliccatoPreRefresh = false;
	
	return "/Task/freelapp-editTask";
    }

    @RequestMapping(value = "/Task/edit/{id}", method = {RequestMethod.PATCH, RequestMethod.POST})
    public String updateTask(@PathVariable("id") Integer id, @Valid @ModelAttribute("formTask") Task formTask,
	    BindingResult bindingResult, Model model) {
	Task task = repositTask.getReferenceById(id);
	
	if (bindingResult.hasErrors()) {
	    bindingResult.addError(new ObjectError("Errore", "c'è un errore nel salvataggio del form"));
	    
	    //passo al model i contatore e task in uso (gli static)
	  	model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
	  	model.addAttribute("taskInUso", ContatoreController.taskInUso);
	  	model.addAttribute("contatoreAttivatoDaRapidButton", ContatoreController.contatoreAttivatoDaRapidButton);
	
	  	//inizializzo a false così al reload successivo js non genera i tasti del contatore
	  	ContatoreController.contatoreAttivatoDaRapidButton = false;
	  	
	  	//invio al model il booleano del contatore attivato
		//se contatoreAttivato = true avvio animazione su titolo task al contatore;
		model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
		//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
		ContatoreController.contatoreAttivato = false;
		
		//inizializzo a false così al reload successivo js non genera i tasti del contatore
		ContatoreController.contatoreAttivatoDaRapidButton = false;
		
		// invio al model il booleano del contatore cliccato prima del refresh pagina
		// se contatoreCliccatoPreRefresh = true avvio animazione che porta la schermata in basso su mobile;
		model.addAttribute("contatoreCliccatoPreRefresh", ContatoreController.contatoreCliccatoPreRefresh);

		// inizializzo a false così che al refresh esegue animazione solo se era stato cliccato in precedenza
		ContatoreController.contatoreCliccatoPreRefresh = false;
		
	  	//metodo che passa al model le informazioni sul task in uso per generare la modale STOP
		taskService.informationFromTaskInUsoToModel(model);
		
		//passa al model la lista di tutti i task esclusi quelli chiusi
		List<Task> taskList = new ArrayList<Task> ();
		taskList = repositTask.findAllNotClosed();
		model.addAttribute("taskList", taskList);

	    return "/Task/freelapp-editTask";
	}

	task.setDataModifica(LocalDateTime.now());
	 if (formTask.getName() != null) task.setName(formTask.getName());
	 if (formTask.getDescrizione() != null) task.setDescrizione(formTask.getDescrizione());
	 if (formTask.getDataInizio() != null) task.setDataInizio(formTask.getDataInizio());
	 if (formTask.getDataChiusuraStimata() != null) task.setDataChiusuraStimata(formTask.getDataChiusuraStimata()); 
  
	repositTask.save(task);
	
	return "redirect:/Task/"+id;

    }

    @PostMapping("/Task/delete/{id}")
    public String deleteTask(@PathVariable("id") Integer id) {

	repositTask.deleteById(id);

	return "redirect:/Task";
    }

    
    @PostMapping("/task/timeExceed/{id}")
	public String timeExceedError(@PathVariable("id")Integer id,
			@Valid @ModelAttribute("formContatoreErroreFinalsecond")Contatore contatore,
			BindingResult bindingResult, Model model) {
		
		Task taskInUso = repositTask.getReferenceById(id);

		ContatoreController.contatoreInUso = null;
		
		ContatoreController.taskInUso = null;
		
		contatoreservice.timeExeed(bindingResult, taskInUso, model);
		
		//metodo che passa al model le informazioni sul task in uso per generare la modale STOP	
		taskService.informationFromTaskInUsoToModel(model);
		
		//passa al model la lista di tutti i task esclusi quelli chiusi
		List<Task> taskList = new ArrayList<Task> ();
		taskList = repositTask.findAllNotClosed();
		model.addAttribute("taskList", taskList);
		
		return "Errori/timeExceeded";
	}
}