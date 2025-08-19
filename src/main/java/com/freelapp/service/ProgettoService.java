package com.freelapp.service;


import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.freelapp.controller.ProgettoController;
import com.freelapp.model.Cliente;
import com.freelapp.model.Contatore;
import com.freelapp.model.Progetto;
import com.freelapp.model.Task;
import com.freelapp.repository.ClienteRepository;
import com.freelapp.repository.ProgettoRepository;

@Service
public class ProgettoService {
	
	@Autowired
	private ProgettoRepository progettoRepository;

	@Autowired
    private TaskService taskservice;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<Progetto> findAll(){
		return progettoRepository.findAll();
		
	}
	
	public Page<Progetto> orderByDataModifica(int pageNumber){
		
		Pageable pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
		//returnString viene personalizzato ogni volta a seconda dei filtri selezionati 
		Page<Progetto> returnString = null;
		returnString =  progettoRepository.findAll(pageable1);
		
		//condizione di inserimento solo filtro di STATO
		if(!ProgettoController.statoProgettoInListaProgetto.equals("") && ProgettoController.ordinaProgettoInListaProgetto.equals("") 
				&& ProgettoController.clienteIdProgettoInListaProgetto == -1 && ProgettoController.dataPerOrdinamentoProgetto.equals("")) {
			if(ProgettoController.statoProgettoInListaProgetto.equals("aperto")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
				returnString = progettoRepository.findByActiveProjectPageable(pageable1);
						
			} else if (ProgettoController.statoProgettoInListaProgetto.equals("chiuso")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());	
				returnString = progettoRepository.findByNotActiveProjectPageable(pageable1);
						
			}
		}
		
		//condizione di inserimento solo filtro di ORDINAMENTO(solo scelta se data modifica o data creazione - di default la piuù recente per prima)
		if(ProgettoController.statoProgettoInListaProgetto.equals("") && ProgettoController.ordinaProgettoInListaProgetto.equals("") 
			&& ProgettoController.clienteIdProgettoInListaProgetto == -1 && !ProgettoController.dataPerOrdinamentoProgetto.equals("")) {
			if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataModificaProgetto")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
				returnString = progettoRepository.findAll(pageable1);
						
			}else if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataCreazioneProgetto")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
				returnString = progettoRepository.findAll(pageable1);
						
			}
		}
		
		//condizione di inserimento solo filtro di ORDINAMENTO(completo di scelta piu o meno recente)
		if(ProgettoController.statoProgettoInListaProgetto.equals("") && !ProgettoController.ordinaProgettoInListaProgetto.equals("") 
			&& ProgettoController.clienteIdProgettoInListaProgetto == -1 && !ProgettoController.dataPerOrdinamentoProgetto.equals("")) {
			if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataModificaProgetto")) {
				if(ProgettoController.ordinaProgettoInListaProgetto.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = progettoRepository.findAll(pageable1);
				}else if(ProgettoController.ordinaProgettoInListaProgetto.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").ascending());
					returnString = progettoRepository.findAll(pageable1);
				}
				
			}else if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataCreazioneProgetto")) {
				if(ProgettoController.ordinaProgettoInListaProgetto.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = progettoRepository.findAll(pageable1);
				}else if(ProgettoController.ordinaProgettoInListaProgetto.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").ascending());
					returnString = progettoRepository.findAll(pageable1);
				}
			}
		}
		
		//condizione di inserimento solo filtro CLIENTE
		if(ProgettoController.statoProgettoInListaProgetto.equals("") && ProgettoController.ordinaProgettoInListaProgetto.equals("") 
			&& ProgettoController.clienteIdProgettoInListaProgetto != -1 && ProgettoController.dataPerOrdinamentoProgetto.equals("")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
				returnString = progettoRepository.findByClienteId(ProgettoController.clienteIdProgettoInListaProgetto, pageable1);
						
		}
	
		//condizione filtri selezionati --> STATO e ORDINAMENTO (per ORDINAMENTO solo scelta se data modifica o data creazione - di default la piuù recente per prima)
		if(!ProgettoController.statoProgettoInListaProgetto.equals("") && ProgettoController.ordinaProgettoInListaProgetto.equals("") 
			&& ProgettoController.clienteIdProgettoInListaProgetto == -1  && !ProgettoController.dataPerOrdinamentoProgetto.equals("")) {
					System.out.println("condizione filtri selezionati --> STATO e ORDINAMENTO (per ORDINAMENTO solo scelta se data modifica o data creazione - di default la piuù recente per prima)");
		   if(ProgettoController.statoProgettoInListaProgetto.equals("aperto")) {
				if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataModificaProgetto")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = progettoRepository.findByActiveProjectPageable(pageable1);
						
				}else if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataCreazioneProgetto")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = progettoRepository.findByActiveProjectPageable(pageable1);
						
				}
				
			}
		   if(ProgettoController.statoProgettoInListaProgetto.equals("chiuso")) {
				if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataModificaProgetto")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = progettoRepository.findByNotActiveProjectPageable(pageable1);
						
				}else if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataCreazioneProgetto")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = progettoRepository.findByNotActiveProjectPageable(pageable1);
						
				}
				
			}
		}
		
		//condizione filtri selezionati --> STATO e ORDINAMENTO (per ORDINAMENTO completo di scelta piu o meno recente)
		if(!ProgettoController.statoProgettoInListaProgetto.equals("") && !ProgettoController.ordinaProgettoInListaProgetto.equals("") 
			&& ProgettoController.clienteIdProgettoInListaProgetto == -1  && !ProgettoController.dataPerOrdinamentoProgetto.equals("")) {
					System.out.println("condizione filtri selezionati --> STATO e ORDINAMENTO (per ORDINAMENTO completo di scelta piu o meno recente)");
		   if(ProgettoController.statoProgettoInListaProgetto.equals("aperto")) {
				if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataModificaProgetto")) {
				if(ProgettoController.ordinaProgettoInListaProgetto.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = progettoRepository.findByActiveProjectPageable(pageable1);
				}else if(ProgettoController.ordinaProgettoInListaProgetto.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").ascending());
					returnString = progettoRepository.findByActiveProjectPageable(pageable1);
				}
				
			}else if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataCreazioneProgetto")) {
				if(ProgettoController.ordinaProgettoInListaProgetto.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = progettoRepository.findByActiveProjectPageable(pageable1);
				}else if(ProgettoController.ordinaProgettoInListaProgetto.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").ascending());
					returnString = progettoRepository.findByActiveProjectPageable(pageable1);
					}
				}
				
			}
		   
		    if(ProgettoController.statoProgettoInListaProgetto.equals("chiuso")) {
				if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataModificaProgetto")) {
				if(ProgettoController.ordinaProgettoInListaProgetto.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = progettoRepository.findByNotActiveProjectPageable(pageable1);
				}else if(ProgettoController.ordinaProgettoInListaProgetto.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").ascending());
					returnString = progettoRepository.findByNotActiveProjectPageable(pageable1);
				}
				
			}else if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataCreazioneProgetto")) {
				if(ProgettoController.ordinaProgettoInListaProgetto.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = progettoRepository.findByNotActiveProjectPageable(pageable1);
				}else if(ProgettoController.ordinaProgettoInListaProgetto.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").ascending());
					returnString = progettoRepository.findByNotActiveProjectPageable(pageable1);
				}
			}
				
			}
		}
		
		//condizione filtri selezionati --> ORDINAMENTO e CLIENTE(per ORDINAMENTO solo scelta se data modifica o data creazione - di default la piuù recente per prima)
		if(ProgettoController.statoProgettoInListaProgetto.equals("") && ProgettoController.ordinaProgettoInListaProgetto.equals("") 
			&& ProgettoController.clienteIdProgettoInListaProgetto != -1 && !ProgettoController.dataPerOrdinamentoProgetto.equals("")) {
					System.out.println("condizione filtri selezionati --> ORDINAMENTO e CLIENTE (per ORDINAMENTO solo scelta se data modifica o data creazione - di default la piuù recente per prima)");
			if(ProgettoController.ordinaProgettoInListaProgetto.equals("dataModificaProgetto")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
				returnString = progettoRepository.findByClienteId(ProgettoController.clienteIdProgettoInListaProgetto, pageable1);
						
			} else if(ProgettoController.ordinaProgettoInListaProgetto.equals("dataCreazioneProgetto")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
				returnString = progettoRepository.findByClienteId(ProgettoController.clienteIdProgettoInListaProgetto, pageable1);
						
			}
		}
		
		//condizione filtri selezionati --> ORDINAMENTO e CLIENTE(per ORDINAMENTO completo di scelta piu o meno recente)
		if(ProgettoController.statoProgettoInListaProgetto.equals("") && !ProgettoController.ordinaProgettoInListaProgetto.equals("") 
			&& ProgettoController.clienteIdProgettoInListaProgetto != -1 && !ProgettoController.dataPerOrdinamentoProgetto.equals("")) {
					System.out.println("condizione filtri selezionati --> ORDINAMENTO e CLIENTE (per ORDINAMENTO completo di scelta piu o meno recente)");
			if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataModificaProgetto")) {
				if(ProgettoController.ordinaProgettoInListaProgetto.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = progettoRepository.findByClienteId(ProgettoController.clienteIdProgettoInListaProgetto,pageable1);
				}else if(ProgettoController.ordinaProgettoInListaProgetto.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").ascending());
					returnString = progettoRepository.findByClienteId(ProgettoController.clienteIdProgettoInListaProgetto,pageable1);
				}
				
			}else if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataCreazioneProgetto")) {
				if(ProgettoController.ordinaProgettoInListaProgetto.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = progettoRepository.findByClienteId(ProgettoController.clienteIdProgettoInListaProgetto,pageable1);
				}else if(ProgettoController.ordinaProgettoInListaProgetto.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").ascending());
					returnString = progettoRepository.findByClienteId(ProgettoController.clienteIdProgettoInListaProgetto,pageable1);
					}
				}
		}
		
		//condizione filtri selezionati --> STATO e CLIENTE (progetto con modifica più recente per primo)
		if(!ProgettoController.statoProgettoInListaProgetto.equals("") && ProgettoController.ordinaProgettoInListaProgetto.equals("") 
			&& ProgettoController.clienteIdProgettoInListaProgetto != -1) {
					System.out.println("condizione filtri selezionati --> STATO e CLIENTE");
			if(ProgettoController.statoProgettoInListaProgetto.equals("aperto")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
				returnString = progettoRepository.findByClienteIdWhereProjectIsActive(ProgettoController.clienteIdProgettoInListaProgetto, pageable1);
			}else if(ProgettoController.statoProgettoInListaProgetto.equals("chiuso")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
				returnString = progettoRepository.findByClienteIdWhereProjectIsNotActive(ProgettoController.clienteIdProgettoInListaProgetto, pageable1);
			}
		}
		
		//condizione filtri selezionati --> STATO, ORDINAMENTO e CLIENTE(per ORDINAMENTO completo di scelta piu o meno recente)
		if(!ProgettoController.statoProgettoInListaProgetto.equals("") && !ProgettoController.ordinaProgettoInListaProgetto.equals("") 
			&& ProgettoController.clienteIdProgettoInListaProgetto != -1  && !ProgettoController.dataPerOrdinamentoProgetto.equals("")) {
					System.out.println("condizione filtri selezionati --> STATO, ORDINAMENTO e CLIENTE (per ORDINAMENTO completo di scelta piu o meno recente)");
			if(ProgettoController.statoProgettoInListaProgetto.equals("aperto")) {
				if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataModificaProgetto")) {
				if(ProgettoController.ordinaProgettoInListaProgetto.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = progettoRepository.findByClienteIdWhereProjectIsActive(ProgettoController.clienteIdProgettoInListaProgetto, pageable1);
				}else if(ProgettoController.ordinaProgettoInListaProgetto.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").ascending());
					returnString = progettoRepository.findByClienteIdWhereProjectIsActive(ProgettoController.clienteIdProgettoInListaProgetto, pageable1);
				}
				
			}else if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataCreazioneProgetto")) {
				if(ProgettoController.ordinaProgettoInListaProgetto.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = progettoRepository.findByClienteIdWhereProjectIsActive(ProgettoController.clienteIdProgettoInListaProgetto, pageable1);
				}else if(ProgettoController.ordinaProgettoInListaProgetto.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").ascending());
					returnString = progettoRepository.findByClienteIdWhereProjectIsActive(ProgettoController.clienteIdProgettoInListaProgetto, pageable1);
					}
				}
				
			}
		   
		    if(ProgettoController.statoProgettoInListaProgetto.equals("chiuso")) {
				if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataModificaProgetto")) {
				if(ProgettoController.ordinaProgettoInListaProgetto.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = progettoRepository.findByClienteIdWhereProjectIsNotActive(ProgettoController.clienteIdProgettoInListaProgetto, pageable1);
				}else if(ProgettoController.ordinaProgettoInListaProgetto.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").ascending());
					returnString = progettoRepository.findByClienteIdWhereProjectIsNotActive(ProgettoController.clienteIdProgettoInListaProgetto, pageable1);
				}
				
			}else if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataCreazioneProgetto")) {
				if(ProgettoController.ordinaProgettoInListaProgetto.equals("piuRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = progettoRepository.findByClienteIdWhereProjectIsNotActive(ProgettoController.clienteIdProgettoInListaProgetto, pageable1);
				}else if(ProgettoController.ordinaProgettoInListaProgetto.equals("menoRecente")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").ascending());
					returnString = progettoRepository.findByClienteIdWhereProjectIsNotActive(ProgettoController.clienteIdProgettoInListaProgetto, pageable1);
				}
			}
				
			}
		}
		
		//condizione filtri selezionati --> STATO, ORDINAMENTO e CLIENTE(per ORDINAMENTO solo scelta se data modifica o data creazione - di default la più recente per prima)
		if(!ProgettoController.statoProgettoInListaProgetto.equals("") && !ProgettoController.ordinaProgettoInListaProgetto.equals("") 
			&& ProgettoController.clienteIdProgettoInListaProgetto != -1  && !ProgettoController.dataPerOrdinamentoProgetto.equals("")) {
					System.out.println("condizione filtri selezionati --> STATO, ORDINAMENTO e CLIENTE (per ORDINAMENTO solo scelta se data modifica o data creazione - di default la più recente per prima)");
			if(ProgettoController.statoProgettoInListaProgetto.equals("aperto")) {
				if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataModificaProgetto")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = progettoRepository.findByClienteIdWhereProjectIsActive(ProgettoController.clienteIdProgettoInListaProgetto, pageable1);
				
			}else if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataCreazioneProgetto")) {
				pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
				returnString = progettoRepository.findByClienteIdWhereProjectIsActive(ProgettoController.clienteIdProgettoInListaProgetto, pageable1);
				}
			}
		   
		    if(ProgettoController.statoProgettoInListaProgetto.equals("chiuso")) {
				if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataModificaProgetto")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					returnString = progettoRepository.findByClienteIdWhereProjectIsNotActive(ProgettoController.clienteIdProgettoInListaProgetto, pageable1);
				
			}else if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataCreazioneProgetto")) {
					pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					returnString = progettoRepository.findByClienteIdWhereProjectIsNotActive(ProgettoController.clienteIdProgettoInListaProgetto, pageable1);
			}
				
			}
		}
		
		return returnString;
		
	}
	
	
	
	
	
	

	
	
	public Page<Progetto> findByArchivio(int pageNumber){
		Pageable pageableArchive = PageRequest.of(pageNumber -1, 12, Sort.by("archivia").descending());
		
		return progettoRepository.findByArchivia(true,pageableArchive);
		
		
	}
	
	public Page<Progetto> findSearchedPageByDataModifica(int pageNumber, String input){
		Pageable pageable = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
		return progettoRepository.search(input, pageable);
		
	}
	
	
	public Page<Progetto> findSearchedPageByArchiviati(Boolean isArchived, int pageNumber, String input){
		//Pageable pageableArch = PageRequest.of(pageNumber -1, 4, Sort.by("dataModifica").descending());
		Pageable pageableArch = PageRequest.of(pageNumber -1, 4);
		return progettoRepository.searchArchiviati(isArchived, input, pageableArch);
		
	}
	
	//metodo che passa al model tutti i dati relativi alla tipologia di progetto ai fini statistici
	public void tipologiaFromProgettoToModel(Progetto progetto, Model model) {
		
		model.addAttribute("tipologia", progetto.getTipologia());
		model.addAttribute("budgetMonetario", progetto.getBudgetMonetario());
		model.addAttribute("tariffaOraria", progetto.getTariffaOraria());
		model.addAttribute("budgetOre", progetto.getBudgetOre());
		
	}

	//metodo che verifica la tipologia del progetto e passa i dati al modello per il calcolo delle statistiche
	public void calcoloStatisticheTipologiaFromProgettoToModel(Progetto progetto, Model model) {
		
		if(progetto.getTipologia().contains("budget")) {
					double oreTotaliProgetto = progetto.getBudgetMonetario() / progetto.getTariffaOraria();
					double oreRimanenti = oreTotaliProgetto;
					//valore (finalTimeProgetto) che deriva dalla somma di tutti i finalTime di progetti
					int finalTimeProgetto = 0;
					for(Task task : progetto.getElencoTask()) {
						if(task.getContatore() != null) {
							finalTimeProgetto += task.getContatore().getFinaltime();							
						}
					}
					
					oreRimanenti = oreTotaliProgetto - (finalTimeProgetto/3600);
					model.addAttribute("oreRimanenti", Math.round(oreRimanenti));
				}
		if(progetto.getTipologia().contains("limite-tempo")) {
			double guadagnoStimato = progetto.getBudgetOre() * progetto.getTariffaOraria();
			double guadagnoProvvisorio = 0;
			for(Task task : progetto.getElencoTask()) {
				//trasformo il finalTime di ogni task in ore e lo moltiplico per la tariffa oraria e poi lo sommo agli altri
						guadagnoProvvisorio += ((task.getContatore().getFinaltime())/3600)* progetto.getTariffaOraria();
					}
			double percentualeGuadagno = (guadagnoProvvisorio / guadagnoStimato) * 100;
			model.addAttribute("guadagnoStimato", guadagnoStimato);
			model.addAttribute("guadagnoProvvisorio", guadagnoProvvisorio);
			model.addAttribute("percentualeGuadagno", percentualeGuadagno);
		}
		
		if(progetto.getTipologia().contains("tariffa-oraria")) {
			double guadagnoProvvisorio = 0;
			for(Task task : progetto.getElencoTask()) {
				//trasformo il finalTime di ogni task in ore e lo moltiplico per la tariffa oraria e poi lo sommo agli altri
						guadagnoProvvisorio += ((task.getContatore().getFinaltime())/3600)* progetto.getTariffaOraria();
						System.out.println("guadagno provvosorio: " + guadagnoProvvisorio);
					}
			model.addAttribute("guadagnoProvvisorio", guadagnoProvvisorio);
		}
		
	}
	
	//metodo che calcola il guadagno totale del progetto
		public void guadagnoTotaleProgetto(Progetto progetto, Model model) {
		
	double guadagnoTotaleProgettoR = 0;
	for(Task task : progetto.getElencoTask()) {
		if(task.getContatore() != null) {
			guadagnoTotaleProgettoR += ((task.getContatore().getFinaltime().doubleValue())/3600)* progetto.getTariffaOraria();							
	
						}
		else {}
				}
			String guadagnoTotaleProgetto = String.format("%.2f", guadagnoTotaleProgettoR);
			model.addAttribute("guadagnoTotaleProgetto", guadagnoTotaleProgetto);
			}
		
	//metodo che calcola il guadagno totale dei task attivi
	public void guadagnoTotaleTaskAttivi(Progetto progetto, Model model) {
				
			double guadagnoTotaleTaskAttiviResult = 0;
			
			
			for(Task task : progetto.getElencoTask()) {
				
				Boolean stato = task.getStato().equals("chiuso");
				
				if(task.getContatore() != null) {
					
					if(!stato) {
					
						guadagnoTotaleTaskAttiviResult += ((task.getContatore().getFinaltime().doubleValue())/3600)* progetto.getTariffaOraria();
						}
					
							}
						}
								String guadagnoTotaleTaskAttivi = String.format("%.2f", guadagnoTotaleTaskAttiviResult);
								model.addAttribute("guadagnoTotaleTaskAttivi", guadagnoTotaleTaskAttivi);
					} 
					
					
		
		//metodo che calcola il guadagno totale dei task chiusi
		public void guadagnoTotaleTaskChiusi(Progetto progetto, Model model) {
					
				double guadagnoTotaleTaskChiusiResult = 0;
				
				for(Task task : progetto.getElencoTask()) {
					if( task.getContatore() != null)  {
						
						if( task.getStato().equals("chiuso")) {
							guadagnoTotaleTaskChiusiResult += ((task.getContatore().getFinaltime().doubleValue())/3600)* progetto.getTariffaOraria();	
									}	
								}
							}
				String guadagnoTotaleTaskChiusi = String.format("%.2f", guadagnoTotaleTaskChiusiResult);
				model.addAttribute("guadagnoTotaleTaskChiusi", guadagnoTotaleTaskChiusi);
						}
			
				// calcolo il finaltime totale del progetto sommando  i finaltime dei task aperti o chusi
				public void finaltimeTotaleProgetto(Progetto progetto, Model model) {
					Long finaltime = 0l; 
					Task tasknew = new Task();
					Contatore contatore = new Contatore();
					
					for(Task task : progetto.getElencoTask()) {
						if(task.getContatore() != null) {
							task.getContatore().getFinaltime();
							finaltime += task.getContatore().getFinaltime();
						}
					}
					tasknew.setContatore(contatore);
					tasknew.getContatore().setFinaltime(finaltime);
					String finaltimeTotaleProgetto = taskservice.Timer(tasknew);					
					model.addAttribute("finaltimeTotaleProgetto", finaltimeTotaleProgetto);
				}
				
				
			
			//metodo che genera la stringa dei filtri applicati da far vedere all'utente in lista progetti
			public void stringaFiltriInListaProgetti(Model model) {
				
				//inizializzo le tre stringhe che poi verranno passate al model e utilizzate da javascript per riempire la lista dei filtri applicati
				String statoProgetto = null;
				String ordinamentoProgetto = null;
				String nomeCliente = null; 
				String dataOrdinamentoProgetto = null;
				if(ProgettoController.statoProgettoInListaProgetto != null || ProgettoController.ordinaProgettoInListaProgetto != null && ProgettoController.clienteIdProgettoInListaProgetto != -1) {
					
					if(ProgettoController.statoProgettoInListaProgetto.equals("aperto")) {
						statoProgetto = "APERTO";
					}else if(ProgettoController.statoProgettoInListaProgetto.equals("chiuso")) {
						statoProgetto = "CHIUSO";
					}
					
					if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataModificaProgetto")) {
						dataOrdinamentoProgetto = "data di modifica";
					}else if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataCreazioneProgetto")) {
						dataOrdinamentoProgetto = "data di creazione";
					}
					
					if(ProgettoController.ordinaProgettoInListaProgetto.equals("piuRecente")) {
						ordinamentoProgetto = "più recente";
						
					}else if(ProgettoController.ordinaProgettoInListaProgetto.equals("menoRecente")) {
						ordinamentoProgetto = "meno recente";
					}
					
					if(ProgettoController.clienteIdProgettoInListaProgetto != -1) {
						
						Integer idClienteSelezionato = ProgettoController.clienteIdProgettoInListaProgetto;
						nomeCliente = clienteRepository.findById(idClienteSelezionato).get().getLabelCliente();
					}
					
				}
				
				model.addAttribute("filtroStatoProgetto", statoProgetto);
				model.addAttribute("filtroOrdinamentoProgetto", ordinamentoProgetto);
				model.addAttribute("filtroNomeCliente", nomeCliente);
				model.addAttribute("filtroDataOrdinamentoProgetto", dataOrdinamentoProgetto);
				
			}
	
	
}




