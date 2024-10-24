package com.freelapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import jakarta.validation.Valid;

@Controller
public class ClientController {

	@Autowired
	private ClienteRepository repositoryCliente;
	
	
	 @GetMapping("/Clienti")
	 public String listaClienti(Model model) {
	
		List<Cliente> listaClienti = repositoryCliente.findAll();
		model.addAttribute("list", listaClienti);
		return "/Clienti/listClient";
	}
	 
	 @GetMapping("/cliente-search")
	 public String clienteByPartitaIva(@Param("input") String input, Model model) {


				List<Cliente> list = new ArrayList<Cliente>();
				
				if(!input.isEmpty()) {
					
					list = repositoryCliente.search(input);
					
				} 
					
				model.addAttribute("list", list);	
				
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
	

