package com.freelapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.freelapp.model.SessioniTask;
import com.freelapp.repository.SessionitaskRepository;

import jakarta.transaction.Transactional;



@Controller
public class SessioniTaskController {
	/////// ***************************************************************///////////
	////// molta della logica per questo controller è nel contatore controller
	/////il metodo che chiama il repository per cancellare deve essere eseguito 
	///all’interno di una transazione, perché l’operazione di cancellazione richiede una 
	///transazione attiva
	////////// ***************************************************************///////////
	
	@Autowired
	private SessionitaskRepository SessioniTaskRepository;
	
	
	@PostMapping("/resetsessioni/{id}")
	@Transactional
	public String resetSessioni(@PathVariable("id") Integer contatoreId, Model model) {
		SessioniTaskRepository.deleteByContatoreId(contatoreId);

	    return "redirect:/Task/{id}";
	}
	
	@GetMapping("/refreshSessioni/{id}")
	public String refreshTask(@PathVariable Integer id) {
	    return "redirect:/Task/" + id;
	}
}
