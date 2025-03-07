package com.freelapp.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.freelapp.model.Contatore;
import com.freelapp.model.Task;
import com.freelapp.repository.ContatoreRepository;
import com.freelapp.repository.TaskRepository;
import com.freelapp.service.ContatoreService;
import com.freelapp.service.TaskService;

@Controller
public class ContatoreController {

    @Autowired
    private ContatoreService contatoreservice;
  
    @Autowired
	  private TaskService taskservice;

    @Autowired
    private TaskRepository repositTask;

    @Autowired
    private ContatoreRepository repositContatore;
    
    public static Contatore contatoreInUso;
    
    public static Task taskInUso;

//    @GetMapping("/Contatore/timer/{id}")
//    public String gestioneTimer(@PathVariable("id") Integer taskId, @ModelAttribute("contatore") Contatore contatore,
//	    Model model, BindingResult bindingresult) {
//    	
//
//	// richiamo l'id del task
//	Task task = repositTask.getReferenceById(taskId);
//
//	if (task.getContatore() != null) {
//	    // parte per javascript: serve per collegare il finaltime da java a javascript
//	    // sul frontend
//	    contatoreservice.contatoreIsTrue(task, model);
//
//	    contatoreservice.contatoreIsRun(task, model);
//	    
//	    
//
//	    boolean contatoreIsRun = contatoreservice.contatoreIsRun(task, model);
//
//	    LocalDateTime restartTime = task.getContatore().getRestart();
//
//	    LocalDateTime timeNow = LocalDateTime.now();
//
//	    Long FinalTime = task.getContatore().getFinaltime();
//	    
//	    contatoreservice.timeExeed(bindingresult, task, model);
//
//	    if (contatoreIsRun == true && restartTime == null) {
//
//		task.getContatore()
//			.setFinaltime((long) (timeNow.getSecond() - task.getContatore().getStart().getSecond()));
//
//    	contatoreservice.timeExeed(bindingresult, task, model);
//
//	    } else if (contatoreIsRun == true && restartTime != null) {
//
//		task.getContatore().setFinaltime(
//			(long) (FinalTime + (timeNow.getSecond() - task.getContatore().getRestart().getSecond())));
//		contatoreservice.timeExeed(bindingresult, task, model);
//	    }
//
//	    model.addAttribute("finaltime", task.getContatore().getFinaltime());
//
//	}
//
//	model.addAttribute("contatore", contatore);
//
//	return "/Contatore/timer";
//    }

    @PostMapping("/start/{id}")
    public String startContatore(@PathVariable("id") Integer taskId, @ModelAttribute("contatore") Contatore contatore,
    		// l'endpoint passato dal model serve a far ritornare sulla pagina di partenza dopo aver cliccato su start
    		@ModelAttribute("endPoint") String endPoint,
	    Model model, BindingResult bindingresult) {
    	
	// richiamo l'id del task
	Task task = repositTask.getReferenceById(taskId);

	// CONTATORE IN RESTART
	if ((task.getContatore() != null) && (task.getContatore().getStop() == null)
		&& (task.getContatore().getStart() != null)) {

		
		
	    // CONTATORE IS RUN
	    if (contatoreservice.contatoreIsRun(task, model) != false) {

	    	// metto in pausa gli altri contatori
	    	contatoreservice.pauseOtherTimers();
	    	
		boolean contatoreIsRun = contatoreservice.contatoreIsRun(task, model);

		    LocalDateTime restartTime = task.getContatore().getRestart();

		    Long FinalTime = task.getContatore().getFinaltime();
		    LocalDateTime timeNow = LocalDateTime.now();
		if (contatoreIsRun == true && restartTime == null) {

			task.getContatore()
				.setFinaltime((long) (timeNow.getSecond() - task.getContatore().getStart().getSecond()));

		    } else if (contatoreIsRun == true && restartTime != null) {

			task.getContatore().setFinaltime(
				(long) (FinalTime + (timeNow.getSecond() - task.getContatore().getRestart().getSecond())));
		    }
		
		model.addAttribute("finaltime", task.getContatore().getFinaltime());
		// parte per javascript
		contatoreservice.contatoreIsTrue(task, model);
		contatoreservice.contatoreIsRun(task, model);

	    }

	    // CONTATORE IS NOT RUN
	    else if (contatoreservice.contatoreIsRun(task, model) != true) {

		// imposta il valore di restart
		task.getContatore().setRestart(LocalDateTime.now());

		Long FinalTime = task.getContatore().getFinaltime();

		// collego nel modello html il task e il contatore
		model.addAttribute("task", task);
		model.addAttribute("contatore", contatore);
		model.addAttribute("finaltime", FinalTime);

		repositContatore.save(task.getContatore());

		// parte per javascript
		contatoreservice.contatoreIsTrue(task, model);
		contatoreservice.contatoreIsRun(task, model);
	    }

	}

	// CONTATORE IN STOP
	else if ((task.getContatore() != null) && (task.getContatore().getStop() != null)) {
	    // se il contatore ha lo stop, non fare nulla

	    // Parte per Javascript
	    Long FinalTime = task.getContatore().getFinaltime();
	    contatoreservice.contatoreIsTrue(task, model);
	    contatoreservice.contatoreIsRun(task, model);
	    model.addAttribute("finaltime", FinalTime);
	}

	// CONTATORE RESETTATO
	else if ((task.getContatore() != null) && (task.getContatore().getStart() == null)) {
	    // imposta il valore di start
	    task.getContatore().setStart(LocalDateTime.now());

	    // collego nel modello html il task e il contatore
	    model.addAttribute("task", task);
	    model.addAttribute("contatore", contatore);

	    repositContatore.save(task.getContatore());

	    // Parte per Javascript
	    Long FinalTime = task.getContatore().getFinaltime();
	    contatoreservice.contatoreIsTrue(task, model);
	    contatoreservice.contatoreIsRun(task, model);
	    model.addAttribute("finaltime", FinalTime);
	}

	// CONTATORE IN START
	else {
		
		// Appena avvi un nuovo timer metti in pausa eventuali timer attivi
		

	    // istanzio un nuovo contatore
	    contatore = new Contatore();

	    // associo al task il nuovo contatore
	    task.setContatore(contatore);
	    
	 // metto in pausa gli altri contatori
    	contatoreservice.pauseOtherTimers();

	    // eseguo il TIMESTAMP
	    contatore.setStart(LocalDateTime.now());
	    contatore.setFinaltime(0l);

	    // collego nel modello html il task e il contatore
	    model.addAttribute("task", task);
	    model.addAttribute("contatore", contatore);

	    // salvo il contatore a DB
	    repositContatore.save(contatore);
	    // parte per javascript: serve per collegare il finaltime da java a javascript
	    // sul frontend
	    contatoreservice.contatoreIsTrue(task, model);
	    contatoreservice.contatoreIsRun(task, model);

	}

	contatoreInUso = contatore;
	taskInUso = task;

//	return "/Contatore/timer";
	return "redirect:" + endPoint;

    }


	 @PostMapping("/Contatore/pause/{id}")
    public String pauseContatore(@PathVariable("id") Integer taskId,
    		// l'endpoint passato dal model serve a far ritornare sulla pagina di partenza dopo aver cliccato su pause
    		@ModelAttribute("endPoint") String endPoint,
    		Model model) {

	// richiamo l'id del task
	Task task = repositTask.getReferenceById(taskId);
	Contatore contatore = task.getContatore();

	// verifica che il contatore esista e che non sia stato resettato
	if ((contatore != null) && (contatore.getStop() == null) && (contatore.getStart() != null)) {

	    // Parte per Javascript
	    Long FinalTime = task.getContatore().getFinaltime();
	    contatoreservice.contatoreIsTrue(task, model);
	    contatoreservice.contatoreIsRun(task, model);
	    model.addAttribute("finaltime", FinalTime);

	    // recupero timestamp di inizio e pausa
	    LocalDateTime PAUSE = task.getContatore().getPause();
	    LocalDateTime START = task.getContatore().getStart();
	    LocalDateTime RESTART = task.getContatore().getRestart();

	    // PAUSE DOPO START: se la pausa non esiste
	    if (PAUSE == null) {

		PAUSE = LocalDateTime.now();

		contatore.setPause(PAUSE);

		// metodo che calcola la differenza fra i due timestamp
		Long FinalTime1 = contatoreservice.findTime(START, PAUSE);

		// imposto il finaltime differenza fra stop e pausa - tipo Long
		contatore.setFinaltime(FinalTime1);

		// ad ogni clic la funzione prende gli stop e incrementa di 1
		contatore.setStop_numbers(contatore.getStop_numbers() + 1);

		model.addAttribute("finaltime", FinalTime1);
		// salvo il contatore
		repositContatore.save(contatore);

		if (task.getContatore() != null) {
		    contatoreservice.contatoreIsTrue(task, model);
		    contatoreservice.contatoreIsRun(task, model);
		}

	    }

	    // PAUSE DOPO RESTART: se la pausa esiste già
	    else if (PAUSE != null) {

		Long FinalTime2 = task.getContatore().getFinaltime();

		if (RESTART != null && PAUSE.isBefore(RESTART)) {

		    // imposto comunque una nuova pausa
		    PAUSE = LocalDateTime.now();

		    contatore.setPause(PAUSE);

		    Long timenow = contatoreservice.findTime(RESTART, PAUSE);

		    // aggiungo 1 secondo per sincronizzare java con javascript frontend
		    FinalTime2 = FinalTime2 + timenow + 1;

		    contatore.setFinaltime(FinalTime2);

		    // ad ogni clic la funzione prende gli stop e incrementa di 1
		    contatore.setStop_numbers(contatore.getStop_numbers() + 1);

		    // salvo il contatore
		    repositContatore.save(contatore);
		}

		contatoreservice.contatoreIsTrue(task, model);
		contatoreservice.contatoreIsRun(task, model);
		model.addAttribute("finaltime", FinalTime2);
	    }

	}

	// PAUSE DOPO STOP: verifico che il contatore non sia stoppato
	else if ((task.getContatore() != null) && (task.getContatore().getStop() != null)) {
	    // Parte per Javascript
	    Long FinalTime = task.getContatore().getFinaltime();
	    model.addAttribute("finaltime", FinalTime);
	}

//	return "/Contatore/timer";
	return "redirect:" + endPoint;
    }


	@PostMapping("/Contatore/stop/{id}")
	public String stopContatore(@PathVariable("id") Integer taskId, Model model) {
		// richiamo l'id del task
		Task task = repositTask.getReferenceById(taskId);

		// verifica che il contatore esista
		if (task.getContatore() != null) {
			// parte per javascript: serve per collegare il finaltime da java a javascript
			// sul frontend
			contatoreservice.contatoreIsTrue(task, model);
			contatoreservice.contatoreIsRun(task, model);

			// setto le variabili
			LocalDateTime STOP = task.getContatore().getStop();
			LocalDateTime START = task.getContatore().getStart();
			LocalDateTime RESTART = task.getContatore().getRestart();
			LocalDateTime PAUSE = task.getContatore().getPause();
			Contatore contatore = task.getContatore();

			// PRIMO IF - IL CONTATORE ESISTE E NON È RESETTATO
			if ((STOP == null) && (START != null)) {

				// CASO 1: IL CONTATORE è FERMO e RESTART è NULLO
				if ((PAUSE != null) && (RESTART == null)) {

					// eseguo il TIMESTAMP dello STOP e chiudo il contatore
					STOP = LocalDateTime.now();

					contatore.setStop(STOP);
					
					// salvo in automatico la data fine task in corrispondenza dello stop contatore
			    	taskservice.setStopTaskDate(STOP, taskId);
					repositTask.save(task);
					
					Long FinalTime = contatore.getFinaltime();

					model.addAttribute("finaltime", FinalTime);

					repositContatore.save(task.getContatore());
				}

				// CASO 2: IL CONTATORE è FERMO: RESTART è prima della PAUSA
				else if ((PAUSE != null) && (RESTART.isBefore(PAUSE))) {

					// eseguo il TIMESTAMP dello STOP e chiudo il contatore
					STOP = LocalDateTime.now();

					contatore.setStop(STOP);
					
					// salvo in automatico la data fine task in corrispondenza dello stop contatore
			    	taskservice.setStopTaskDate(STOP, taskId);
					repositTask.save(task);

					repositContatore.save(task.getContatore());

					Long FinalTime = contatore.getFinaltime();

					contatoreservice.contatoreIsTrue(task, model);
					contatoreservice.contatoreIsRun(task, model);
					model.addAttribute("finaltime", FinalTime);
				}

				// CASO 3: IL CONTATORE è ATTIVO E NON È MAI STATO FERMATO
				else if ((PAUSE == null) && (RESTART == null)) {

					// eseguo il TIMESTAMP dello STOP e chiudo il contatore
					STOP = LocalDateTime.now();
					// setto lo stop a db
					contatore.setStop(STOP);
					
					// salvo in automatico la data fine task in corrispondenza dello stop contatore
			    	taskservice.setStopTaskDate(STOP, taskId);
					repositTask.save(task);
					
					// calcolo il tempo trascorso
					Long FinalTime1 = contatoreservice.findTime(START, STOP);
					// imposto il finaltime
					contatore.setFinaltime(FinalTime1);
					// salvo il contatore
					repositContatore.save(contatore);

					// Parte per Javascript
					contatoreservice.contatoreIsTrue(task, model);
					contatoreservice.contatoreIsRun(task, model);
					model.addAttribute("finaltime", FinalTime1);

				}

				// CASO 4: IL CONTATORE È ATTIVO ED È STATO PIU VOLTE AVVIATO
				else if ((RESTART != null) && (RESTART.isAfter(PAUSE))) {

					// eseguo il TIMESTAMP dello STOP e chiudo il contatore
					STOP = LocalDateTime.now();

					contatore.setStop(STOP);
					
					// salvo in automatico la data fine task in corrispondenza dello stop contatore
			    	taskservice.setStopTaskDate(STOP, taskId);
					repositTask.save(task);

					// metodo che calcola la differenza fra i due timestamp
					Long lastTime = contatoreservice.findTime(RESTART, STOP);

					Long prevSec = task.getContatore().getFinaltime();

					Long FinalTime = lastTime + prevSec;

					// imposto il finaltime differenza fra stop e pausa - tipo Long
					contatore.setFinaltime(FinalTime);

					repositContatore.save(task.getContatore());
				}

			}

			// SECONDO IF - CONTATORE FERMO IN STOP
			else if (STOP != null) {
				// Parte per Javascript
				Long FinalTime = task.getContatore().getFinaltime();
				contatoreservice.contatoreIsTrue(task, model);
				contatoreservice.contatoreIsRun(task, model);
				model.addAttribute("finaltime", FinalTime);
			}
			
			// TERZO IF, SE IL CONTATORE NON ESISTE (CASO DI INSERIMENTO DI ORE LAVORATE)
			else if(task.getContatore() == null) {
				
				
			}

		}
    contatoreInUso = null;
	taskInUso = null;//	return "/Contatore/timer";
	return "redirect:/Task/" + task.getId();
	}

	

    @PostMapping("/Contatore/reset/{id}")
    public String resetContatore(@PathVariable("id") Integer taskId,
    		// l'endpoint passato dal model serve a far ritornare sulla pagina di partenza dopo aver cliccato su start
    		@ModelAttribute("endPoint") String endPoint) {

		// richiamo l'id del task
		Task task = repositTask.getReferenceById(taskId);

		// verifica che il contatore esista
		if (task.getContatore() != null) {

			// eseguo il RESET
			task.getContatore().setStop(null);
			task.getContatore().setStart(null);
			task.getContatore().setPause(null);
			task.getContatore().setFinaltime(0l);
			task.getContatore().setStop_numbers(0);
			task.getContatore().setRestart(null);

			// salvo il contatore
			repositContatore.save(task.getContatore());

		}

		//return "/Contatore/timer";

	return "redirect:" + endPoint;
	}

	}
