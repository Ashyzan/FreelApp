package com.freelapp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import com.freelapp.model.Progetto;
import com.freelapp.repository.ProgettoRepository;
import com.freelapp.model.User;
import com.freelapp.repository.UserRepository;
import com.freelapp.model.Cliente;
import com.freelapp.repository.ClienteRepository;

@Controller
public class ProgettoController {


	@Autowired
	private ProgettoRepository repositProgetto;
	  
	@Autowired
	private UserRepository repositUser;
	
	@Autowired
	private ClienteRepository repositClient;
	
	
			@GetMapping("/Progetti/{id}")
			public String descrizioneProgetto(@PathVariable("id") int progettoId, Model model) {
		
				model.addAttribute("progetto", repositProgetto.getReferenceById(progettoId));
	
				return "/Progetti/descrizioneProgetto";
		   }
	
			@GetMapping("/Progetti/insert/{id}")
			public String insertProject(@PathVariable("id") Integer id, Model model) {
				
				User utente = repositUser.findById(id).get();

				Progetto formProgetto = new Progetto();
				
				formProgetto.setUtente(utente);
					
				List<Cliente> listaClienti = repositClient.findAll();  
				
				model.addAttribute("formClienti", listaClienti);

				model.addAttribute("formProgetto", formProgetto);
				
				return "/Progetti/insertProgetto";
			}
	
	
			@PostMapping("/Progetti/insert")
			public String storeProgetto(@Valid @ModelAttribute("formProgetto") Progetto formProgetto, BindingResult bindingResult, Model model) {
		
				if(bindingResult.hasErrors()) {
					return "/Progetti/insertProgetto";
				}
		
				repositProgetto.save(formProgetto);
		
				return "redirect:/Admin/" + formProgetto.getUtente().getId();       
			}
	
			
			@GetMapping("/Progetti/edit/{id}")
			public String edit(@PathVariable("id") Integer id, Model model) {
				
				Progetto formProgetto = repositProgetto.findById(id).get();
				
				model.addAttribute("formProgetto", formProgetto);
				
				return "/Progetti/editProgetto";
			}
			
			
			@PostMapping("/Progetti/edit/{id}")
		    public String updateProgetto(@Valid @ModelAttribute("formProgetto") Progetto formProgetto, BindingResult bindingResult, Model model) {
							
				if(bindingResult.hasErrors()) {
					return  "/Progetti/editProgetto";
				}
 
				repositProgetto.save(formProgetto);
				
				return "redirect:/Admin/" + formProgetto.getUtente().getId(); 
			    }
		
			
}
