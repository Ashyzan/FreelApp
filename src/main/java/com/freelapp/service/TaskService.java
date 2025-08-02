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
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.freelapp.controller.ContatoreController;
import com.freelapp.controller.TaskController;
import com.freelapp.model.Progetto;
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
		
		Pageable pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
		//returnString viene personalizzato ogni volta a seconda dei filtri selezionati 
		Page<Task> returnString = null;
		returnString =  taskRepository.findAll(pageable1);
		
		//condizione di inserimento solo filtro di STATO
		if(!TaskController.statoTaskInListaTask.equals("") && TaskController.ordinaTaskInListaTask.equals("") 
				&& TaskController.clienteIdTaskInListaTask == -1 && TaskController.dataPerOrdinamentoTask.equals("")) {
			if(TaskController.statoTaskInListaTask.equals("aperto")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
				returnString = taskRepository.findByActiveTaskPageable(pageable1);
						
			} else if (TaskController.statoTaskInListaTask.equals("chiuso")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());	
				returnString = taskRepository.findByNotActiveTaskPageable(pageable1);
						
			}
		}
		
		//condizione di inserimento solo filtro di ORDINAMENTO(solo scelta se data modifica o data creazione - di default la piuù recente per prima)
		if(TaskController.statoTaskInListaTask.equals("") && TaskController.ordinaTaskInListaTask.equals("") 
			&& TaskController.clienteIdTaskInListaTask == -1 && !TaskController.dataPerOrdinamentoTask.equals("")) {
			if(TaskController.dataPerOrdinamentoTask.equals("dataModificaTask")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
				returnString = taskRepository.findAll(pageable1);
						
			}else if(TaskController.dataPerOrdinamentoTask.equals("dataCreazioneTask")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
				returnString = taskRepository.findAll(pageable1);
						
			}
		}
		
		//condizione di inserimento solo filtro di ORDINAMENTO(completo di scelta piu o meno recente)
		if(TaskController.statoTaskInListaTask.equals("") && !TaskController.ordinaTaskInListaTask.equals("") 
			&& TaskController.clienteIdTaskInListaTask == -1 && !TaskController.dataPerOrdinamentoTask.equals("")) {
			if(TaskController.dataPerOrdinamentoTask.equals("dataModificaTask")) {
				if(TaskController.ordinaTaskInListaTask.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = taskRepository.findAll(pageable1);
				}else if(TaskController.ordinaTaskInListaTask.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").ascending());
					returnString = taskRepository.findAll(pageable1);
				}
				
			}else if(TaskController.dataPerOrdinamentoTask.equals("dataCreazioneTask")) {
				if(TaskController.ordinaTaskInListaTask.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = taskRepository.findAll(pageable1);
				}else if(TaskController.ordinaTaskInListaTask.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").ascending());
					returnString = taskRepository.findAll(pageable1);
				}
			}
		}
		
		//condizione di inserimento solo filtro CLIENTE
		if(TaskController.statoTaskInListaTask.equals("") && TaskController.ordinaTaskInListaTask.equals("") 
			&& TaskController.clienteIdTaskInListaTask != -1 && TaskController.dataPerOrdinamentoTask.equals("")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
				returnString = taskRepository.findByClienteId(TaskController.clienteIdTaskInListaTask, pageable1);
						
		}
		
//		//condizione di inserimento solo filtro PROGETTO
//		if(TaskController.statoTaskInListaTask.equals("") && TaskController.ordinaTaskInListaTask.equals("") 
//			&& TaskController.clienteIdTaskInListaTask == -1 && TaskController.dataPerOrdinamentoTask.equals("") && TaskController.progettoIdTaskInListaTask != -1) {
//				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
//				returnString = taskRepository.findByProgettoId(TaskController.clienteIdTaskInListaTask, pageable1);
//						
//		}
	
		//condizione filtri selezionati --> STATO e ORDINAMENTO (per ORDINAMENTO solo scelta se data modifica o data creazione - di default la piuù recente per prima)
		if(!TaskController.statoTaskInListaTask.equals("") && TaskController.ordinaTaskInListaTask.equals("") 
			&& TaskController.clienteIdTaskInListaTask == -1  && !TaskController.dataPerOrdinamentoTask.equals("")) {
					System.out.println("condizione filtri selezionati --> STATO e ORDINAMENTO (per ORDINAMENTO solo scelta se data modifica o data creazione - di default la piuù recente per prima)");
		   if(TaskController.statoTaskInListaTask.equals("aperto")) {
				if(TaskController.dataPerOrdinamentoTask.equals("dataModificaTask")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = taskRepository.findByActiveTaskPageable(pageable1);
						
				}else if(TaskController.dataPerOrdinamentoTask.equals("dataCreazioneTask")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = taskRepository.findByActiveTaskPageable(pageable1);
						
				}
				
			}
		   if(TaskController.statoTaskInListaTask.equals("chiuso")) {
				if(TaskController.dataPerOrdinamentoTask.equals("dataModificaTask")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = taskRepository.findByNotActiveTaskPageable(pageable1);
						
				}else if(TaskController.dataPerOrdinamentoTask.equals("dataCreazioneTask")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = taskRepository.findByNotActiveTaskPageable(pageable1);
						
				}
				
			}
		}
		
		//condizione filtri selezionati --> STATO e ORDINAMENTO (per ORDINAMENTO completo di scelta piu o meno recente)
		if(!TaskController.statoTaskInListaTask.equals("") && !TaskController.ordinaTaskInListaTask.equals("") 
			&& TaskController.clienteIdTaskInListaTask == -1  && !TaskController.dataPerOrdinamentoTask.equals("")) {
					System.out.println("condizione filtri selezionati --> STATO e ORDINAMENTO (per ORDINAMENTO completo di scelta piu o meno recente)");
		   if(TaskController.statoTaskInListaTask.equals("aperto")) {
				if(TaskController.dataPerOrdinamentoTask.equals("dataModificaTask")) {
				if(TaskController.ordinaTaskInListaTask.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = taskRepository.findByActiveTaskPageable(pageable1);
				}else if(TaskController.ordinaTaskInListaTask.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").ascending());
					returnString = taskRepository.findByActiveTaskPageable(pageable1);
				}
				
			}else if(TaskController.dataPerOrdinamentoTask.equals("dataCreazioneTask")) {
				if(TaskController.ordinaTaskInListaTask.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = taskRepository.findByActiveTaskPageable(pageable1);
				}else if(TaskController.ordinaTaskInListaTask.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").ascending());
					returnString = taskRepository.findByActiveTaskPageable(pageable1);
					}
				}
				
			}
		   
		    if(TaskController.statoTaskInListaTask.equals("chiuso")) {
				if(TaskController.dataPerOrdinamentoTask.equals("dataModificaTask")) {
				if(TaskController.ordinaTaskInListaTask.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = taskRepository.findByNotActiveTaskPageable(pageable1);
				}else if(TaskController.ordinaTaskInListaTask.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").ascending());
					returnString = taskRepository.findByNotActiveTaskPageable(pageable1);
				}
				
			}else if(TaskController.dataPerOrdinamentoTask.equals("dataCreazioneTask")) {
				if(TaskController.ordinaTaskInListaTask.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = taskRepository.findByNotActiveTaskPageable(pageable1);
				}else if(TaskController.ordinaTaskInListaTask.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").ascending());
					returnString = taskRepository.findByNotActiveTaskPageable(pageable1);
				}
			}
				
			}
		}
		
		//condizione filtri selezionati --> ORDINAMENTO e CLIENTE(per ORDINAMENTO solo scelta se data modifica o data creazione - di default la piuù recente per prima)
		if(TaskController.statoTaskInListaTask.equals("") && TaskController.ordinaTaskInListaTask.equals("") 
			&& TaskController.clienteIdTaskInListaTask != -1 && !TaskController.dataPerOrdinamentoTask.equals("")) {
					System.out.println("condizione filtri selezionati --> ORDINAMENTO e CLIENTE (per ORDINAMENTO solo scelta se data modifica o data creazione - di default la piuù recente per prima)");
			if(TaskController.ordinaTaskInListaTask.equals("dataModificaTask")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
				returnString = taskRepository.findByClienteId(TaskController.clienteIdTaskInListaTask, pageable1);
						
			} else if(TaskController.ordinaTaskInListaTask.equals("dataCreazioneTask")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
				returnString = taskRepository.findByClienteId(TaskController.clienteIdTaskInListaTask, pageable1);
						
			}
		}
		
		//condizione filtri selezionati --> ORDINAMENTO e CLIENTE(per ORDINAMENTO completo di scelta piu o meno recente)
		if(TaskController.statoTaskInListaTask.equals("") && !TaskController.ordinaTaskInListaTask.equals("") 
			&& TaskController.clienteIdTaskInListaTask != -1 && !TaskController.dataPerOrdinamentoTask.equals("")) {
					System.out.println("condizione filtri selezionati --> ORDINAMENTO e CLIENTE (per ORDINAMENTO completo di scelta piu o meno recente)");
			if(TaskController.dataPerOrdinamentoTask.equals("dataModificaTask")) {
				if(TaskController.ordinaTaskInListaTask.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = taskRepository.findByClienteId(TaskController.clienteIdTaskInListaTask,pageable1);
				}else if(TaskController.ordinaTaskInListaTask.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").ascending());
					returnString = taskRepository.findByClienteId(TaskController.clienteIdTaskInListaTask,pageable1);
				}
				
			}else if(TaskController.dataPerOrdinamentoTask.equals("dataCreazioneTask")) {
				if(TaskController.ordinaTaskInListaTask.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = taskRepository.findByClienteId(TaskController.clienteIdTaskInListaTask,pageable1);
				}else if(TaskController.ordinaTaskInListaTask.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").ascending());
					returnString = taskRepository.findByClienteId(TaskController.clienteIdTaskInListaTask,pageable1);
					}
				}
		}
		
		//condizione filtri selezionati --> STATO e CLIENTE (progetto con modifica più recente per primo)
		if(!TaskController.statoTaskInListaTask.equals("") && TaskController.ordinaTaskInListaTask.equals("") 
			&& TaskController.clienteIdTaskInListaTask != -1) {
					System.out.println("condizione filtri selezionati --> STATO e CLIENTE");
			if(TaskController.statoTaskInListaTask.equals("aperto")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
				returnString = taskRepository.findByClienteIdWhereTaskIsActive(TaskController.clienteIdTaskInListaTask, pageable1);
			}else if(TaskController.statoTaskInListaTask.equals("chiuso")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
				returnString = taskRepository.findByClienteIdWhereTaskIsNotActive(TaskController.clienteIdTaskInListaTask, pageable1);
			}
		}
		
		//condizione filtri selezionati --> STATO, ORDINAMENTO e CLIENTE(per ORDINAMENTO completo di scelta piu o meno recente)
		if(!TaskController.statoTaskInListaTask.equals("") && !TaskController.ordinaTaskInListaTask.equals("") 
			&& TaskController.clienteIdTaskInListaTask != -1  && !TaskController.dataPerOrdinamentoTask.equals("")) {
					System.out.println("condizione filtri selezionati --> STATO, ORDINAMENTO e CLIENTE (per ORDINAMENTO completo di scelta piu o meno recente)");
			if(TaskController.statoTaskInListaTask.equals("aperto")) {
				if(TaskController.dataPerOrdinamentoTask.equals("dataModificaTask")) {
				if(TaskController.ordinaTaskInListaTask.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = taskRepository.findByClienteIdWhereTaskIsActive(TaskController.clienteIdTaskInListaTask, pageable1);
				}else if(TaskController.ordinaTaskInListaTask.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").ascending());
					returnString = taskRepository.findByClienteIdWhereTaskIsActive(TaskController.clienteIdTaskInListaTask, pageable1);
				}
				
			}else if(TaskController.dataPerOrdinamentoTask.equals("dataCreazioneTask")) {
				if(TaskController.ordinaTaskInListaTask.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = taskRepository.findByClienteIdWhereTaskIsActive(TaskController.clienteIdTaskInListaTask, pageable1);
				}else if(TaskController.ordinaTaskInListaTask.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").ascending());
					returnString = taskRepository.findByClienteIdWhereTaskIsActive(TaskController.clienteIdTaskInListaTask, pageable1);
					}
				}
				
			}
		   
		    if(TaskController.statoTaskInListaTask.equals("chiuso")) {
				if(TaskController.dataPerOrdinamentoTask.equals("dataModificaTask")) {
				if(TaskController.ordinaTaskInListaTask.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = taskRepository.findByClienteIdWhereTaskIsNotActive(TaskController.clienteIdTaskInListaTask, pageable1);
				}else if(TaskController.ordinaTaskInListaTask.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").ascending());
					returnString = taskRepository.findByClienteIdWhereTaskIsNotActive(TaskController.clienteIdTaskInListaTask, pageable1);
				}
				
			}else if(TaskController.dataPerOrdinamentoTask.equals("dataCreazioneTask")) {
				if(TaskController.ordinaTaskInListaTask.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = taskRepository.findByClienteIdWhereTaskIsNotActive(TaskController.clienteIdTaskInListaTask, pageable1);
				}else if(TaskController.ordinaTaskInListaTask.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").ascending());
					returnString = taskRepository.findByClienteIdWhereTaskIsNotActive(TaskController.clienteIdTaskInListaTask, pageable1);
				}
			}
				
			}
		}
		
		//condizione filtri selezionati --> STATO, ORDINAMENTO e CLIENTE(per ORDINAMENTO solo scelta se data modifica o data creazione - di default la più recente per prima)
		if(!TaskController.statoTaskInListaTask.equals("") && !TaskController.ordinaTaskInListaTask.equals("") 
			&& TaskController.clienteIdTaskInListaTask != -1  && !TaskController.dataPerOrdinamentoTask.equals("")) {
					System.out.println("condizione filtri selezionati --> STATO, ORDINAMENTO e CLIENTE (per ORDINAMENTO solo scelta se data modifica o data creazione - di default la più recente per prima)");
			if(TaskController.statoTaskInListaTask.equals("aperto")) {
				if(TaskController.dataPerOrdinamentoTask.equals("dataModificaTask")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = taskRepository.findByClienteIdWhereTaskIsActive(TaskController.clienteIdTaskInListaTask, pageable1);
				
			}else if(TaskController.dataPerOrdinamentoTask.equals("dataCreazioneTask")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
				returnString = taskRepository.findByClienteIdWhereTaskIsActive(TaskController.clienteIdTaskInListaTask, pageable1);
				}
			}
		   
		    if(TaskController.statoTaskInListaTask.equals("chiuso")) {
				if(TaskController.dataPerOrdinamentoTask.equals("dataModificaTask")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = taskRepository.findByClienteIdWhereTaskIsNotActive(TaskController.clienteIdTaskInListaTask, pageable1);
				
			}else if(TaskController.dataPerOrdinamentoTask.equals("dataCreazioneTask")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = taskRepository.findByClienteIdWhereTaskIsNotActive(TaskController.clienteIdTaskInListaTask, pageable1);
			}
				
			}
		}
		
		return returnString;
		
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
				String statoTask = null;
				String ordinamentoTask = null;
				String nomeCliente = null; 
				String nomeProgetto = null;
				String dataOrdinamentoTask = null;
				if(TaskController.statoTaskInListaTask != null || TaskController.ordinaTaskInListaTask != null || TaskController.clienteIdTaskInListaTask != -1
								|| TaskController.progettoIdTaskInListaTask != -1) {
					
					if(TaskController.statoTaskInListaTask.equals("aperto")) {
						statoTask = "APERTO";
					}else if(TaskController.statoTaskInListaTask.equals("chiuso")) {
						statoTask = "CHIUSO";
					}
					
					if(TaskController.dataPerOrdinamentoTask.equals("dataModificaTask")) {
						dataOrdinamentoTask = "data di modifica";
					}else if(TaskController.dataPerOrdinamentoTask.equals("dataCreazioneTask")) {
						dataOrdinamentoTask = "data di creazione";
					}
					
					if(TaskController.ordinaTaskInListaTask.equals("piuRecente")) {
						ordinamentoTask = "più recente";
						
					}else if(TaskController.ordinaTaskInListaTask.equals("menoRecente")) {
						ordinamentoTask = "meno recente";
					}
					
					if(TaskController.clienteIdTaskInListaTask != -1) {
						
						Integer idClienteSelezionato = TaskController.clienteIdTaskInListaTask;
						nomeCliente = clienteRepository.findById(idClienteSelezionato).get().getLabelCliente();
					}
					
					if(TaskController.progettoIdTaskInListaTask != -1) {
						
						Integer idProgettoSelezionato = TaskController.progettoIdTaskInListaTask;
						nomeProgetto = progettoRepository.findById(idProgettoSelezionato).get().getName();
					}
					
				}
				
				model.addAttribute("filtroStatoTask", statoTask);
				model.addAttribute("filtroOrdinamentoTask", ordinamentoTask);
				model.addAttribute("filtroNomeCliente", nomeCliente);
				model.addAttribute("filtroNomeProgetto", nomeProgetto);
				model.addAttribute("filtroDataOrdinamentoTask", dataOrdinamentoTask);
				
			}
	
}
