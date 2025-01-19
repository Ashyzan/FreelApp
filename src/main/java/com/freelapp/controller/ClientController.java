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
import com.freelapp.repository.ClienteRepository;
import com.freelapp.service.ClienteService;

import jakarta.validation.Valid;

@Controller
public class ClientController {

	@Autowired
	private ClienteRepository repositoryCliente;
	
	@Autowired
	private ClienteService clienteService;
	
	
//	@GetMapping("/Clienti")
//	public String listaClienti(Model model) {
//		
//		List<Cliente> listaClienti = repositoryCliente.findAll();
//		
//		model.addAttribute("list", listaClienti);
//		
//		return "/Clienti/listClient";
//	} 
	
	@GetMapping("/Clienti")
	public String listaClienti(Model model) {
		
		return getOnePage(1, model );
	} 
	
	
	@GetMapping("/Clienti/page/{pageNumber}")
	public String getOnePage(@PathVariable("pageNumber") int currentPage, Model model ) {
		
		Page<Cliente> page = clienteService.findPage(currentPage);
		
		int totalPages = page.getTotalPages();
		
		long totalItems = page.getTotalElements();
		
		List<Cliente> listClienti = page.getContent();
		
		model.addAttribute("list", listClienti);
		
		model.addAttribute("currentPage", currentPage);
	
		model.addAttribute("totalPages", totalPages);
		
		model.addAttribute("totalItems", totalItems);
		
		return "/Clienti/listClient";
	} 
	
	@GetMapping("/cliente-search")
	public String listaClientiSearch(@Param("input") String input,Model model) {
		
		return clienteBySearch(1, input, model);
	} 
		 
	 @GetMapping("/cliente-search/page/{numberPage}")
	 public String clienteBySearch(@PathVariable("pageNumber") int currentPage, String input,
			 	Model model) {

//		 		List<Cliente> list = new ArrayList<Cliente>();
//
//				 if(!input.isEmpty()) {
//					 
//					 list = repositoryCliente.search(input);
//					 
//				 } 
				 
				 Page<Cliente> page = clienteService.findSearchedPage(currentPage,input);

				 int totalPages = page.getTotalPages();
				 
				 long totalItems = page.getTotalElements();
				 
				 List<Cliente> listaClientiSearch = page.getContent();
				
				model.addAttribute("currentPage", currentPage);
			
				model.addAttribute("totalPages", totalPages);
				
				model.addAttribute("totalItems", totalItems);
				
				model.addAttribute("list", listaClientiSearch);	
				
				return "Clienti/listClient";
		 
		 
//		    if (input == null || input.isBlank()) {
//		          return "redirect:/Clienti";
//		       }
//		    else { 
//		    	 
//		    	   list = repositoryCliente.search(input);
//
//		           if (list.isEmpty()) 
//		    			         return "redirect:/Clienti";     
//		           else  {  
//		        	   		model.addAttribute("list", list);
//			    	  		return "redirect:/Clienti/" + list.get(0).getId(); 
//			                }   
//		          } 
	  }	
	
	@GetMapping("/Clienti/{id}")
	public String descrizioneCliente(@PathVariable("id") int clienteId, Model model) {
		
		model.addAttribute("cliente", repositoryCliente.getReferenceById(clienteId));
	
		return "/Clienti/descrizioneCliente";
	  }
	
	
	@GetMapping("/Clienti/insert")
	public String aggiungiCliente(Model model) {
	    
	    model.addAttribute("formCliente", new Cliente());
	    
	    return "/Clienti/insertClient"; 
	}
	
	
	@PostMapping("/Clienti/insert")
	public String storeCliente(@Valid @ModelAttribute("formCliente") Cliente formCliente, BindingResult bindingResult, Model model){
		
	   if(bindingResult.hasErrors()) {
	      return "/Clienti/insertClient";
	   }

	   repositoryCliente.save(formCliente);
	  

	   return "redirect:/Clienti";
	}
	
	@GetMapping("/Clienti/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
				
		model.addAttribute("formCliente", repositoryCliente.findById(id).get());
		
		return "/Clienti/editClient";
	}
	
	
	@PostMapping("/Clienti/edit/{id}")
	public String updateCliente (@Valid @ModelAttribute("formCliente") Cliente formCliente, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "/Clienti/editClient";
		}
		
		repositoryCliente.save(formCliente);
		
		return "redirect:/Clienti";
	}
	
	
	@PostMapping("/Clienti/delete/{id}")
	public String deleteClienti(@PathVariable("id") Integer id) {
		
		repositoryCliente.deleteById(id);
		
		return "redirect:/Clienti";
	  }

	
}
	

