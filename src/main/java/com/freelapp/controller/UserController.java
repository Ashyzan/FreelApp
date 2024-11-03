package com.freelapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.freelapp.model.User;
import com.freelapp.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private UserRepository repositoryUser;
	
	
	@GetMapping("/Admin/{id}")
	public String descrizioneAdmin(@PathVariable("id") int userId, Model model) {
		
		model.addAttribute("user", repositoryUser.getReferenceById(userId));
	
		return "/Admin/descrizioneUser";
	  }
	
	@GetMapping("/Admin/AllClient/{id}")
	public String allClient(@PathVariable("id") int userId, Model model) {
		
		model.addAttribute("user", repositoryUser.getReferenceById(userId));
	
		return "/Admin/allClient";
	  }
	
	@GetMapping("Admin/insert")
	public String aggiungiUtente(Model model) {
	    
	    model.addAttribute("formUser", new User());
	    
	    return "/Admin/insertUser"; 
	}
	
	
	@PostMapping("/Admin/insert")
	public String storeUser(@Valid @ModelAttribute("formUser") User formUser, BindingResult bindingResult, Model model){
		
	   if(bindingResult.hasErrors()) {
	      return "/Admin/insertUser";
	   }
	   
	   repositoryUser.save(formUser);
	  
	   return "redirect:/Admin/" + formUser.getId();

	}
	
	@GetMapping("/Admin/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
				
		model.addAttribute("formUser", repositoryUser.findById(id).get());
		
		return "/Admin/editUser";
	}
	
	
	@PostMapping("/Admin/edit/{id}")
	public String updateUser (@Valid @ModelAttribute("formUser") User formUser, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "/Admin/editUser";
		}
		
		repositoryUser.save(formUser);
		
		return "redirect:/Admin/" + formUser.getId();
	}

}
	
