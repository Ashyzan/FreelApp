package com.freelapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.freelapp.service.ContatoreService;

@Controller
@RequestMapping("/Errori")
public class ErroriController {
	
	@Autowired 
	private ContatoreService contatoreservice;
	
	@GetMapping("/MaxUploadSizeExceeded")
	public String MaxUploadSizeExceeded(Model model) {
		
//		passo al model l'endpoint da dare come input hidden a strt/pause/stop del contatore
		String endPoint = "/Errori/MaxUploadSizeExceeded";
		model.addAttribute("endPoint", endPoint);
		
			contatoreservice.importContatoreInGet(model);
			model.addAttribute("contatoreInUso", ContatoreController.contatoreInUso);
			model.addAttribute("taskInUso", ContatoreController.taskInUso);

		
		return "/Errori/MaxUploadSizeExceeded";
		
	}
	

}
