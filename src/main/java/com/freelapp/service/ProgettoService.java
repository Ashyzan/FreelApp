package com.freelapp.service;


import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.freelapp.controller.ProgettoController;
import com.freelapp.model.Cliente;
import com.freelapp.model.Contatore;
import com.freelapp.model.Progetto;
import com.freelapp.model.Task;
import com.freelapp.repository.ClienteRepository;
import com.freelapp.repository.ProgettoRepository;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Subquery;

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
	
	//inizializzata variabile per metodo ordinaListaProgettiPerDataModificaPiuRecenteTraProgettoETask
	LocalDateTime dataModificaPiuRecente = null;
	
	public Page<Progetto> orderByDataModifica(int pageNumber){
		//criterio di default della lista dei progetti senza alcun filtro selezionato
		Pageable pageable = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
		
		//if che in base al tipo di ordinamento scelto varia il criterio di orninamento di pageable1 e lo manda nella query dinamica
		if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataModificaProgetto")) {
				if(ProgettoController.ordinaProgettoInListaProgetto.equals("piuRecente")) {
					pageable = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").descending());
					
				}else if(ProgettoController.ordinaProgettoInListaProgetto.equals("menoRecente")) {
					pageable = PageRequest.of(pageNumber -1, 12, Sort.by("dataModifica").ascending());
					
				}
				
			}else if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataCreazioneProgetto")) {
				if(ProgettoController.ordinaProgettoInListaProgetto.equals("piuRecente")) {
					pageable = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
					
				}else if(ProgettoController.ordinaProgettoInListaProgetto.equals("menoRecente")) {
					pageable = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").ascending());
					
				}
	}
		
		//returnString viene personalizzato ogni volta a seconda dei filtri selezionati 
		Page<Progetto> returnString = null;

		
		//inizializza le due specifiche per le query che verranno poi assegnate solo se selezionati i filtri di stato o la selezione cliente
		Specification<Progetto> specification_SelezioneCliente = null;
		Specification<Progetto> specificationStato;
		
		//switch che assegna la specifica per la query di stato
		switch(ProgettoController.statoProgettoInListaProgetto) {
				case "aperto":
					specificationStato = firltroProgetti_StatoAttivo();
					
					break;
				case "chiuso":
					specificationStato = firltroProgetti_StatoNonAttivo();
					break;
				default: specificationStato = null;
   
		}
		
		//if che assegna la specifica per la query se è stato scelto il cliente
		if(ProgettoController.clienteIdProgettoInListaProgetto != -1) {
			specification_SelezioneCliente = firltroProgetti_SelezioneCliente();
		}
		
		//if che a secondo che sia stato selezionato come filtro il cliente, lo stato o nessuno dei due costruisce la query dinamica
		if(ProgettoController.clienteIdProgettoInListaProgetto == -1 && ProgettoController.statoProgettoInListaProgetto == "") {
			returnString =  progettoRepository.findAll( pageable);
		}else if (ProgettoController.clienteIdProgettoInListaProgetto != -1){
							
			returnString =  progettoRepository.findAll(specification_SelezioneCliente.and(specificationStato) , pageable);
		}else {
			returnString =  progettoRepository.findAll(specificationStato , pageable);
		}
		
		
		
		
		return returnString;
		
	}
	
	//specificazione che genera la quey di filtro per progetti attivi
	Specification<Progetto> firltroProgetti_StatoAttivo(){
		return (root, query, criteriaBuilder) ->{		
			
			return criteriaBuilder.isNull(root.get("dataFine"));
					
		};
		
	}
	
	//specificazione che genera la quey di filtro per progetti non attivi
	Specification<Progetto> firltroProgetti_StatoNonAttivo(){
		return (root, query, criteriaBuilder) ->{			
			
			return criteriaBuilder.isNotNull(root.get("dataFine"));
					
		};
		
	}
	
	//specificazione che genera la quey di filtro per cliente selezionato
	Specification<Progetto> firltroProgetti_SelezioneCliente(){
		
		return (root, query, criteriaBuilder) ->{
			
			return criteriaBuilder.equal(root.get("cliente").as(Integer.class), ProgettoController.clienteIdProgettoInListaProgetto);
					
		};
		
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
				String statoProgetto = "";
				String filtroStatoProgetto = null;
				String ordinamentoProgetto = "";
				String filtroOrdinamentoProgetto = null;
				String nomeCliente = ""; 
				String filtroNomeCliente = null;
				String dataOrdinamentoProgetto = "";
				String filtroDataOrdinamentoProgetto = null;
				String testoFinale = "Nessun filtro applicato";
				if(ProgettoController.statoProgettoInListaProgetto != null || ProgettoController.ordinaProgettoInListaProgetto != null && ProgettoController.clienteIdProgettoInListaProgetto != -1) {

					if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataModificaProgetto")) {
						dataOrdinamentoProgetto = "<span>- ordinamento per <strong>data di modifica</strong></span>";
						filtroDataOrdinamentoProgetto = "data di modifica";
					}else if(ProgettoController.dataPerOrdinamentoProgetto.equals("dataCreazioneProgetto")) {
						dataOrdinamentoProgetto = "<span>- ordinamento per <strong>data di creazione</strong></div>";
						filtroDataOrdinamentoProgetto = "data di creazione";
					}
					
					if(ProgettoController.ordinaProgettoInListaProgetto.equals("piuRecente")) {
						ordinamentoProgetto = "<span> <strong>più recente<span> </strong>";
						filtroOrdinamentoProgetto = "più recente";
						
					}else if(ProgettoController.ordinaProgettoInListaProgetto.equals("menoRecente")) {
						ordinamentoProgetto = "<span> <strong>meno recente<span> </strong>";
						filtroOrdinamentoProgetto = "meno recente";
					}
					
					if(ProgettoController.clienteIdProgettoInListaProgetto != -1) {
						
						Integer idClienteSelezionato = ProgettoController.clienteIdProgettoInListaProgetto;
						filtroNomeCliente = clienteRepository.findById(idClienteSelezionato).get().getLabelCliente();
						nomeCliente ="<div>- cliente <strong>" +  filtroNomeCliente + "</strong></div>";
					}
					
					if(ProgettoController.statoProgettoInListaProgetto.equals("aperto")) {
						statoProgetto = "<div>- stato progetto <strong>APERTO</strong></div>";
						filtroStatoProgetto = "APERTO";
					}else if(ProgettoController.statoProgettoInListaProgetto.equals("chiuso")) {
						statoProgetto = "<div>- stato progetto <strong>CHIUSO</strong></div>";
						filtroStatoProgetto = "CHIUSO";
					}

					testoFinale = dataOrdinamentoProgetto + ordinamentoProgetto + nomeCliente + statoProgetto;
				}
				model.addAttribute("filtroStatoProgetto", filtroStatoProgetto);
				model.addAttribute("filtroOrdinamentoProgetto", filtroOrdinamentoProgetto);
				model.addAttribute("filtroNomeCliente",filtroNomeCliente);
				model.addAttribute("filtroDataOrdinamentoProgetto", filtroDataOrdinamentoProgetto);
				model.addAttribute("testoFinale", testoFinale);
				
			}
			
		
		//metodo che ordina lista dei progetti in base alla data di modifica piu recente tra se stesso ed i suoi progetti
		public List<Progetto> ordinaListaProgettiPerDataModificaPiuRecenteTraProgettoETask(){
			//ini
			List<Progetto> listaProgetti = new ArrayList<Progetto>();
			List<Progetto> listaInteraProgetti = progettoRepository.findAll();
			
			listaInteraProgetti.forEach(progetto ->{
				dataModificaPiuRecente = progetto.getDataModifica();
				progetto.getElencoTask().forEach(task ->{
					if(task.getDataModifica().isAfter(dataModificaPiuRecente)) {
						dataModificaPiuRecente = task.getDataModifica();
					}
				});
				progetto.setDataModifica(dataModificaPiuRecente);
				listaProgetti.add(progetto);
				dataModificaPiuRecente = null;
			});
			
			listaProgetti.sort(Comparator.comparing(Progetto::getDataModifica).reversed());
				
			
			return listaProgetti;
		}
}




