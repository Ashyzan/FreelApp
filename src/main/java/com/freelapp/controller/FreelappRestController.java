package com.freelapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freelapp.model.Progetto;
import com.freelapp.model.Task;
import com.freelapp.repository.ProgettoRepository;
import com.freelapp.repository.TaskRepository;
import com.freelapp.restModel.RestProject;
import com.freelapp.restModel.RestTask;
import com.freelapp.service.ContatoreService;
import com.freelapp.service.ProgettoService;
import com.freelapp.service.TaskService;




@RestController
@CrossOrigin
@RequestMapping("/api")
public class FreelappRestController {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ProgettoRepository progettoRepository;
	
	@Autowired
	private ContatoreService contatoreService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ProgettoService progettoService;
	
	@GetMapping("/task/{id}")
	public Optional<RestTask> get(@PathVariable("id") Integer id){
		
		Task task = taskRepository.getReferenceById(id);
		
		String nome = task.getName();
		
		String progetto = task.getProgetto().getName();
		
		Integer progettoId = task.getProgetto().getId();
		
		String cliente = task.getProgetto().getCliente().getLabelCliente();
		
		Integer clienteId = task.getProgetto().getCliente().getId();
		
		String logoCliente = task.getProgetto().getCliente().getLogoPath();
		
		Long finalTime = 0l;
		
		Integer taskInUsoDaInviare = 0;
		
		Integer idTask = task.getId();
		
		String statoTask = task.getStato();
		
		if(ContatoreController.taskInUso != null) {
			taskInUsoDaInviare = ContatoreController.taskInUso.getId();
		} 
		
		if(task.getContatore() != null && task.getContatore().getFinaltime() != null) {
			
			finalTime = task.getContatore().getFinaltime();
			
		}
		
		//LocalDateTime stop = task.getContatore().getStop();

	
		Optional<RestTask> restTask = Optional.of(new RestTask(nome, progetto, progettoId, 
				cliente, clienteId, logoCliente, finalTime, taskInUsoDaInviare, 
				idTask, statoTask));
	
		return restTask;
	}
	
	@GetMapping("/searchMode/taskList")
	public List<RestTask> searchListOreLavorate(@RequestParam String input) {
		
		List<RestTask> taskListOreLavorate = new ArrayList<RestTask> ();
		
		if(input == "") {
			List<RestTask> taskListOreLAvorateEmpty = new ArrayList<RestTask> ();
			return taskListOreLAvorateEmpty;
		}
		
		
		// lista che restituisce i task per modale delle ore lavorate sulla dashboard
		List<Task> taskList = new ArrayList<Task> ();
		taskList = taskRepository.searchOreLavorate(input);
		taskList.forEach( task -> {
			
			RestTask taskTemporaneo = new RestTask(null, null, null, null, null, null, null, null, null, null);
			
			taskTemporaneo.setNome(task.getName());
			taskTemporaneo.setProgetto(task.getProgetto().getName());
			taskTemporaneo.setProgettoId(task.getProgetto().getId());
			taskTemporaneo.setCliente(task.getProgetto().getCliente().getLabelCliente());
			taskTemporaneo.setClienteId(task.getProgetto().getCliente().getId());
			taskTemporaneo.setLogoCliente(task.getProgetto().getCliente().getLogoPath());
			taskTemporaneo.setFinalTime(0l);
			taskTemporaneo.setTaskAttualmenteInUso(0);
			taskTemporaneo.setId(task.getId());
			taskTemporaneo.setStato(task.getStato());
		
				if( task.getContatore() == null) {
				taskListOreLavorate.add(taskTemporaneo);
				
						}
				
				else if( task.getContatore() != null) {
					Boolean contatoreAttivo;
					contatoreAttivo = contatoreService.contatoreIsRun(task);
		
					if(task.getContatore().getStop() == null  && contatoreAttivo == false) {
					
							 taskListOreLavorate.add(taskTemporaneo); 
									
							 }	
						}
		
				} );
		
		return taskListOreLavorate;
	}
	
	//metodo che ritorna la lista completa dei task(non chiusi) per la modalità select del rapid Button
	@GetMapping("/selectMode")
	public List<RestTask> taskListSelectModeRapidButton(){
	
		List<RestTask> listaTaskSelectMode = new ArrayList<RestTask> ();
		
		List<Task> listaTaskNotClosed = taskRepository.findAllNotClosed();
		
		listaTaskNotClosed.forEach(task ->{
			
			RestTask taskTemporaneo = new RestTask(null, null, null, null, null, null, null, null, null, null);
			
			taskTemporaneo.setNome(task.getName());
			taskTemporaneo.setProgetto(task.getProgetto().getName());
			taskTemporaneo.setProgettoId(task.getProgetto().getId());
			taskTemporaneo.setCliente(task.getProgetto().getCliente().getLabelCliente());
			taskTemporaneo.setClienteId(task.getProgetto().getCliente().getId());
			taskTemporaneo.setLogoCliente(task.getProgetto().getCliente().getLogoPath());
			taskTemporaneo.setFinalTime(0l);
			taskTemporaneo.setTaskAttualmenteInUso(0);
			taskTemporaneo.setId(task.getId());
			taskTemporaneo.setStato(task.getStato());
			
			listaTaskSelectMode.add(taskTemporaneo);
		});
		
		return listaTaskSelectMode;
		
	}
	
	
// *********************** API PER STATISTICHE **************************
	
	//api che ritorna json con statistiche dettaglio task
	@GetMapping("/statistiche-dettaglio-task/{id}") 
	public JSONObject TaskJson(@PathVariable("id") Integer id) throws InterruptedException{
				
		//aggiunto ritardo di 200ms nell'esecuzione dell'api per permettere al db di aggiornarsi e di poter aggiornare correttamente i dati statistici
		Thread.sleep(200);

		Task task = taskRepository.getReferenceById(id);

		//recupero dati chiusura stimata
		Map<String, Long> giorniChiusuraStimata;
		if(task.getDataChiusuraStimata() != null){
			giorniChiusuraStimata = taskService.inLineaConChiusuraStimata(task);
		} else {
			giorniChiusuraStimata = null;
		}
		
		//recupero tipologia del progetto del task 		
		String tipologiaProgetto = task.getProgetto().getTipologia();
			
		//creazione json
		JSONObject JsonObj = new JSONObject();
				
		JsonObj.put("giorniChiusuraStimata" , giorniChiusuraStimata);
		JsonObj.put("tipologiaProgetto" , tipologiaProgetto);
		
		
		//a seconda della tipolgia progetto mando nel json un budget differente e suo relativo utilizzo		
		switch (tipologiaProgetto) {
		case "budget":
			JsonObj.put("budgetImpiegatoDaAltriTask" , taskService.calcoloParteDiBudgetUsataDaAltriTaskNelProgettoMonetario(task));
			JsonObj.put("budgetTotaleProgetto" , task.getProgetto().getBudgetMonetario());
			if(task.getContatore() != null) {
				JsonObj.put("budgetImpiegatoDalTask" , taskService.calcoloGuadagnoTaskDaFinalTimeToDouble(task));
			} else {
				JsonObj.put("budgetImpiegatoDalTask" , "-");
			}
			break;
		case "ore":
			JsonObj.put("budgetImpiegatoDaAltriTask" , taskService.calcoloParteDiBudgetUsataDaAltriTaskNelProgettoOre(task));
			JsonObj.put("budgetTotaleProgetto" , task.getProgetto().getBudgetOre());
			//restituisce le ore utilizzate dal task trasformando il finaltime in ore
			if(task.getContatore() != null) {
				JsonObj.put("budgetImpiegatoDalTask" , task.getContatore().getFinaltime().doubleValue() / 3600);				
			} else {
				JsonObj.put("budgetImpiegatoDalTask" , 0);
			}
			break;
		default:
			JsonObj.put("budgetTotaleProgetto" , null);
		}
		//aggiunta di altri dati statistici del dettaglio progetto
		String guadagnoAttualeTask = taskService.calcoloGuadagnoTaskDaFinalTime(task) + " €";
		String pauseTask = String.valueOf(task.getContatore().getStop_numbers());
		String oreLavorate = String.valueOf(task.getContatore().getFinaltime()/3600);
		
		JsonObj.put("guadagnoAttualeTask", guadagnoAttualeTask);
		JsonObj.put("pauseTask", pauseTask);
		JsonObj.put("oreLavorate", oreLavorate);
		return JsonObj;
				
	}
	 

// *********************** API PER FILTRI TASK **************************
	
//api che restituisce intero elenco di progetti per il filtro select progetto
@GetMapping("/filtri-task/progetti-all")
public List<RestProject> listaInteraProgettiPerFiltriTask() {
	
	//recupero dal db la lista intera dei progetti attivi
	List<Progetto> listaProgetti = new ArrayList<Progetto>();
	listaProgetti = progettoRepository.findByActiveProject();
	
	//creazione list rest progetti vuota
	List<RestProject> listaRestProgetti= new ArrayList<RestProject>();
	
	//per ogni elemento della lista recuperata da db creo un elemento restProject e lo pusho sulla listaRestProgetti
	listaProgetti.forEach(progetto -> {
		RestProject progettoTemporaneo = new RestProject(null, null);
		progettoTemporaneo.setId(progetto.getId());
		progettoTemporaneo.setName(progetto.getName());
		listaRestProgetti.add(progettoTemporaneo);
		
	});
	
	
	return listaRestProgetti;
}
	
	
//api che restituisce elenco filtrato di progetti per filtro search progetto
@GetMapping("/filtri-task/progetti-search")
public List<RestProject> listaFiltrataProgettiPerFiltriTask(@RequestParam String input){
	if(input == "") {
			List<RestProject> taskListOreLAvorateEmpty = new ArrayList<RestProject> ();
			return taskListOreLAvorateEmpty;
		}
	
	//recupero dal db la lista intera dei progetti attivi
	List<Progetto> listaProgetti = new ArrayList<Progetto>();
	listaProgetti = progettoRepository.searchProgettiByNameInput(input);
	
	//creazione list rest progetti vuota
	List<RestProject> listaRestProgetti= new ArrayList<RestProject>();
	
	//per ogni elemento della lista recuperata da db creo un elemento restProject e lo pusho sulla listaRestProgetti
	listaProgetti.forEach(progetto -> {
		RestProject progettoTemporaneo = new RestProject(null, null);
		progettoTemporaneo.setId(progetto.getId());
		progettoTemporaneo.setName(progetto.getName());
		listaRestProgetti.add(progettoTemporaneo);
		
	});
	
	
	return listaRestProgetti;
	
}
	
	
	
	
//	@GetMapping(value = "/task/timeExceed/{id}", produces = MediaType.TEXT_HTML_VALUE)
//	public String timeExceedError(@PathVariable("id")Integer id, Model model) {
//		
//		Task taskInUso = taskRepository.getReferenceById(id);
//		
//		contatoreService.timeExeed(taskInUso, model);
//		
//		return "Errori/timeExceeded";
//	}
}
