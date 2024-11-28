package com.freelapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.freelapp.model.Cliente;
import com.freelapp.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<Cliente> findAll(){
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> findPage(int pageNumber){
		Pageable pageable = PageRequest.of(pageNumber -1, 4);
		return clienteRepository.findAll(pageable);
		
	}
	
	public Page<Cliente> findSearchedPage(int pageNumber, String input){
		Pageable pageable = PageRequest.of(pageNumber -1, 4);
		return clienteRepository.search(input, pageable);
		
	}


}
