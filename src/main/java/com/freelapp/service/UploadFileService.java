package com.freelapp.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {
	
	public String saveLogoImage(MultipartFile file) throws IllegalStateException, IOException {
		
		
//	creazione file name
		String finalFileName = LocalDateTime.now() + file.getOriginalFilename();
		
//	trasferimento alla directory del file !!! ATTENZIONE !!! essendo server locale il percorso varia per ogni 
//	JVM pertanto è necessario cambiare il percorso della directory
		
		file.transferTo(new File("/Users/francesco/Desktop/Corso Backend JAVA Developer PT/Progetti Condivisi/FreelApp/FreelApp/src/main/resources/static/logoImage/" + finalFileName));

//	creazione dell'url che viene salvato a db nella colonna urlLogo
		String imgPath = "/logoImage/" + finalFileName;
		
		return imgPath;
	}
	
//	metodo che verifica se il formato del file rispecchia i requisiti (JPEG/JPG) e restituisce
//	un boolean per la successiva generazione dell'errore custom relativo al formato
	public Boolean anyErrorFormatImage(MultipartFile file) {
		
		boolean result = false;
		
		String contentType = file.getContentType();
		
		if (!file.isEmpty() && !contentType.equals("image/jpeg") && !contentType.equals("image/jpg")) {
		    
			result = true;
			
		}
		
		return result;
	
	}
	

}
