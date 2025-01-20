package com.freelapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.freelapp.model.Cliente;
import com.freelapp.model.Progetto;
import com.freelapp.model.User;
import com.freelapp.repository.ClienteRepository;
import com.freelapp.repository.ProgettoRepository;
import com.freelapp.repository.UserRepository;
import com.freelapp.service.ProgettoService;

import jakarta.validation.Valid;

@Controller
public class ProgettoController {


	@Autowired
	private ProgettoRepository repositProgetto;
	  
	@Autowired
	private UserRepository repositUser;
	
	@Autowired
	private ClienteRepository repositClient;
	
	@Autowired
	private ProgettoService progettoService;
	
			@GetMapping("/Progetti")
			public String listaProgetti(Model model) {
				
				return getOnePage(1, model);
			}
			
			@GetMapping("/Progetti/page/{pageNumber}")
			public String getOnePage(@PathVariable("pageNumber") int currentPage, Model model ) {
				
					Page<Progetto> page = progettoService.findPage(currentPage);
					
					int totalPages = page.getTotalPages();
					
					long totalItems = page.getTotalElements();
					
					List<Progetto> listProgetti = page.getContent();
					
					model.addAttribute("list", listProgetti);
					
					model.addAttribute("currentPage", currentPage);
				
					model.addAttribute("totalPages", totalPages);
					
					model.addAttribute("totalItems", totalItems);
				
				return "/Progetti/freelApp-listaProgetti";
			} 
			
			@GetMapping("/Progetti/progetto-search")
			public String listaProgettiSearch(@Param("input") String input, Model model) {
				
				return progettoBySearch(1, input, model);
			} 
				 
			 @GetMapping("/Progetti/progetto-search/page/{numberPage}")
			 public String progettoBySearch(@PathVariable("pageNumber") int currentPage, String input,
					 	Model model) {

						 
					Page<Progetto> page = progettoService.findSearchedPage(currentPage,input);

					int totalPages = page.getTotalPages();
						 
					long totalItems = page.getTotalElements();
						 
					List<Progetto> listaClientiSearch = page.getContent();
						
					model.addAttribute("currentPage", currentPage);
					
					model.addAttribute("totalPages", totalPages);
						
					model.addAttribute("totalItems", totalItems);
						
					model.addAttribute("list", listaClientiSearch);	
						
				return "/Progetti/freelApp-listaProgetti";
			}
	
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
				
				return "/Progetti/freelapp-insertProgetto";
			}
	
	
			@PostMapping("/Progetti/insert")
			public String storeProgetto(@Valid @ModelAttribute("formProgetto") Progetto formProgetto, BindingResult bindingResult, Model model) {
		
				if(bindingResult.hasErrors()) {
					
					return "/Progetti/freelapp-insertProgetto";
					
				}
		
				repositProgetto.save(formProgetto);
		
				return "redirect:/Admin/" + formProgetto.getUtente().getId();       
			}
	
			
			@GetMapping("/Progetti/edit/{id}")
			public String edit(@PathVariable("id") Integer id, Model model) {
				
				Progetto formProgetto = repositProgetto.findById(id).get();
				
				model.addAttribute("formProgetto", formProgetto);
				
				model.addAttribute("formClienti", repositClient.findAll());
				
				return "/Progetti/freelapp-editProgetto";
			}
			
			
			@PostMapping("/Progetti/edit/{id}")
		    public String updateProgetto(@Valid @ModelAttribute("formProgetto") Progetto formProgetto, BindingResult bindingResult, Model model) {
							
				if(bindingResult.hasErrors()) {
					return  "/Progetti/freelapp-editProgetto";
				}
 
				repositProgetto.save(formProgetto);
				
				return "redirect:/Admin/" + formProgetto.getUtente().getId(); 
			    }		
			
}
