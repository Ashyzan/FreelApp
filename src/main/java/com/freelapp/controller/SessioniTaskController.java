package com.freelapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.freelapp.repository.SessionitaskRepository;



@Controller
public class SessioniTaskController {
	/////// ***************************************************************///////////
	////// molta della logica per questo controller è nel contatore controller
	////////// ***************************************************************///////////
	
	@Autowired
	private SessionitaskRepository SessioniTaskRepository;
	
	
	@PostMapping("/resetsessioni/{id}")
	public String resetSessioni(@PathVariable("id") Integer contatoreId, Model model) {
		SessioniTaskRepository.deleteById(contatoreId);
		System.out.println("********** *******************************************************************");
		System.out.println("********** contatore id" + contatoreId);
	    return "redirect:/Task/{id}";
	}
}
