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
	
	public Page<Progetto> findPage(int pageNumber){
		Pageable pageable = PageRequest.of(pageNumber -1, 12, Sort.by("dataInizio").descending().and(Sort.by("name")));
		
		return progettoRepository.findAll(pageable);
		
	}
	
	public Page<Progetto> findSearchedPage(int pageNumber, String input){
		Pageable pageable = PageRequest.of(pageNumber -1, 4);
		return progettoRepository.search(input, pageable);
		
	}

}
