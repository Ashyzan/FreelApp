package com.freelapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freelapp.model.Cliente;
import com.freelapp.repository.ClienteRepository;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiRestController {
	
	@Autowired
	private ClienteRepository repositoryCliente;
	
	@GetMapping
	public Page<Cliente> findAll(@RequestParam int page, @RequestParam int size){
		PageRequest pageRequest = PageRequest.of(page, size);
		return repositoryCliente.findAll(pageRequest);
	}
	
}
