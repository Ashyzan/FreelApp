package com.freelapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.freelapp.model.User;
import com.freelapp.model.Cliente;
import com.freelapp.model.Progetto;
import com.freelapp.repository.ClienteRepository;
import com.freelapp.repository.UserRepository;
import com.freelapp.repository.ProgettoRepository;


@Controller
public class DashboardController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProgettoRepository progettoRepository;

	@GetMapping("/dashboard")
	public String index(Model model){

		List<User> userList = new ArrayList<User> ();

		userList = userRepository.findAll(Sort.by(Sort.Direction.ASC, "Cognome"));

		model.addAttribute("list", userList);
		

		List<Cliente> clienteList = new ArrayList<Cliente> ();

		clienteList = clienteRepository.findAll(Sort.by(Sort.Direction.ASC, "Name"));

		model.addAttribute("clientiList", clienteList);
		
		
		List<Progetto> progettiList = new ArrayList<Progetto> ();

		progettiList = progettoRepository.findAll(Sort.by(Sort.Direction.ASC, "Name"));

		model.addAttribute("progettiList", progettiList);
		

		return "dashboard";

	}
}
