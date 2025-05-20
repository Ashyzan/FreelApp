package com.freelapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freelapp.model.Progetto;
import com.freelapp.model.Task;
import com.freelapp.repository.ProgettoRepository;
import com.freelapp.repository.TaskRepository;
import com.freelapp.restModel.RestProgetto;
import com.freelapp.restModel.RestTask;
import com.freelapp.service.ContatoreService;


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
	
	//metodo che genera una lista di progetti archiviati custom 
	@GetMapping("/statistics/achived-projects")
	public List<RestProgetto> archiviedProjectsList(){
		
		//crezione nuova lista da riempire
		List<RestProgetto> archProjectList = new ArrayList<RestProgetto>();
		
		//lista progetti archiviati da repository
		List<Progetto> progettoList = new ArrayList<Progetto>();
		progettoList = progettoRepository.findByArchivia(true);
		
		progettoList.forEach(progetto -> {
			
			//creo per ogni progetto archiviato preso dalla repo un provvisiorio RestProgetto in cui inserisco i dati
			//del progetto archiviato preso da db . Inseriro poi questo provvisiorio nella archProjectList
			RestProgetto progettoTemporaneo = new RestProgetto(null, null, null, null, null, null);
			
			progettoTemporaneo.setNome(progetto.getName());
			progettoTemporaneo.setProgettoId(progetto.getId());
			progettoTemporaneo.setNomeCliente(progetto.getCliente().getLabelCliente());
			progettoTemporaneo.setClienteId(progetto.getCliente().getId());
			progettoTemporaneo.setCountTaskProgetto(progetto.getElencoTask().size());
			progettoTemporaneo.setTipologia(progetto.getTipologia());
			
			archProjectList.add(progettoTemporaneo);
			
//			System.out.println(progetto.getName());
		});
		
		return archProjectList;
	}
	

		
		//metodo che genera una lista di progetti archiviati custom 
			@GetMapping("/statistiche-test") 
			public JSONObject TaskJson(){
			
				JSONObject JsonObj = new JSONObject();
				

				JsonObj.put("io" , "Rosa");
				
				//JSONArray arrayjs = new JSONArray();
				
			//	arrayjs.add("pinco");
			//	arrayjs.add("pallino");
			//	arrayjs.add("zuppa");
			//	arrayjs.add("minestrone");
				
			//	JsonObj.put("descrizione lista", arrayjs);
				
				return  JsonObj;
				
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
