package com.freelapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.freelapp.model.Cliente;
import com.freelapp.model.Progetto;
import com.freelapp.model.User;
import com.freelapp.repository.UserRepository;

@Controller
public class HomePageController {

	  @Autowired
	  private UserRepository repositoryUser;
      
      		@GetMapping("/HomePage/{id}")
		public String indexPage(@PathVariable("id") Integer id, Model model) {
			
			User utente = repositoryUser.findById(id).get();
			
			model.addAttribute("formUser", utente);
			
			return "/HomePage"; 
		}
 
}
	

