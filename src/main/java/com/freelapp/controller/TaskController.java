package com.freelapp.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.freelapp.model.Contatore;
import com.freelapp.model.Progetto;
import com.freelapp.model.Task;
import com.freelapp.repository.ProgettoRepository;
//import com.freelapp.model.Stato;
//import com.freelapp.repository.StatoRepository;
import com.freelapp.repository.TaskRepository;
import com.freelapp.service.ContatoreService;
import com.freelapp.service.TaskService;

import jakarta.validation.Valid;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository repositTask;

    @Autowired
    private ProgettoRepository repositProgetto;

    @Autowired
    private TaskService taskService;
    
    @Autowired
    private ContatoreService contatoreservice;

//	@Autowired
//	private StatoRepository repositStato;

    @GetMapping("/Task")
    public String iMieiTask(Model model) {
    	
		//passo al model i contatore e task in uso (gli static)
		model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
		model.addAttribute("taskInUso", ContatoreController.taskInUso);
		
		//invio al model il booleano del contatore attivato
		//se contatoreAttivato = true avvio animazione su titolo task al contatore;
		model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
		//passa al model l'id del taskInUso se diverso da null e serve per inibire se necessario
		//il pulsante di selezione contatore se  taskInUsoId == task.id (task è il task corrispondente al pulsante)
		Integer taskInUsoId = 0;
		
		if(ContatoreController.taskInUso != null) {
			taskInUsoId = ContatoreController.taskInUso.getId();
		}
		
		model.addAttribute("taskInUsoId", taskInUsoId);
		
		// restituisce al model questo valore booleano false se non ci sono progetti a db
		// e restituisce true se ci sono progetti a db
		boolean areTasksOnDb = false;
		if (!repositTask.findAll().isEmpty()) {
			areTasksOnDb = true;
		}
		model.addAttribute("areTasksOnDb", areTasksOnDb);
		
		
    	

	return getOnePage(1, model);
    }

    @GetMapping("/Task/page/{pageNumber}")
    public String getOnePage(@PathVariable("pageNumber") int currentPage, Model model) {

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
	
	//invio al model il booleano del contatore attivato
	//se contatoreAttivato = true avvio animazione su titolo task al contatore;
	model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
	
	//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
	ContatoreController.contatoreAttivato = false;
	
	//passa al model l'id del taskInUso se diverso da null e serve per inibire se necessario
		//il pulsante di selezione contatore se  taskInUsoId == task.id (task è il task corrispondente al pulsante)
		Integer taskInUsoId = 0;
		
		if(ContatoreController.taskInUso != null) {
			taskInUsoId = ContatoreController.taskInUso.getId();
		}
		
		model.addAttribute("taskInUsoId", taskInUsoId);

		// restituisce al model questo valore booleano false se non ci sono progetti a db
		// e restituisce true se ci sono progetti a db
		boolean areTasksOnDb = false;
		if (!repositTask.findAll().isEmpty()) {
			areTasksOnDb = true;
		}
		model.addAttribute("areTasksOnDb", areTasksOnDb);

	return "/Task/freelApp-listaTask";
    }

    @GetMapping("/task-search")
    public String listaTaskSearch(@Param("input") String input, Model model) {
  
    	//passo al model i contatore e task in uso (gli static)
		model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
		model.addAttribute("taskInUso", ContatoreController.taskInUso);
		
		//invio al model il booleano del contatore attivato
		//se contatoreAttivato = true avvio animazione su titolo task al contatore;
		model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
		//passa al model l'id del taskInUso se diverso da null e serve per inibire se necessario
		//il pulsante di selezione contatore se  taskInUsoId == task.id (task è il task corrispondente al pulsante)
		Integer taskInUsoId = 0;
		
		if(ContatoreController.taskInUso != null) {
			taskInUsoId = ContatoreController.taskInUso.getId();
		}
		
		model.addAttribute("taskInUsoId", taskInUsoId);
		
		//invio al model il booleano del contatore attivato
		//se contatoreAttivato = true avvio animazione su titolo task al contatore;
		model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
		//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
//		ContatoreController.contatoreAttivato = false;

		// restituisce al model questo valore booleano false se non ci sono progetti a db
		// e restituisce true se ci sono progetti a db
		boolean areTasksOnDb = false;
		if (!repositTask.findAll().isEmpty()) {
			areTasksOnDb = true;
		}
		model.addAttribute("areTasksOnDb", areTasksOnDb);

	return taskBySearch(1, input, model);
    }

    @GetMapping("/task-search/page/{numberPage}")
    public String taskBySearch(@PathVariable("pageNumber") int currentPage, String input, Model model) {

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
	
	//passa al model l'id del taskInUso se diverso da null e serve per inibire se necessario
		//il pulsante di selezione contatore se  taskInUsoId == task.id (task è il task corrispondente al pulsante)
		Integer taskInUsoId = 0;
		
		if(ContatoreController.taskInUso != null) {
			taskInUsoId = ContatoreController.taskInUso.getId();
		}
		
		model.addAttribute("taskInUsoId", taskInUsoId);
	
		//invio al model il booleano del contatore attivato
		//se contatoreAttivato = true avvio animazione su titolo task al contatore;
		model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
		
		//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
		ContatoreController.contatoreAttivato = false;
		
		// restituisce al model questo valore booleano false se non ci sono progetti a db
		// e restituisce true se ci sono progetti a db
		boolean areTasksOnDb = false;
		if (!repositTask.findAll().isEmpty()) {
			areTasksOnDb = true;
		}
		model.addAttribute("areTasksOnDb", areTasksOnDb);

	return "/Task/freelApp-listaTask";
    }

    @GetMapping("/Task/{id}")
    public String descrizioneTask(@PathVariable("id") int taskId, Model model) {

    Task task = repositTask.getReferenceById(taskId);
    model.addAttribute("task", task);
    
    // passo il finaltime formattato per la voce "timer" di tipo string sul dettaglio task
    
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
	
	//invio al model il booleano del contatore attivato
	//se contatoreAttivato = true avvio animazione su titolo task al contatore;
	model.addAttribute("contatoreAttivato", ContatoreController.contatoreAttivato);
	
	//inizializzo a false così che al refresh o cambio pagina non esegue animazione ma solo allo start
	ContatoreController.contatoreAttivato = false;
	
	return "/Task/freelapp-descrizioneTask";
    }

    @GetMapping("/Task/insert/progetto-{id}")
    public String insertTask(@PathVariable("id") Integer id,

	    Progetto progetto, Model model) {

    	
	// richiamo il progetto tramite id
	progetto = repositProgetto.getReferenceById(id);

	// istanzio un nuovo task
	Task newTask = new Task();
	System.out.println(" STAMPO ID DEL NewTASK ****************** " + newTask.getId());
	// attribuisco il task al progetto
	newTask.setProgetto(progetto);

	// riporto nel modello il task
	model.addAttribute("task", newTask);
	
	//riporto al model l'id del progetto in uso
	model.addAttribute("progettoId", progetto.getId());

	//passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
	String endPoint = "/Task/insert/progetto-" ;
	
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
	
	return "/Task/freelapp-insertTask";
    }

    @PostMapping("/Task/insert/progetto")
    public String storeTask(@Valid @ModelAttribute("task") Task task,
	    BindingResult bindingResult, Model model) {

//// richiamo il progetto tramite id
//	Progetto progetto = repositProgetto.getReferenceById(id);

//// attribuisco il task passato dal modello al progetto (progettoRif)
	//task.setProgetto(progetto);
	
	//System.out.println(" STAMPO ID DEL PROGETTO ****************** " + progetto.getId());
	System.out.println(" STAMPO ID DEL TASK ****************** " + task.getId());
	// restituisco il task al modello
	//model.addAttribute("task", task);

	if (bindingResult.hasErrors()) {
// bindingResult.addError(
// new ObjectError("Errore", "Huston abbiamo un problema"));
		//passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
		String endPoint = "/Task/insert/progetto-" ;
		
		model.addAttribute("endPoint", endPoint);
		
		//riporto al model l'id del progetto in uso
		//model.addAttribute("progettoId", progetto.getId());
		
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

	    return "/Task/freelapp-insertTask";
	}

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

	// riporto nel modello l'elenco dei progetti disponibili
	model.addAttribute("listaProgetti", repositProgetto.findAll());

	//  passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
	String endPoint = "/Task/newTask";
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
		
		//passp al model taskInUsoId che serve alla modale di controllo start e pause in edit e insert
		if(ContatoreController.taskInUso != null) {
			model.addAttribute("taskInUsoId",ContatoreController.taskInUso.getId());
		}else {
			model.addAttribute("taskInUsoId",0);
		}

	    return "/Task/freelapp-insertTask-noProgetto";
	}

	// salvo il task
	repositTask.save(task);

	return "redirect:/Task";

    }

    @GetMapping("/Task/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {

	Task formTask = repositTask.getReferenceById(id);
	model.addAttribute("formTask", formTask);
	
	//passo al model l'endpoint da dare come input hidden a start/pause/stop del contatore
    String endPoint = "/Task/edit/";
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
	
	return "/Task/freelapp-editTask";
    }

    @PostMapping("/Task/edit/{id}")
    public String updateTask(@PathVariable("id") Integer id, @Valid @ModelAttribute("formTask") Task formTask,
	    BindingResult bindingResult, Model model) {
	repositTask.getReferenceById(id);

	if (bindingResult.hasErrors()) {
	    bindingResult.addError(new ObjectError("Errore", "c'è un errore nel salvataggio del form"));
	    
	  //passo al model i contatore e task in uso (gli static)
	  	model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
	  	model.addAttribute("taskInUso", ContatoreController.taskInUso);
	  	
	  	//passp al model taskInUsoId che serve alla modale di controllo start e pause in edit e insert
	  	if(ContatoreController.taskInUso != null) {
	  		model.addAttribute("taskInUsoId",ContatoreController.taskInUso.getId());
	  	}else {
	  		model.addAttribute("taskInUsoId",0);
	  	}

	    return "/Task/freelapp-editTask";
	}

	repositTask.save(formTask);

	return "redirect:/Task";

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
		
		
		
		return "Errori/timeExceeded";
	}
}