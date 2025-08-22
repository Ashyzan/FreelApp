package com.freelapp.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.freelapp.controller.ContatoreController;
import com.freelapp.controller.TaskController;
import com.freelapp.model.Task;
import com.freelapp.repository.ClienteRepository;
import com.freelapp.repository.ProgettoRepository;
import com.freelapp.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProgettoRepository progettoRepository;
	
	//variabile che viene utilizzata nel metodo di statistica "calcoloParteDiBudgetUsataDaAltriTaskNelProgetto"
	Long finalTimeAltriTaskDelProgetto = 0l;

	public List<Task> findAllNotClosed(){
		//restituisce la lista dei task attivi(non chiusi)
		return taskRepository.findAllNotClosed();
	}
	
	public Page<Task> findPage(int pageNumber){
		
		//criterio di default della lista dei progetti senza alcun filtro selezionato
		Pageable pageable = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
		System.out.println("TaskController.dataPerOrdinamentoTask --> " + TaskController.dataPerOrdinamentoTask);
			System.out.println("TaskController.ordinaTaskInListaTask --> " + TaskController.ordinaTaskInListaTask);
		//if che in base al tipo di ordinamento scelto varia il criterio di orninamento di pageable1 e lo manda nella query dinamica
		if(TaskController.dataPerOrdinamentoTask.equals("dataModificaTask")) {
				if(TaskController.ordinaTaskInListaTask.equals("piuRecente")) {
					System.out.println("SONO IN PIU RECENTE - MODIFICA TASK");
					pageable = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					
				}else if(TaskController.ordinaTaskInListaTask.equals("menoRecente")) {
					System.out.println("SONO IN MENO RECENTE - MODIFICA TASK");
					pageable = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").ascending());
					
				}
				
			}else if(TaskController.dataPerOrdinamentoTask.equals("dataCreazioneTask")) {
				if(TaskController.ordinaTaskInListaTask.equals("piuRecente")) {
					System.out.println("SONO IN PIU RECENTE - CREAZIONE TASK");
					pageable = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					
				}else if(TaskController.ordinaTaskInListaTask.equals("menoRecente")) {
					System.out.println("SONO IN MENO RECENTE - CREAZIONE TASK");
					pageable = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").ascending());
					
				}
	}
		
		//returnString viene personalizzato ogni volta a seconda dei filtri selezionati 
		Page<Task> returnString = null;

		
		//inizializza le due specifiche per le query che verranno poi assegnate solo se selezionati i filtri di stato o la selezione cliente
		Specification<Task> specification_SelezioneCliente = null;
		Specification<Task> specification_SelezioneProgetto = null;
		Specification<Task> specificationStato;
		
		//switch che assegna la specifica per la query di stato
		switch(TaskController.statoTaskInListaTask) {
				case "aperto":
					specificationStato = filtroTask_StatoAttivo();
					
					break;
				case "chiuso":
					specificationStato = filtroTask_StatoNonAttivo();
					break;
				default: specificationStato = null;
   
		}
		
		//if che assegna la specifica per la query se è stato scelto il cliente
		if(TaskController.clienteIdTaskInListaTask != -1) {
			specification_SelezioneCliente = filtroTask_SelezioneCliente();
		}
		
		//if che assegna la specifica per la query se è stato scelto il progetto
		if(TaskController.progettoIdTaskInListaTask != -1) {
			specification_SelezioneProgetto = filtroTask_SelezioneProgetto();
		}
		
		//if che a secondo che sia stato selezionato come filtro il progetto, lo stato o nessuno dei due costruisce la query dinamica
		if(TaskController.progettoIdTaskInListaTask == -1 && TaskController.statoTaskInListaTask == "" && TaskController.clienteIdTaskInListaTask == -1) {
			returnString =  taskRepository.findAll( pageable);
		}else if (TaskController.progettoIdTaskInListaTask != -1){
							
			returnString =  taskRepository.findAll(specification_SelezioneProgetto.and(specificationStato) , pageable);
		}else if(TaskController.clienteIdTaskInListaTask != -1){
			returnString =  taskRepository.findAll(specification_SelezioneCliente.and(specificationStato) , pageable);
		}else {
		
			returnString =  taskRepository.findAll(specificationStato , pageable);
		}
		
		return returnString;
	}
	
	
	//specificazione che genera la quey di filtro per task attivi
	Specification<Task> filtroTask_StatoAttivo(){
		return (root, query, criteriaBuilder) ->{		
			
			return criteriaBuilder.isNull(root.get("dataChiusuraDefinitiva"));
					
		};
		
	}
	
	//specificazione che genera la quey di filtro per task non attivi
	Specification<Task> filtroTask_StatoNonAttivo(){
		return (root, query, criteriaBuilder) ->{			
			
			return criteriaBuilder.isNotNull(root.get("dataChiusuraDefinitiva"));
					
		};
		
	}
	
	//specificazione che genera la quey di filtro per cliente selezionato
	Specification<Task> filtroTask_SelezioneCliente(){
		
		return (root, query, criteriaBuilder) ->{
			
			return criteriaBuilder.equal(root.get("progetto").get("cliente").as(Integer.class), TaskController.clienteIdTaskInListaTask);
					
		};
		
	}
	
	//specificazione che genera la quey di filtro per progetto selezionato
	Specification<Task> filtroTask_SelezioneProgetto(){
		
		return (root, query, criteriaBuilder) ->{
			
			return criteriaBuilder.equal(root.get("progetto").as(Integer.class), TaskController.progettoIdTaskInListaTask);
					
		};
		
	}
	
	
	public Page<Task> findSearchedPage(int pageNumber, String input){
		Pageable pageable = PageRequest.of(pageNumber -1, 12);
		return taskRepository.search(input, pageable);
		
	}
	
	// Metodo che salva la data fine task in corrispondenza di STOP contatore
	// fa la conversioni da localdatetime dello stop a localdate della data di chiusura del task
	public void setStopTaskDate(LocalDateTime STOP, int taskId) {
		java.util.Date NEWSTOP = java.util.Date.from(STOP.atZone(ZoneId.systemDefault()).toInstant());
		LocalDate NEWSTOP2 = NEWSTOP.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		taskRepository.getReferenceById(taskId).setDataChiusuraDefinitiva(NEWSTOP2);
	}
	
	// metodo che restituisce il finaltime formattato in HH:MM:SS
	public String Timer(Task task) {		
	    Long finaltime = task.getContatore().getFinaltime(); 
	    Long HH = finaltime / 3600;
	    Long MM= (finaltime % 3600) / 60;
	    Long SS = finaltime % 60;  
	    String timer = String.format("%02d:%02d:%02d", HH, MM, SS);	    
		return timer;
	}
	
	//passa al model l'id, il nome e il progetto del taskInUso se diverso da null
	//impegato per la modale di stop
	public void informationFromTaskInUsoToModel(Model model) {
		
		Integer taskInUsoId = 0;
		String taskInUsoName = null;
		String taskInUsoProgetto = null;
		
		if(ContatoreController.taskInUso != null) {
			taskInUsoId = ContatoreController.taskInUso.getId();
			taskInUsoName = ContatoreController.taskInUso.getName();
			taskInUsoProgetto = ContatoreController.taskInUso.getProgetto().getName();
		}
		
		model.addAttribute("taskInUsoId", taskInUsoId);
		model.addAttribute("taskInUsoName", taskInUsoName);
		model.addAttribute("taskInUsoProgetto", taskInUsoProgetto);
	}
	
	
// ******************** METODI PER CALCOLO STATISTICHE ************************************
	
	//metodo per calcolare il guadagno di un task riferito all'ultima finalTime salvata a DB -restituisce un String
	public String calcoloGuadagnoTaskDaFinalTime(Task task) {
		
		Long finalTimeAttuale = task.getContatore().getFinaltime();
		
		double finalTimeAttualeInOre = (finalTimeAttuale.doubleValue() / 3600);
		//System.out.println("finalTimeAttualeInOre: " + finalTimeAttualeInOre);
		
		double tariffaOrariaProgetto = task.getProgetto().getTariffaOraria();
		//System.out.println("tariffaOrariaProgetto: " + tariffaOrariaProgetto);

		double guadagnoTemporaneoTask = (finalTimeAttualeInOre*tariffaOrariaProgetto);
		//System.out.println("guadagnoTemporaneoTask: " + guadagnoTemporaneoTask);
		
		DecimalFormat guadagnoTemporaneoTaskFormattato = new DecimalFormat("0.00");
		
		String guadagnoTemporaneoTaskToString = String.valueOf(guadagnoTemporaneoTaskFormattato.format(guadagnoTemporaneoTask));
	
		return guadagnoTemporaneoTaskToString;
	}
	
	//metodo per calcolare il guadagno di un task riferito all'ultima finalTime salvata a DB - restituisce un double
	public double calcoloGuadagnoTaskDaFinalTimeToDouble(Task task) {
		
		Long finalTimeAttuale = task.getContatore().getFinaltime();
		
		double finalTimeAttualeInOre = (finalTimeAttuale.doubleValue() / 3600);
		//System.out.println("finalTimeAttualeInOre: " + finalTimeAttualeInOre);
		
		double tariffaOrariaProgetto = task.getProgetto().getTariffaOraria();
		//System.out.println("tariffaOrariaProgetto: " + tariffaOrariaProgetto);

		double guadagnoTemporaneoTask = (finalTimeAttualeInOre*tariffaOrariaProgetto);
		//System.out.println("guadagnoTemporaneoTask: " + guadagnoTemporaneoTask);
		
		return guadagnoTemporaneoTask;
	}
	
	//metodo che restituisce se in linea o no rispetto chiusura prevista
	public Map<String, Long> inLineaConChiusuraStimata(Task task) {
		
			Map<String, Long> statisticheChiusuraStimata = new HashMap<String, Long>();
		
			//calcolo dei giorni totali tra data inizio e fine stimata
			LocalDate dataChiusuraStimata = task.getDataChiusuraStimata();
			LocalDate dataInizio = task.getDataInizio();
			long giorniTotali = dataInizio.until(dataChiusuraStimata, ChronoUnit.DAYS);
			//System.out.println("giorni disponibili : " + giorniTotali);
			
			//calcolo dei giorni ancora disponibili o in eccesso
			LocalDate dataAttuale = LocalDate.now();
			long giorniInEccesso = 0;
			long giorniAncoraDisponibili = 0;
			long calcoloGiorniAncoraDisponibili = dataAttuale.until(dataChiusuraStimata, ChronoUnit.DAYS);
			if(calcoloGiorniAncoraDisponibili >= 0) {
				giorniAncoraDisponibili = calcoloGiorniAncoraDisponibili;
			} else {
				giorniInEccesso = Math.abs(calcoloGiorniAncoraDisponibili);
				
			}
			
			//System.out.println("giorni ancora diponibili: " + giorniAncoraDisponibili);
			
			//riempimento dell'HashMap
			statisticheChiusuraStimata.put("giorniTotaliStimati", giorniTotali);
			statisticheChiusuraStimata.put("giorniAncoraDisponibili", giorniAncoraDisponibili);
			statisticheChiusuraStimata.put("giorniOltreChiusuraStimata", giorniInEccesso);
			
			//System.out.println("giorniChiusuraStimata MAP: " + statisticheChiusuraStimata);
			
			return statisticheChiusuraStimata;
	}
	
	
	//metodo che calcola la parte di budget orario Impiegata dagli altri task del progetto ad ore
	public double calcoloParteDiBudgetUsataDaAltriTaskNelProgettoOre(Task task) {
		
		double parteDiBudgetUsataDaAltriTaskNelProgettoInOre = 0;
		
		//riporto a zero la variabile finalTimeAltriTaskDelProgetto inizializzata ad inizio classe
		finalTimeAltriTaskDelProgetto = 0l;
		
		//ricavo il progetto relativo al task
		Integer progettoId = task.getProgetto().getId();
		
		//recupero tutti i task del progetto per sommarne poi il finaltime
		List<Task> listaTaskProgetto = new ArrayList<Task>();
		listaTaskProgetto = taskRepository.findByProgettoId(progettoId);
		listaTaskProgetto.forEach( altroTask -> {			
			if(altroTask.getContatore() != null && altroTask.getId() != task.getId()) {
					finalTimeAltriTaskDelProgetto = finalTimeAltriTaskDelProgetto + altroTask.getContatore().getFinaltime();					
			}
		});
		
		//trasmormo il finalTime da secondi ad ore
		parteDiBudgetUsataDaAltriTaskNelProgettoInOre = (finalTimeAltriTaskDelProgetto.doubleValue() / 3600);
		
		return parteDiBudgetUsataDaAltriTaskNelProgettoInOre;
	}
	
	
	//metodo che calcola la parte di budget monetario Impiegata dagli altri task del progetto a budget monetario
	public double calcoloParteDiBudgetUsataDaAltriTaskNelProgettoMonetario(Task task) {
		
		double parteDiBudgetUsataDaAltriTaskNelProgettoInOre = 0;
		
		//riporto a zero la variabile finalTimeAltriTaskDelProgetto inizializzata ad inizio classe
		finalTimeAltriTaskDelProgetto = 0l;
		
		//ricavo il progetto relativo al task
		Integer progettoId = task.getProgetto().getId();
		
		double tariffaOrariaProgetto = task.getProgetto().getTariffaOraria();
		
		//recupero tutti i task del progetto per sommarne poi il finaltime
		List<Task> listaTaskProgetto = new ArrayList<Task>();
		listaTaskProgetto = taskRepository.findByProgettoId(progettoId);
		listaTaskProgetto.forEach( altroTask -> {			
			if(altroTask.getContatore() != null && altroTask.getId() != task.getId()) {
					finalTimeAltriTaskDelProgetto = finalTimeAltriTaskDelProgetto + altroTask.getContatore().getFinaltime();					
			}
		});
		
		//trasformo il finalTime da secondi ad ore
		parteDiBudgetUsataDaAltriTaskNelProgettoInOre = (finalTimeAltriTaskDelProgetto.doubleValue() / 3600);
		
		return parteDiBudgetUsataDaAltriTaskNelProgettoInOre*tariffaOrariaProgetto;
	}
	
	
	
	
	//metodo che genera la stringa dei filtri applicati da far vedere all'utente in lista progetti
			public void stringaFiltriInListaTask(Model model) {
				
				//inizializzo le tre stringhe che poi verranno passate al model e utilizzate da javascript per riempire la lista dei filtri applicati
				String statoTask = "";
				String filtroStatoTask = null;
				String ordinamentoTask = "";
				String filtroOrdinamentoTask = null;
				String nomeCliente = "";
				String filtroNomeCliente = null;
				String nomeProgetto = "";
				String filtroNomeProgetto = null;
				String dataOrdinamentoTask = "";
				String filtroDataOrdinamentoTask = null;
				String testoFinale = "Nessun filtro applicato";
				if(TaskController.statoTaskInListaTask != null || TaskController.ordinaTaskInListaTask != null || TaskController.clienteIdTaskInListaTask != -1
								|| TaskController.progettoIdTaskInListaTask != -1) {
					
					if(TaskController.statoTaskInListaTask.equals("aperto")) {
						statoTask = "<div>- stato task <strong>APERTO</strong></div>";
						filtroStatoTask = "APERTO";
					}else if(TaskController.statoTaskInListaTask.equals("chiuso")) {
						statoTask = "<div>- stato task <strong>CHIUSO</strong></div>";
						filtroStatoTask = "CHIUSO";
					}
					
					if(TaskController.dataPerOrdinamentoTask.equals("dataModificaTask")) {
						dataOrdinamentoTask = "<span>- ordinamento per <strong>data di modifica</strong></span>";
						filtroDataOrdinamentoTask = "data di modifica";
					}else if(TaskController.dataPerOrdinamentoTask.equals("dataCreazioneTask")) {
						dataOrdinamentoTask = "<span>- ordinamento per <strong>data di creazione</strong></div>";
						filtroDataOrdinamentoTask = "data di creazione";
					}
					
					if(TaskController.ordinaTaskInListaTask.equals("piuRecente")) {
						ordinamentoTask = "<span> <strong>più recente<span> </strong>";
						filtroOrdinamentoTask = "più recente";
						
					}else if(TaskController.ordinaTaskInListaTask.equals("menoRecente")) {
						ordinamentoTask = "<span> <strong>meno recente<span> </strong>";
						filtroOrdinamentoTask = "meno recente";
					}
					
					if(TaskController.clienteIdTaskInListaTask != -1) {
						
						Integer idClienteSelezionato = TaskController.clienteIdTaskInListaTask;
						filtroNomeCliente = clienteRepository.findById(idClienteSelezionato).get().getLabelCliente();
						nomeCliente ="<div>- cliente <strong>" +  filtroNomeCliente + "</strong></div>";
					}
					
					if(TaskController.progettoIdTaskInListaTask != -1) {
						
						Integer idProgettoSelezionato = TaskController.progettoIdTaskInListaTask;
						filtroNomeProgetto = progettoRepository.findById(idProgettoSelezionato).get().getName();
						nomeProgetto ="<div>- progetto <strong>" +  filtroNomeProgetto + "</strong></div>";
					}
					
					testoFinale = dataOrdinamentoTask + ordinamentoTask + nomeCliente + nomeProgetto + statoTask;
					
				}
				
				
				model.addAttribute("filtroStatoTask", filtroStatoTask);
				model.addAttribute("filtroOrdinamentoTask", filtroOrdinamentoTask);
				model.addAttribute("filtroNomeCliente", filtroNomeCliente);
				model.addAttribute("filtroNomeProgetto", filtroNomeProgetto);
				model.addAttribute("filtroDataOrdinamentoTask", filtroDataOrdinamentoTask);
				model.addAttribute("testoFinale", testoFinale);	
			}
	
}
