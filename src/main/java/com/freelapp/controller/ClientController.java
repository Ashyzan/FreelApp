package com.freelapp.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;

import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.freelapp.model.Cliente;
import com.freelapp.model.Progetto;
import com.freelapp.repository.ClienteRepository;
import com.freelapp.repository.ProgettoRepository;
import com.freelapp.repository.TaskRepository;
import com.freelapp.service.ClienteService;
import com.freelapp.service.UploadFileService;

import jakarta.validation.Valid;

@Controller
@ControllerAdvice //  serve per la gestione degli errori di maxSize file
public class ClientController {

	@Autowired
	private ClienteRepository repositoryCliente;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProgettoRepository repositProgetto;
	
	@Autowired
	private TaskRepository repositTask;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	
	
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
		
		return "/Clienti/freelApp-listClient";
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
				
				return "Clienti/freelApp-listClient";
		 
		 
	  }	
	
	@GetMapping("/Clienti/{id}")
	public String descrizioneCliente(@PathVariable("id") int clienteId, Model model) {
		
		model.addAttribute("cliente", repositoryCliente.getReferenceById(clienteId));
	
		return "/Clienti/freelapp-descrizioneCliente";
	  }
	
	
	@GetMapping("/Clienti/insert")
	public String aggiungiCliente(Model model) {
	    
	    model.addAttribute("formCliente", new Cliente());
	    
	    model.addAttribute("areErrors", false);
	    
	    return "/Clienti/freelapp-insertClient"; 
	}
	
	@PostMapping(value = "/Clienti/insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String storeCliente(@RequestParam("file")MultipartFile file, Model model,
							@Valid @ModelAttribute("formCliente") Cliente formCliente, BindingResult bindingResult)
							throws IllegalStateException, IOException {
//	verifica attravero un metodo dell'UploadFileService se il file ha un formato non valido e restuisce un boolean
		Boolean anyFormatImgError = uploadFileService.anyErrorFormatImage(file);
		
//  se il file ha un formato non valido viene creato un nuovo errore e aggiunto al bindingResult		
		if(anyFormatImgError == true) {
			
			ObjectError errorFormatImage = new ObjectError("formatImageError", "Formati consentiti: JPEG o JPG");
			
			bindingResult.addError(errorFormatImage);
			
//	una volta creato l'errore custom ne viene recueperato il messaggio e passato al model
			
			String errorFormatImageMessage = errorFormatImage.getDefaultMessage();
		
			model.addAttribute("errorFormatImageMessage", errorFormatImageMessage);
		}
		
		
		if(bindingResult.hasErrors()) {
		   
			model.addAttribute("areErrors", true);
			

			return "/Clienti/freelapp-insertClient";
		}
	   
		if(!file.isEmpty()) {
			
//	metodo dell'UploadFileService che trasferisce il file alla directory dell'applicazione ed
//  a db viene salvato l'url per poi recuperare l'immagine e utilizzarla
		   
		   formCliente.setUrlLogo(uploadFileService.saveLogoImage(file));
	  
		} else {
			
//	se l'utente non ha scelto alcun file di default viene assegnato al logo cliente un avatar
		   
		   formCliente.setUrlLogo("/logoImage/avatar.jpg");

		}
	   

		repositoryCliente.save(formCliente);
	  

		return "redirect:/Clienti";
	}
	
	@GetMapping("/Clienti/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
				
		model.addAttribute("formCliente", repositoryCliente.findById(id).get());
		
		return "/Clienti/freelapp-editClient";
	}
	
	
	@PostMapping("/Clienti/edit/{id}")
	public String updateCliente (@Valid @ModelAttribute("formCliente") Cliente formCliente, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "/Clienti/freelapp-editClient";
		}
		
		repositoryCliente.save(formCliente);
		
		return "redirect:/Clienti";
	}
	
	
	@PostMapping("/Clienti/delete/{id}")
	public String deleteClienti(@PathVariable("id") Integer id) {
		
		Cliente cliente = new Cliente();
		
		cliente = repositoryCliente.getReferenceById(id);
		
		//per poter cancellare i progetti collegati al cliente ho dovuto cancellare i task associati
		//e per farlo ho creato una lista di progetti associati al cliente, l'ho iterata con il for 
		//e per ogni progetto della lista vado a cancellare i suoi task. fatto questo esco
		//dal for e procedo a cancellare i progetti del cliente e poi infine il cliente.
		
		List<Progetto> listaProgetti = cliente.getProgetti();
		
		Integer idProgetto = 0;
		
		for(int i = 0 ; i < listaProgetti.size(); i++) {
			
			idProgetto = listaProgetti.get(i).getId();
			
			repositTask.deleteByProgettoId(idProgetto);
			
		}
		
		repositProgetto.deleteByClienteId(id);
		
		repositoryCliente.deleteById(id);
		
		return "redirect:/Clienti";
	  }

// Valore che viene inserito per la dimensione massima presa da application.properties
//  nel messaggio  di errore "handleMaxSizeUploadError" 
	
	@Value("${spring.servlet.multipart.max-file-size}")
	private String maxUploadFileSize;
	
//	Metodo che restituice errore di maxSize per l'upload dei file e lo passa direttamente al
//	template senza utilizzo del model. viene poi utilizato in js come gli altri valori passati
//	con il model
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public String handleMaxSizeUploadError(RedirectAttributes redirectAttributes) {
		
		redirectAttributes.addFlashAttribute("errorMaxFileSize", "Non è possibile fare l'upload di file superiori a " + maxUploadFileSize);
		
		return "redirect:/Clienti/insert";
	}
}
	

