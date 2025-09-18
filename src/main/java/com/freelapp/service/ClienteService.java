package com.freelapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.data.domain.Pageable;

import com.freelapp.model.Cliente;
import com.freelapp.model.Progetto;
import com.freelapp.repository.ClienteRepository;
import com.freelapp.repository.ProgettoRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProgettoRepository progettoRepository;
	
	public List<Cliente> findAll(){
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> findPage(int pageNumber){
		Pageable pageable = PageRequest.of(pageNumber -1, 12);
		return clienteRepository.findAll(pageable);
		
	}
	
	public Page<Cliente> findSearchedPage(int pageNumber, String input){
		Pageable pageable = PageRequest.of(pageNumber -1, 12);
		return clienteRepository.search(input, pageable);
		
	}


	//metodo che restituisce numero di progetti per cliente
	public void numeroProgettiDelCliente(Cliente cliente, Model model) {
		List<Progetto> listaProgetti = new ArrayList<Progetto>();
		listaProgetti = progettoRepository.findByClienteId(cliente.getId());
		model.addAttribute("numeroProgettiDelCliente", listaProgetti.size());
	}
}
