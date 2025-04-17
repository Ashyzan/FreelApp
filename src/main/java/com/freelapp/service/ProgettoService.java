package com.freelapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.freelapp.model.Progetto;
import com.freelapp.model.Task;
import com.freelapp.repository.ProgettoRepository;

@Service
public class ProgettoService {
	
	@Autowired
	private ProgettoRepository progettoRepository;

	public List<Progetto> findAll(){
		return progettoRepository.findAll();
		
	}
	
	public Page<Progetto> orderByDataInizio(int pageNumber){
		Pageable pageable1 = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending());
		
		return progettoRepository.findAll(pageable1);
		
		
	}
	
	public Page<Progetto> orderByClient(int pageNumber){
		Pageable pageable2 = PageRequest.of(pageNumber -1, 12, Sort.by("cliente.labelCliente").ascending());
		
		return progettoRepository.findAll(pageable2);		
		
	}

	
	public Page<Progetto> findByArchivio(int pageNumber){
		Pageable pageableArchive = PageRequest.of(pageNumber -1, 12, Sort.by("archivia").descending());
		
		return progettoRepository.findByArchivia(true,pageableArchive);
		
		
	}
	
	public Page<Progetto> findSearchedPageByDataInizio(int pageNumber, String input){
		Pageable pageable = PageRequest.of(pageNumber -1, 4, Sort.by("dataInizio").descending());
		return progettoRepository.search(input, pageable);
		
	}
	
	public Page<Progetto> findSearchedPageByClient(int pageNumber, String input){
		Pageable pageable = PageRequest.of(pageNumber -1, 4, Sort.by("cliente.labelCliente").ascending());
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
						finalTimeProgetto += task.getContatore().getFinaltime();
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
}
