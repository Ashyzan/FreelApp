package com.freelapp.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.freelapp.controller.ContatoreController;
import com.freelapp.model.Task;
import com.freelapp.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public List<Task> findAllNotClosed(){
		//restituisce la lista dei task attivi(non chiusi)
		return taskRepository.findAllNotClosed();
	}
	
	public Page<Task> findPage(int pageNumber){
		Pageable pageable = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
		//restituisce la lista dei task attivi(non chiusi)
		return taskRepository.findAllNotClosed(pageable);
		
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
}
