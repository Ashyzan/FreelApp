package com.freelapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.freelapp.model.Progetto;
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
	


}
